package com.github.onlinemovieservice.service.impl;

import com.github.onlinemovieservice.dto.genre.GenreDto;
import com.github.onlinemovieservice.dto.genre.GenreSaveDto;
import com.github.onlinemovieservice.mapper.GenreMapper;
import com.github.onlinemovieservice.model.Genre;
import com.github.onlinemovieservice.repository.GenreRepository;
import com.github.onlinemovieservice.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Override
    public GenreDto save(GenreSaveDto genreDto) {
        Genre newGenre = genreMapper.toModel(genreDto);

        return genreMapper.toDto(genreRepository.save(newGenre));
    }

    @Override
    public GenreDto update(Long id, GenreSaveDto genreDto) {
        getOrThrow(id);

        Genre updateGenre = genreMapper.toModel(genreDto);
        updateGenre.setId(id);

        return genreMapper.toDto(genreRepository.save(updateGenre));
    }

    @Override
    public List<GenreDto> findAll() {
        return genreRepository.findAll()
                .stream()
                .map(genreMapper::toDto)
                .toList();
    }

    @Override
    public GenreDto findById(Long id) {
        Genre genre = getOrThrow(id);

        return genreMapper.toDto(genre);
    }

    @Override
    public void deleteById(Long id) {
        genreRepository.deleteById(id);
    }

    public Genre getOrThrow(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Genre with id %d not found.".formatted(id)));
    }

    public Set<Genre> getListOrThrow(Set<Long> genreIds) {
        Set<Genre> genres = genreIds.stream()
                .map(genreRepository::findById)
                .filter(Optional::isPresent)

                .map(Optional::get)
                .collect(Collectors.toSet());

        if (genres.size() != genreIds.size()) {
            throw new NoSuchElementException("One or more genres not found.");
        }

        return genres;
    }
}
