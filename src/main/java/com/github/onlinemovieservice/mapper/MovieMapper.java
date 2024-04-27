package com.github.onlinemovieservice.mapper;

import com.github.onlinemovieservice.config.MapperConfig;
import com.github.onlinemovieservice.dto.movie.MovieDto;
import com.github.onlinemovieservice.dto.movie.MovieSaveDto;
import com.github.onlinemovieservice.dto.movie.MovieWithoutGenreDto;
import com.github.onlinemovieservice.model.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface MovieMapper {
    MovieDto toDto(Movie movie);

    @Mapping(target = "genres", ignore = true)
    Movie toModel(MovieSaveDto movieDto);

    MovieWithoutGenreDto toModelWithoutGenre(Movie movie);
}
