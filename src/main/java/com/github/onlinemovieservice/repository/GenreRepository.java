package com.github.onlinemovieservice.repository;

import com.github.onlinemovieservice.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {}