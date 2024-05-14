package com.github.onlinemovieservice.service;

import com.github.onlinemovieservice.dto.genre.GenreDto;
import com.github.onlinemovieservice.dto.genre.GenreSaveDto;

import java.util.List;

public interface GenreService {
    GenreDto save(GenreSaveDto genreDto);

    GenreDto update(Long id, GenreSaveDto genreDto);

    List<GenreDto> findAll();

    GenreDto findById(Long id);

    void deleteById(Long id);
}
