package com.github.onlinemovieservice.service;

import com.github.onlinemovieservice.dto.director.DirectorDto;
import com.github.onlinemovieservice.dto.director.DirectorSaveDto;

import java.util.List;

public interface DirectorService {
    DirectorDto save(DirectorSaveDto director);
    DirectorDto update(Long id, DirectorSaveDto director);
    List<DirectorDto> findAll();
    DirectorDto findById(Long id);
    DirectorDto deleteById(Long id);
}
