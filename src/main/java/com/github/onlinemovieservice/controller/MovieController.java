package com.github.onlinemovieservice.controller;

import com.github.onlinemovieservice.dto.director.DirectorSearchParameters;
import com.github.onlinemovieservice.dto.director.DirectorSearchParametersWithPageable;
import com.github.onlinemovieservice.dto.movie.MovieDto;
import com.github.onlinemovieservice.dto.movie.MovieSaveDto;
import com.github.onlinemovieservice.dto.movie.MovieWithoutGenreDto;
import com.github.onlinemovieservice.service.MovieService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/movies")
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
    public Page<List<MovieWithoutGenreDto>> getListMovie(
            @RequestBody DirectorSearchParametersWithPageable directorSearchParameters) {
        return movieService.search(directorSearchParameters);
    }

    @PostMapping(value = "/_report", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void generateReport(HttpServletResponse httpServletResponse,
                               @Validated @RequestBody DirectorSearchParameters directorSearchParameters) {
        movieService.generateReport(httpServletResponse, directorSearchParameters);
    }
}
