package com.github.onlinemovieservice.service.impl;

import com.github.onlinemovieservice.dto.movie.MovieDto;
import com.github.onlinemovieservice.dto.movie.MovieSaveDto;
import com.github.onlinemovieservice.dto.movie.MovieWithoutGenreDto;
import com.github.onlinemovieservice.mapper.MovieMapper;
import com.github.onlinemovieservice.model.Director;
import com.github.onlinemovieservice.model.Genre;
import com.github.onlinemovieservice.model.Movie;
import com.github.onlinemovieservice.repository.DirectorRepository;
import com.github.onlinemovieservice.repository.GenreRepository;
import com.github.onlinemovieservice.repository.MovieRepository;
import com.github.onlinemovieservice.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final GenreRepository genreRepository;
    private final DirectorRepository directorRepository;

    @Override
    public MovieDto save(MovieSaveDto movieDto) {
        Set<Long> genresIds = movieDto.getGenresIds();
        Long directorId = movieDto.getDirectorId();

        Set<Genre> genres = genresIds.stream()
                .map(genreRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        Director director = directorRepository.findById(directorId)
                .orElseThrow(() -> new NoSuchElementException("Director with id %d not found.".formatted(directorId)));

        Movie newMovie = movieMapper.toModel(movieDto);
        newMovie.setDirector(director);
        newMovie.setGenres(genres);

        return movieMapper.toDto(movieRepository.save(newMovie));
    }

    @Override
    public MovieDto update(Long id, MovieSaveDto movieDto) {
        getOrThrow(id);

        Movie updateMovie = movieMapper.toModel(movieDto);
        updateMovie.setId(id);

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

    private Movie getOrThrow(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Movie with id %d not found.".formatted(id)));
    }
}
