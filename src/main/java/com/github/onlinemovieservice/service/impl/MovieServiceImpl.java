package com.github.onlinemovieservice.service.impl;

import com.github.onlinemovieservice.dto.movie.MovieDto;
import com.github.onlinemovieservice.dto.movie.MovieSaveDto;
import com.github.onlinemovieservice.dto.movie.MovieWithoutGenreDto;
import com.github.onlinemovieservice.model.Movie;
import com.github.onlinemovieservice.repository.MovieRepository;
import com.github.onlinemovieservice.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper;

    @Override
    public MovieDto save(MovieSaveDto movieDto) {
        Movie newMovie = modelMapper.map(movieDto, Movie.class);

        return modelMapper.map(movieRepository.save(newMovie), MovieDto.class);
    }

    @Override
    public MovieDto update(Long id, MovieSaveDto movieDto) {
        getOrThrow(id);

        Movie updateMovie = modelMapper.map(movieDto, Movie.class);
        updateMovie.setId(id);

        return modelMapper.map(movieRepository.save(updateMovie), MovieDto.class);
    }



    @Override
    public List<MovieDto> findAll() {
        return movieRepository.findAll()
                .stream()
                .map(element -> modelMapper.map(element, MovieDto.class))
                .toList();
    }

    @Override
    public MovieDto findById(Long id) {
        Movie movie = getOrThrow(id);

        return modelMapper.map(movie, MovieDto.class);
    }

    @Override
    public List<MovieWithoutGenreDto> findByGenre(String name) {
        return movieRepository.findAll().stream()
                .filter(movie -> movie.getGenres().stream().anyMatch(genre -> genre.getName().equals(name)))
                .map(element -> modelMapper.map(element, MovieWithoutGenreDto.class))
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
