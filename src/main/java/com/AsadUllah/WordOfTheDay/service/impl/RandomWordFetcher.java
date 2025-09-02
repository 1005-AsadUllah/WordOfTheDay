package com.AsadUllah.WordOfTheDay.service.impl;

import com.AsadUllah.WordOfTheDay.service.WordFetcher;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class RandomWordFetcher implements WordFetcher {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private static final String RANDOM_WORD_URL = "https://random-word-api.herokuapp.com/word";

    @Override
    public String fetchRandomWord() {
        try {
            String response = restTemplate.getForObject(RANDOM_WORD_URL, String.class);
            JsonNode node = objectMapper.readTree(response);
            return node.get(0).asText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch random word", e);
        }
    }
}
