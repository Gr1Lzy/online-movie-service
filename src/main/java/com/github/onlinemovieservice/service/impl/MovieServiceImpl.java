package com.github.onlinemovieservice.service.impl;

import com.github.onlinemovieservice.dto.movie.MovieDto;
import com.github.onlinemovieservice.dto.movie.MovieSaveDto;
import com.github.onlinemovieservice.dto.movie.MovieWithoutGenreDto;
import com.github.onlinemovieservice.exception.EntityNotFoundException;
import com.github.onlinemovieservice.mapper.MovieMapper;
import com.github.onlinemovieservice.model.Movie;
import com.github.onlinemovieservice.repository.MovieRepository;
import com.github.onlinemovieservice.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final DirectorServiceImpl directorService;
    private final GenreServiceImpl genreService;

    @Override
    public MovieDto save(MovieSaveDto movieDto) {
        Movie newMovie = movieMapper.toModel(movieDto);

        newMovie.setDirector(directorService.getDirectorOrThrow(movieDto.getDirectorId()));
        newMovie.setGenres(genreService.getListOrThrow(movieDto.getGenresIds()));

        return movieMapper.toDto(movieRepository.save(newMovie));
    }

    @Override
    public MovieDto update(Long id, MovieSaveDto movieDto) {
        getOrThrow(id);

        Movie updateMovie = movieMapper.toModel(movieDto);

        updateMovie.setId(id);
        updateMovie.setDirector(directorService.getDirectorOrThrow(movieDto.getDirectorId()));
        updateMovie.setGenres(genreService.getListOrThrow(movieDto.getGenresIds()));

        return movieMapper.toDto(movieRepository.save(updateMovie));
    }

    @Override
    public List<MovieDto> findAll() {
        return movieRepository.findAll()
                .stream()
                .map(movieMapper::toDto)
                .toList();
    }

    @Override
    public MovieDto findById(Long id) {
        Movie movie = getOrThrow(id);

        return movieMapper.toDto(movie);
    }

    @Override
    public List<MovieWithoutGenreDto> findByGenre(String name) {
        return movieRepository.findAllByGenreName(name)
                .stream()
                .map(movieMapper::toModelWithoutGenre)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        movieRepository.deleteById(id);
    }

    public Movie getOrThrow(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movie with id %d not found.".formatted(id)));
    }
}
