package com.AsadUllah.WordOfTheDay.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


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
