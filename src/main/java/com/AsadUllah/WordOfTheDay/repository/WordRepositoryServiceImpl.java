package com.AsadUllah.WordOfTheDay.repository;

import com.AsadUllah.WordOfTheDay.Model.Word;
import com.AsadUllah.WordOfTheDay.entity.WordEntity;
import com.AsadUllah.WordOfTheDay.mapper.WordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WordRepositoryServiceImpl implements WordRepositoryService {

    private final WordRepository wordRepository;
    private final WordMapper wordMapper;

    @Override
    public boolean exists(String word) {
        return wordRepository.findByWord(word).isPresent();
    }

    @Override
    public void saveWord(Word word) {
        WordEntity entity = wordMapper.wordToWordEntity(word);
        wordRepository.save(entity);
    }
}
