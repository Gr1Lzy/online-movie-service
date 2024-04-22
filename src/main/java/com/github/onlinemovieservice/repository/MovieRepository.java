package com.github.onlinemovieservice.repository;

import com.github.onlinemovieservice.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {}