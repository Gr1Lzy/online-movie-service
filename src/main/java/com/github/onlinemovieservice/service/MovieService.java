package com.github.onlinemovieservice.service;

import com.github.onlinemovieservice.dto.movie.MovieDto;
import com.github.onlinemovieservice.dto.movie.MovieSaveDto;

import java.util.List;

public interface MovieService {
    MovieDto save(MovieSaveDto movieDto);
    MovieDto update(Long id, MovieSaveDto movieDto);
    List<MovieDto> findAll();
    MovieDto findById(Long id);
    void deleteById(Long id);
}
