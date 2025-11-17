package com.AsadUllah.WordOfTheDay.controller;

import com.AsadUllah.WordOfTheDay.Model.WordResponse;
import com.AsadUllah.WordOfTheDay.service.WordService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Operation(summary = "User endpoint only accessible by users with USER role")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("user")
    public String user() {
        return "Hello, User!";
    }

    @Operation(summary = "Admin endpoint only accessible by users with ADMIN role")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("admin")
    public String admin() {
        return "Hello, Admin!";
    }

}
