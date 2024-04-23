package com.github.onlinemovieservice.service;

import com.github.onlinemovieservice.dto.genre.GenreDto;
import com.github.onlinemovieservice.dto.genre.GenreSaveDto;

import java.util.List;

public interface GenreService {
    GenreDto save(GenreSaveDto genre);
    GenreDto update(Long id, GenreSaveDto genre);
    List<GenreDto> findAll();
    GenreDto findById(Long id);
    GenreDto deleteById(Long id);
}
