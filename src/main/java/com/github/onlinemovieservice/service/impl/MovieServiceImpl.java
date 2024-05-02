package com.github.onlinemovieservice.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.onlinemovieservice.dto.director.DirectorSearchParametersWithPageable;
import com.github.onlinemovieservice.dto.movie.MovieDto;
import com.github.onlinemovieservice.dto.movie.MovieSaveDto;
import com.github.onlinemovieservice.dto.movie.MovieWithoutGenreDto;
import com.github.onlinemovieservice.exception.EntityNotFoundException;
import com.github.onlinemovieservice.exception.FileUploadException;
import com.github.onlinemovieservice.mapper.MovieMapper;
import com.github.onlinemovieservice.model.Director;
import com.github.onlinemovieservice.model.Movie;
import com.github.onlinemovieservice.repository.DirectorRepository;
import com.github.onlinemovieservice.repository.MovieRepository;
import com.github.onlinemovieservice.repository.spec.impl.DirectorSpecificationBuilder;
import com.github.onlinemovieservice.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final DirectorServiceImpl directorService;
    private final DirectorRepository directorRepository;
    private final GenreServiceImpl genreService;
    private final ObjectMapper objectMapper;
    private final DirectorSpecificationBuilder directorSpecificationBuilder;

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
    public String uploadFromFile(MultipartFile file) {
        int failedImports = 0;
        int successfulImports = 0;

        try {
            List<MovieSaveDto> movies = objectMapper.readValue(file.getInputStream(), new TypeReference<>() {
            });

            for (MovieSaveDto movie : movies) {
                try {
                    save(movie);
                    successfulImports++;
                } catch (Exception e) {
                    failedImports++;
                }
            }
            return String.format("{\"successfulImports\": %d, \"failedImports\": %d}", successfulImports, failedImports);

        } catch (IOException e) {
            throw new FileUploadException(e);
        }

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
    public Page<List<MovieWithoutGenreDto>> search(DirectorSearchParametersWithPageable parameters) {
        Specification<Director> directorSpecification = directorSpecificationBuilder.build(parameters);
        PageRequest pageable = PageRequest.of(parameters.getPage(), parameters.getSize());
        return directorRepository.findAll(directorSpecification, pageable)
                .map(director -> {
                    List<Movie> moviesByDirector = movieRepository.findAllByDirectorId(director.getId());
                    return movieMapper.toDtoListWithoutGenre(moviesByDirector);
                });
    }

    @Override
    public List<MovieWithoutGenreDto> findByGenre(String name) {
        return movieRepository.findAllByGenreName(name)
                .stream()
                .map(movieMapper::toDtoWithoutGenre)
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
