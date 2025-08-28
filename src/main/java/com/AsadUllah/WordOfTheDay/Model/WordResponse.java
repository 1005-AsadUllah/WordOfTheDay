package com.AsadUllah.WordOfTheDay.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WordResponse<T> {
    private String word;
    private List<T> responses;

}
