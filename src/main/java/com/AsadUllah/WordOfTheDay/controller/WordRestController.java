package com.AsadUllah.WordOfTheDay.controller;

import com.AsadUllah.WordOfTheDay.Model.WordResponse;
import com.AsadUllah.WordOfTheDay.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WordRestController {

    private final WordService wordService;

    @GetMapping("/wordOfTheDay")
    public WordResponse getWord() {
        return wordService.getWord();
    }

}
