package com.AsadUllah.WordOfTheDay.service.impl;

import com.AsadUllah.WordOfTheDay.Model.Response;
import com.AsadUllah.WordOfTheDay.service.DictionaryService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DictionaryServiceImpl implements DictionaryService {

    private final RestTemplate restTemplate ;
    private final ObjectMapper objectMapper;
    private static final String DICTIONARY_API_URL = "https://api.dictionaryapi.dev/api/v2/entries/en/";

    @Override
    public List<Response> getDefinitions(String word) {
        List<Response> responses = new ArrayList<>();
        try {
            String details = restTemplate.getForObject(DICTIONARY_API_URL + word, String.class);
            JsonNode node = objectMapper.readTree(details);
            JsonNode meanings = node.get(0).get("meanings");

            for (var meaning : meanings) {
                String partOfSpeech = meaning.get("partOfSpeech").asText();
                JsonNode definitions = meaning.get("definitions");
                for (JsonNode def : definitions) {
                    String definition = def.get("definition").asText();
                    responses.add(new Response(definition, partOfSpeech));
                }
            }
        } catch (Exception e) {
            responses.add(new Response("No definition found", "N/A"));
        }
        return responses;
    }
}
