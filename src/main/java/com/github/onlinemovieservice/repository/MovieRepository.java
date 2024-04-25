package com.github.onlinemovieservice.repository;

import com.github.onlinemovieservice.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("SELECT m FROM Movie m JOIN m.genres g WHERE g.name = :genre")
    List<Movie> findAllByGenreName(String genre);
}