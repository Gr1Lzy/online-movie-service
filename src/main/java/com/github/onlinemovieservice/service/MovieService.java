package com.github.onlinemovieservice.service;

import com.github.onlinemovieservice.dto.director.DirectorSearchParameters;
import com.github.onlinemovieservice.dto.movie.MovieDto;
import com.github.onlinemovieservice.dto.movie.MovieSaveDto;
import com.github.onlinemovieservice.dto.movie.MovieWithoutGenreDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MovieService {
    MovieDto save(MovieSaveDto movieDto);
    MovieDto update(Long id, MovieSaveDto movieDto);
    String uploadFromFile(MultipartFile file);
    List<MovieDto> findAll();
    MovieDto findById(Long id);
    List<MovieWithoutGenreDto> findByDirectorCriteria(DirectorSearchParameters directorSearchParameters);
    List<MovieWithoutGenreDto> findByGenre(String name);
    void deleteById(Long id);
}
