package com.AsadUllah.WordOfTheDay.controller;

import com.AsadUllah.WordOfTheDay.Model.WordResponse;
import com.AsadUllah.WordOfTheDay.service.WordService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WordRestController {

    private final WordService wordService;

    /**
     * REST controller endpoint to retrieve the word of the day along with its definitions
      and parts of speech.
     */
    @Operation(summary = "Get the word of the day with its definitions and parts of speech")
    @GetMapping("/wordOfTheDay")
    public WordResponse getWord() {
        return wordService.getWord();
    }

}
