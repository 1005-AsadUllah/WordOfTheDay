package com.AsadUllah.WordOfTheDay.mapper;

import com.AsadUllah.WordOfTheDay.Model.Word;
import com.AsadUllah.WordOfTheDay.entity.WordEntity;
import org.springframework.stereotype.Component;

@Component
public class WordMapper {

    /**
     * Converts a Word model object to a WordEntity object.
     *
     * @param word the Word object
     * @return the mapped WordEntity object
     */
    public WordEntity wordToWordEntity(Word word) {
        WordEntity wordEntity = new WordEntity();
        wordEntity.setWord(word.getWord());
        return wordEntity;
    }
}
