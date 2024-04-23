package com.github.onlinemovieservice.service;

import com.github.onlinemovieservice.dto.movie.MovieDto;
import com.github.onlinemovieservice.dto.movie.MovieSaveDto;

import java.util.List;

public interface MovieService {
    MovieDto save(MovieDto movie);
    MovieDto update(Long id, MovieSaveDto genre);
    List<MovieDto> findAll();
    MovieDto findById(Long id);
    MovieDto deleteById(Long id);
}
