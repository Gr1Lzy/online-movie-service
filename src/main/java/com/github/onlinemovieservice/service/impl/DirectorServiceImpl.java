package com.github.onlinemovieservice.service.impl;

import com.github.onlinemovieservice.dto.director.DirectorDto;
import com.github.onlinemovieservice.dto.director.DirectorSaveDto;
import com.github.onlinemovieservice.model.Director;
import com.github.onlinemovieservice.repository.DirectorRepository;
import com.github.onlinemovieservice.service.DirectorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DirectorServiceImpl implements DirectorService {
    private final DirectorRepository directorRepository;
    private final ModelMapper modelMapper;

    @Override
    public DirectorDto save(DirectorSaveDto directorDto) {
        Director newDirector = modelMapper.map(directorDto, Director.class);

        return modelMapper.map(directorRepository.save(newDirector), DirectorDto.class);
    }

    @Override
    public DirectorDto update(Long id, DirectorSaveDto directorDto) {
        getOrThrow(id);

        Director updateDirector = modelMapper.map(directorDto, Director.class);
        updateDirector.setId(id);

        return modelMapper.map(directorRepository.save(updateDirector), DirectorDto.class);
    }

    @Override
    public List<DirectorDto> findAll() {
        return directorRepository.findAll()
                .stream()
                .map(element -> modelMapper.map(element, DirectorDto.class))
                .toList();
    }

    @Override
    public DirectorDto findById(Long id) {
        Director director = getOrThrow(id);

        return modelMapper.map(director, DirectorDto.class);
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
