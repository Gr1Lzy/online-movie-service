package com.github.onlinemovieservice.service;

import com.github.onlinemovieservice.dto.director.DirectorDto;
import com.github.onlinemovieservice.dto.director.DirectorSaveDto;

import java.util.List;

public interface DirectorService {
    DirectorDto save(DirectorSaveDto directorDto);

    DirectorDto update(Long id, DirectorSaveDto directorDto);

    List<DirectorDto> findAll();

    DirectorDto findById(Long id);

    void deleteById(Long id);
}
