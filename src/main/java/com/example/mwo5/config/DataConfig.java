package com.example.mwo5.config;

import com.example.mwo5.model.Movie;
import com.example.mwo5.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataConfig {
    private final MovieRepository movieRepository;

    public void initData(){
        Movie movie = Movie.builder()
                .title("Film 1")
                .release(2022)
                .build();
        movieRepository.save(movie);
        movie = Movie.builder()
                .title("Film 2")
                .release(2023)
                .build();
        movieRepository.save(movie);
    }
}
