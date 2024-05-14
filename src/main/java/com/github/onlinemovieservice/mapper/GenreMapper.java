package com.github.onlinemovieservice.mapper;

import com.github.onlinemovieservice.config.MapperConfig;
import com.github.onlinemovieservice.dto.genre.GenreDto;
import com.github.onlinemovieservice.dto.genre.GenreSaveDto;
import com.github.onlinemovieservice.model.Genre;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface GenreMapper {
    GenreDto toDto(Genre genre);

    Genre toModel(GenreSaveDto genreDto);
}
