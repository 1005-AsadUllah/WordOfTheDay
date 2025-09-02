package com.AsadUllah.WordOfTheDay.service;

import com.AsadUllah.WordOfTheDay.Model.Response;

import java.util.List;

public interface DictionaryService {
    List<Response> getDefinitions(String word);
}
