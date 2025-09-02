package com.AsadUllah.WordOfTheDay.service;

import com.AsadUllah.WordOfTheDay.Model.Word;
import com.AsadUllah.WordOfTheDay.Model.WordResponse;
import com.AsadUllah.WordOfTheDay.entity.WordEntity;
import com.AsadUllah.WordOfTheDay.mapper.WordMapper;
import com.AsadUllah.WordOfTheDay.repository.WordRepository;
import com.AsadUllah.WordOfTheDay.service.impl.WordServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WordServiceImplTest {

    @Mock
    private WordRepository wordRepository;

    @Mock
    private WordMapper wordMapper;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WordServiceImpl wordServiceImpl;

    @BeforeEach
    void setUp() {
        // Since RestTemplate and ObjectMapper are instantiated within the class,
        // we use ReflectionTestUtils to set the mocked instance.
        ReflectionTestUtils.setField(wordServiceImpl, "restTemplate", restTemplate);
        ReflectionTestUtils.setField(wordServiceImpl, "objectMapper", new ObjectMapper());
    }

    @Test
    void getWord_shouldReturnWordWithDefinitionsAndSaveToRepository() {
        // Given
        String mockWord = "test";
        String randomWordApiResponse = "[\"" + mockWord + "\"]";
        String dictionaryApiResponse = """
                [
                    {
                        "word": "test",
                        "phonetics": [],
                        "meanings": [
                            {
                                "partOfSpeech": "noun",
                                "definitions": [
                                    { "definition": "A procedure for critical evaluation." }
                                ]
                            }
                        ]
                    }
                ]
                """;

        when(restTemplate.getForObject(anyString(), eq(String.class)))
                .thenReturn(randomWordApiResponse, dictionaryApiResponse); // Chaining responses

        when(wordRepository.findByWord(anyString())).thenReturn(Optional.empty());
        when(wordMapper.wordToWordEntity(any(Word.class))).thenReturn(new WordEntity());

        // When
        WordResponse response = wordServiceImpl.getWord();

        // Then
        assertEquals(mockWord, response.getWord());
        assertEquals(1, response.getResponses().size());
        assertEquals("A procedure for critical evaluation.", response.getResponses().get(0).getDefinition());
        assertEquals("noun", response.getResponses().get(0).getPartOfSpeech());

        verify(wordRepository, times(1)).findByWord(mockWord);
        verify(wordRepository, times(1)).save(any(WordEntity.class));
        verify(wordMapper, times(1)).wordToWordEntity(any(Word.class));
        verify(restTemplate, times(2)).getForObject(anyString(), eq(String.class));
    }

    @Test
    void getWord_shouldHandleExceptionAndReturnDefaultResponse() {
        // Given
        String mockWord = "invalidword";
        String randomWordApiResponse = "[\"" + mockWord + "\"]";

        when(restTemplate.getForObject(eq("https://random-word-api.herokuapp.com/word"), eq(String.class)))
                .thenReturn(randomWordApiResponse);

        // Mock a failure for the dictionary API call
        when(restTemplate.getForObject(eq("https://api.dictionaryapi.dev/api/v2/entries/en/" + mockWord), eq(String.class)))
                .thenThrow(new RuntimeException("Dictionary API failed"));

        when(wordRepository.findByWord(anyString())).thenReturn(Optional.empty());

        // When
        WordResponse response = wordServiceImpl.getWord();

        // Then
        assertEquals(mockWord, response.getWord());
        assertEquals(1, response.getResponses().size());
        assertEquals("No definition found", response.getResponses().get(0).getDefinition());
        assertEquals("N/A", response.getResponses().get(0).getPartOfSpeech());

        verify(wordRepository, times(1)).findByWord(mockWord);
        // Ensure no save operation is performed on the repository
        verify(wordRepository, never()).save(any(WordEntity.class));
    }

    @Test
    void fetchWord_shouldKeepFetchingUntilUniqueWordIsFound() {
        // Given
        String duplicateWord = "duplicate";
        String uniqueWord = "unique";

        // Mock random word API to return a duplicate word first, then a unique one.
        // The third call is for the dictionary API.
        when(restTemplate.getForObject(anyString(), eq(String.class)))
                .thenReturn("[\"" + duplicateWord + "\"]", "[\"" + uniqueWord + "\"]")
                .thenReturn("""
                        [
                            {
                                "word": "unique",
                                "meanings": [
                                    { "partOfSpeech": "adjective", "definitions": [ { "definition": "One of a kind." } ] }
                                ]
                            }
                        ]
                        """);

        // Mock repository to simulate the first word existing, but the second one not.
        when(wordRepository.findByWord(duplicateWord)).thenReturn(Optional.of(new WordEntity()));
        when(wordRepository.findByWord(uniqueWord)).thenReturn(Optional.empty());
        when(wordMapper.wordToWordEntity(any(Word.class))).thenReturn(new WordEntity());

        // When
        WordResponse response = wordServiceImpl.getWord();

        // Then
        assertEquals(uniqueWord, response.getWord());

        // Verify that the random word API was called twice and the dictionary API once.
        verify(restTemplate, times(3)).getForObject(anyString(), eq(String.class));

        // The repository's findByWord method should have been called for both words.
        verify(wordRepository, times(1)).findByWord(duplicateWord);
        verify(wordRepository, times(1)).findByWord(uniqueWord);
        verify(wordRepository, times(1)).save(any(WordEntity.class));
    }
}
