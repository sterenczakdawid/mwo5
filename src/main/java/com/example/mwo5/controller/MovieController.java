package com.example.mwo5.controller;

import com.example.mwo5.model.Movie;
import com.example.mwo5.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/v1/movies")
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/all")
    public String getMovies(Model model){
        model.addAttribute("list", movieService.getAllMovies());
        return "movies";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "add-form";
    }

    @PostMapping("/add")
    public String addMovie(@ModelAttribute Movie movie) {
        movieService.addMovie(movie);
        return "redirect:/api/v1/movies/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return "redirect:/api/v1/movies/all";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Movie movie = movieService.getMovieById(id);
        model.addAttribute("movie", movie);

        return "edit-form";
    }

    @PostMapping("/update/{id}")
    public String updateBeer(@PathVariable Long id, @ModelAttribute Movie movie) {
        movieService.updateMovie(id, movie);
        return "redirect:/api/v1/movies/all";
    }
}
