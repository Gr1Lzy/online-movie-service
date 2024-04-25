package com.github.onlinemovieservice.service.impl;

import com.github.onlinemovieservice.dto.genre.GenreDto;
import com.github.onlinemovieservice.dto.genre.GenreSaveDto;
import com.github.onlinemovieservice.model.Genre;
import com.github.onlinemovieservice.repository.GenreRepository;
import com.github.onlinemovieservice.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    @Override
    public GenreDto save(GenreSaveDto genreDto) {
        Genre newGenre = modelMapper.map(genreDto, Genre.class);

        return modelMapper.map(genreRepository.save(newGenre), GenreDto.class);
    }

    @Override
    public GenreDto update(Long id, GenreSaveDto genreDto) {
        getOrThrow(id);

        Genre updateGenre = modelMapper.map(genreDto, Genre.class);
        updateGenre.setId(id);

        return modelMapper.map(genreRepository.save(updateGenre), GenreDto.class);
    }

    @Override
    public List<GenreDto> findAll() {
        return genreRepository.findAll()
                .stream()
                .map(element -> modelMapper.map(element, GenreDto.class))
                .toList();
    }

    @Override
    public GenreDto findById(Long id) {
        Genre genre = getOrThrow(id);

        return modelMapper.map(genre, GenreDto.class);
    }

    @Override
    public void deleteById(Long id) {
        genreRepository.deleteById(id);
    }

    private Genre getOrThrow(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Genre with id %d not found.".formatted(id)));
    }
}
