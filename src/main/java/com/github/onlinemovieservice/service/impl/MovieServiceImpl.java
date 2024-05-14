package com.github.onlinemovieservice.service.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.onlinemovieservice.dto.director.DirectorSearchParameters;
import com.github.onlinemovieservice.dto.director.DirectorSearchParametersWithPageable;
import com.github.onlinemovieservice.dto.movie.MovieDto;
import com.github.onlinemovieservice.dto.movie.MovieSaveDto;
import com.github.onlinemovieservice.dto.movie.MovieWithoutGenreDto;
import com.github.onlinemovieservice.exception.EntityNotFoundException;
import com.github.onlinemovieservice.exception.FileCreateException;
import com.github.onlinemovieservice.exception.FileUploadException;
import com.github.onlinemovieservice.mapper.MovieMapper;
import com.github.onlinemovieservice.model.Director;
import com.github.onlinemovieservice.model.Genre;
import com.github.onlinemovieservice.model.Movie;
import com.github.onlinemovieservice.repository.DirectorRepository;
import com.github.onlinemovieservice.repository.MovieRepository;
import com.github.onlinemovieservice.repository.spec.impl.DirectorSpecificationBuilder;
import com.github.onlinemovieservice.service.MovieService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final DirectorServiceImpl directorService;
    private final DirectorRepository directorRepository;
    private final GenreServiceImpl genreService;
    private final DirectorSpecificationBuilder directorSpecificationBuilder;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)

            .registerModule(new JavaTimeModule());

    @Override
    public MovieDto save(@Valid MovieSaveDto movieDto) {
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
            List<MovieSaveDto> movies = objectMapper.readerForListOf(MovieSaveDto.class).readValue(file.getBytes());

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
    public void generateReport(HttpServletResponse response,
                               DirectorSearchParameters parameters) {
        try {
            response.setContentType("test/csv");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=movies.csv");

            Specification<Director> directorSpecification = directorSpecificationBuilder.build(parameters);
            List<Movie> movies = directorRepository.findAll(directorSpecification).stream()
                    .flatMap(director -> movieRepository.findAllByDirectorId(director.getId()).stream())
                    .toList();

            PrintWriter printWriter = response.getWriter();
            printWriter.println("Id,Title,ReleaseDate,Director,Genres");

            for (Movie movie : movies) {
                printWriter.println(movie.getId() + "," + movie.getTitle() + "," +
                        movie.getReleaseDate() + "," + movie.getDirector().getFirstName() + " " +
                        movie.getDirector().getLastName() + "," + movie.getGenres()
                        .stream().map(Genre::getName).toList());
            }
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            throw new FileCreateException(String.format("Error creating file %s", e.getMessage()));
        }
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
