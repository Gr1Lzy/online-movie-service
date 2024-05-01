package com.github.onlinemovieservice.controller;

import com.github.onlinemovieservice.dto.movie.MovieDto;
import com.github.onlinemovieservice.dto.movie.MovieSaveDto;
import com.github.onlinemovieservice.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/movie")
public class MovieController {
    private final MovieService movieService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDto createMovie(@Validated @RequestBody MovieSaveDto movieDto) {
        return movieService.save(movieDto);
    }

    @GetMapping("/{id}")
    public MovieDto getMovie(@PathVariable Long id) {
        return movieService.findById(id);
    }

    @PostMapping("/{id}")
    public MovieDto updateMovie(@PathVariable Long id, @Validated @RequestBody MovieSaveDto movieDto) {
        return movieService.update(id, movieDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteById(id);
    }
}
