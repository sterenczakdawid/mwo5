package com.example.mwo5.service;

import com.example.mwo5.model.Movie;
import com.example.mwo5.repository.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id){
        return movieRepository.findById(id).orElseThrow( () -> new EntityNotFoundException("Movie has not been found"));
    }

    public void addMovie(Movie movie){
        movieRepository.save(movie);
    }

    public void updateMovie(Long id, Movie movie){
        if( movieRepository.existsById(id) ){
            movieRepository.save(movie);
        } else {
            throw new EntityNotFoundException("Movie has not been found");
        }
    }

    public void deleteMovie(Long id){
        movieRepository.deleteById(id);
    }
}
