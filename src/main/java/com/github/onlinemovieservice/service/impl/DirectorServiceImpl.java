package com.github.onlinemovieservice.service.impl;

import com.github.onlinemovieservice.dto.director.DirectorDto;
import com.github.onlinemovieservice.dto.director.DirectorSaveDto;
import com.github.onlinemovieservice.mapper.DirectorMapper;
import com.github.onlinemovieservice.model.Director;
import com.github.onlinemovieservice.repository.DirectorRepository;
import com.github.onlinemovieservice.service.DirectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DirectorServiceImpl implements DirectorService {
    private final DirectorRepository directorRepository;
    private final DirectorMapper directorMapper;

    @Override
    public DirectorDto save(DirectorSaveDto directorDto) {
        Director newDirector = directorMapper.toModel(directorDto);

        return directorMapper.toDto(directorRepository.save(newDirector));
    }

    @Override
    public DirectorDto update(Long id, DirectorSaveDto directorDto) {
        getOrThrow(id);

        Director updateDirector = directorMapper.toModel(directorDto);
        updateDirector.setId(id);

        return directorMapper.toDto(directorRepository.save(updateDirector));
    }

    @Override
    public List<DirectorDto> findAll() {
        return directorRepository.findAll()
                .stream()
                .map(directorMapper::toDto)
                .toList();
    }

    @Override
    public DirectorDto findById(Long id) {
        Director director = getOrThrow(id);

        return directorMapper.toDto(director);
    }

    @Override
    public void deleteById(Long id) {
        directorRepository.deleteById(id);
    }

    private Director getOrThrow(Long id) {
        return directorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Director with id %d not found.".formatted(id)));
    }
}
