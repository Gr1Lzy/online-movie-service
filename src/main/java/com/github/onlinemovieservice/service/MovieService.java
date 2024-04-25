package com.github.onlinemovieservice.service;

import com.github.onlinemovieservice.dto.movie.MovieDto;
import com.github.onlinemovieservice.dto.movie.MovieSaveDto;
import com.github.onlinemovieservice.dto.movie.MovieWithoutGenreDto;

import java.util.List;

public interface MovieService {
    MovieDto save(MovieSaveDto movieDto);
    MovieDto update(Long id, MovieSaveDto movieDto);
    List<MovieDto> findAll();
    MovieDto findById(Long id);
    List<MovieWithoutGenreDto> findByGenre(String name);
    void deleteById(Long id);
}
