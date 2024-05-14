package com.github.onlinemovieservice.mapper;

import com.github.onlinemovieservice.config.MapperConfig;
import com.github.onlinemovieservice.dto.movie.MovieDto;
import com.github.onlinemovieservice.dto.movie.MovieSaveDto;
import com.github.onlinemovieservice.dto.movie.MovieWithoutGenreDto;
import com.github.onlinemovieservice.model.Movie;
import com.github.onlinemovieservice.service.DirectorService;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfig.class, uses = DirectorService.class)
public interface MovieMapper {
    MovieDto toDto(Movie movie);

    Movie toModel(MovieSaveDto movieDto);

    MovieWithoutGenreDto toDtoWithoutGenre(Movie movie);

    List<MovieWithoutGenreDto> toDtoListWithoutGenre(List<Movie> movies);
}