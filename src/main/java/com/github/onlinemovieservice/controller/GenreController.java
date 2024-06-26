package com.github.onlinemovieservice.controller;

import com.github.onlinemovieservice.dto.genre.GenreDto;
import com.github.onlinemovieservice.dto.genre.GenreSaveDto;
import com.github.onlinemovieservice.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/genres")
public class GenreController {
    private final GenreService genreService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenreDto createGenre(@Validated @RequestBody GenreSaveDto genreDto) {
        return genreService.save(genreDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable Long id) {
        genreService.deleteById(id);
    }

    @GetMapping
    public List<GenreDto> getAll() {
        return genreService.findAll();
    }
}
