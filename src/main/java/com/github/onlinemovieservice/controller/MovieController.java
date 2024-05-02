package com.github.onlinemovieservice.controller;

import com.github.onlinemovieservice.dto.director.DirectorSearchParametersWithPageable;
import com.github.onlinemovieservice.dto.movie.MovieDto;
import com.github.onlinemovieservice.dto.movie.MovieSaveDto;
import com.github.onlinemovieservice.dto.movie.MovieWithoutGenreDto;
import com.github.onlinemovieservice.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/movie")
public class MovieController {
    private final MovieService movieService;

    @GetMapping
    public List<MovieDto> getAll() {
        return movieService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDto createMovie(@Validated @RequestBody MovieSaveDto movieDto) {
        return movieService.save(movieDto);
    }

    @PostMapping( "/upload")
    public ResponseEntity<String> uploadMovie(@RequestParam("file") MultipartFile multipartFile) {
        return ResponseEntity.ok(movieService.uploadFromFile(multipartFile));
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

    @PostMapping("/_list")
    public Page<List<MovieWithoutGenreDto>> getListMovie(@RequestBody DirectorSearchParametersWithPageable directorSearchParameters) {
        return movieService.search(directorSearchParameters);
    }

    @PostMapping("/_report")
    public void reportMovie() {
        //todo write to file with Filtration
    }
}
