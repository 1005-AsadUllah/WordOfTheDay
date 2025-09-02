package com.AsadUllah.WordOfTheDay.repository;

import com.AsadUllah.WordOfTheDay.Model.Word;

public interface WordRepositoryService {
    boolean exists(String word);
    void saveWord(Word word);
}
