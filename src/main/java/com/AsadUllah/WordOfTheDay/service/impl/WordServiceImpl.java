package com.AsadUllah.WordOfTheDay.service.impl;

import com.AsadUllah.WordOfTheDay.Model.Word;
import com.AsadUllah.WordOfTheDay.Model.WordResponse;
import com.AsadUllah.WordOfTheDay.Model.Response;
import com.AsadUllah.WordOfTheDay.repository.WordRepositoryService;
import com.AsadUllah.WordOfTheDay.service.DictionaryService;
import com.AsadUllah.WordOfTheDay.service.WordFetcher;
import com.AsadUllah.WordOfTheDay.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WordServiceImpl implements WordService {

    private final WordFetcher wordFetcher;
    private final DictionaryService dictionaryService;
    private final WordRepositoryService repositoryService;

    @Cacheable(value = "wordOfTheDay", key = "'word'")
    public WordResponse getWord() {
        String word = fetchUniqueWord();
        List<Response> definitions = dictionaryService.getDefinitions(word);

        Word wordModel = new Word();
        wordModel.setWord(word);
        repositoryService.saveWord(wordModel);

        return new WordResponse(word, definitions);
    }

    private String fetchUniqueWord() {
        String word;
        do {
            word = wordFetcher.fetchRandomWord();
        } while (repositoryService.exists(word));
        return word;
    }
}
