package com.AsadUllah.WordOfTheDay.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "Word_History")
@Getter

public class WordEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Setter
    private String word;
}
