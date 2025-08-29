package com.AsadUllah.WordOfTheDay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WordOfTheDayApplication {

	public static void main(String[] args) {
		SpringApplication.run(WordOfTheDayApplication.class, args);
	}

}
