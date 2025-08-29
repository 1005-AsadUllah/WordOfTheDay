package com.AsadUllah.WordOfTheDay.repository;

import com.AsadUllah.WordOfTheDay.entity.WordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface WordRepository extends JpaRepository<WordEntity,Long> {
    Optional<WordEntity> findByWord(String word);
}
