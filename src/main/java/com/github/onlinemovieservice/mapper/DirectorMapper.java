package com.github.onlinemovieservice.mapper;

import com.github.onlinemovieservice.config.MapperConfig;
import com.github.onlinemovieservice.dto.director.DirectorDto;
import com.github.onlinemovieservice.dto.director.DirectorSaveDto;
import com.github.onlinemovieservice.model.Director;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface DirectorMapper {
    DirectorDto toDto(Director director);
    Director toModel(DirectorSaveDto directorDto);
}
