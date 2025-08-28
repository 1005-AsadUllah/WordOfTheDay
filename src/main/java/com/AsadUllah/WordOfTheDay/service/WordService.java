package com.AsadUllah.WordOfTheDay.service;

import com.AsadUllah.WordOfTheDay.Model.Response;
import com.AsadUllah.WordOfTheDay.Model.WordResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class WordService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String Random_Word_URL = "https://random-word-api.herokuapp.com/word";
    private static final String Dictionary_API_URL = "https://api.dictionaryapi.dev/api/v2/entries/en/";

    public <T>WordResponse<T> getWord() {
        String[] words = restTemplate.getForObject(Random_Word_URL, String[].class);
        String word = (words != null && words.length > 0) ? words[0] : "example";

        List<T> responses = new ArrayList<>();

        try {
            String details = restTemplate.getForObject(Dictionary_API_URL + word, String.class);
            JsonNode node = objectMapper.readTree(details);

            JsonNode meanings = node.get(0).get("meanings");
            for(var meaning : meanings) {
                String partOfSpeech = meaning.get("partOfSpeech").asText();
                JsonNode definitions = meaning.get("definitions");

                for(JsonNode def : definitions) {
                    String definition = def.get("definition").asText();
                    responses.add((T) new Response(definition, partOfSpeech) );
                }
            }
        }
        catch (Exception e) {
           responses.add((T) new Response( "No definition found", "N/A"));

        }
        return new WordResponse<>(word, responses);
    }



}
