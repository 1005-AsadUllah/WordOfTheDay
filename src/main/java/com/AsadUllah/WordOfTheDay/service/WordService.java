package com.AsadUllah.WordOfTheDay.service;

import com.AsadUllah.WordOfTheDay.Model.Response;
import com.AsadUllah.WordOfTheDay.Model.Word;
import com.AsadUllah.WordOfTheDay.Model.WordResponse;
import com.AsadUllah.WordOfTheDay.entity.WordEntity;
import com.AsadUllah.WordOfTheDay.mapper.WordMapper;
import com.AsadUllah.WordOfTheDay.repository.WordRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WordService {

    private final WordRepository wordRepository;
    private final WordMapper wordMapper;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String Random_Word_URL = "https://random-word-api.herokuapp.com/word";
    private static final String Dictionary_API_URL = "https://api.dictionaryapi.dev/api/v2/entries/en/";

    public WordResponse getWord() {
        String word = fetchWord();
        List<Response> responses = new ArrayList<>();

        try {
            String details = restTemplate.getForObject(Dictionary_API_URL + word, String.class);
            JsonNode node = objectMapper.readTree(details);

            JsonNode meanings = node.get(0).get("meanings");
            for (var meaning : meanings) {
                String partOfSpeech = meaning.get("partOfSpeech").asText();
                JsonNode definitions = meaning.get("definitions");

                for (JsonNode def : definitions) {
                    String definition = def.get("definition").asText();
                    responses.add(new Response(definition, partOfSpeech));
                }
            }

            Word saveWord = new Word();
            saveWord.setWord(word);
            WordEntity wordEntity = wordMapper.wordToWordEntity(saveWord);
            wordRepository.save(wordEntity);
        } catch (Exception e) {
            responses.add(new Response("No definition found", "N/A"));

        }
        return new WordResponse(word, responses);
    }

    private String fetchWord() {
        String word;
        while (true) {
            try {
                String response = restTemplate.getForObject(Random_Word_URL, String.class);
                JsonNode node = objectMapper.readTree(response);
                word = node.get(0).asText();

                if (wordRepository.findByWord(word).isEmpty()) {
                    break;
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to fetch word", e);
            }
        }
        return word;
    }


}
