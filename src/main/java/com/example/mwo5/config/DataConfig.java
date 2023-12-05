package com.example.mwo5.config;

import com.example.mwo5.model.Movie;
import com.example.mwo5.repository.MovieRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Random;

@Service
public class DataConfig {

    @Bean
    CommandLineRunner commandLineRunner(MovieRepository movieRepository) {
        return args -> {
            Random random = new Random(987654321);
            Faker faker = new Faker(Locale.ENGLISH, random);
            for(int i=0; i<10; i++) {
                Movie fakeMovie = generateFakeMovie(faker);
                movieRepository.save(fakeMovie);
            }
        };
    }

    private Movie generateFakeMovie( Faker faker ) {
        Movie movie = new Movie();
        movie.setTitle(faker.book().title());
        movie.setRelease(faker.number().numberBetween(1970, 2023));
        return movie;
    }
}
