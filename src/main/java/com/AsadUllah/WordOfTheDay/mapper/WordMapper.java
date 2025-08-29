package com.AsadUllah.WordOfTheDay.mapper;

import com.AsadUllah.WordOfTheDay.Model.Word;
import com.AsadUllah.WordOfTheDay.entity.WordEntity;
import org.springframework.stereotype.Component;

@Component
public class WordMapper {
    public WordEntity wordToWordEntity(Word word) {
        WordEntity wordEntity = new WordEntity();
        wordEntity.setWord(word.getWord());
        return wordEntity;
    }
}
