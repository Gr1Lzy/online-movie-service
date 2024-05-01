package com.github.onlinemovieservice.repository;

import com.github.onlinemovieservice.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
    @Query("SELECT m FROM Movie m JOIN m.genres g WHERE g.name = :genre")
    List<Movie> findAllByGenreName(String genre);

    @Query("SELECT m FROM Movie m JOIN m.director d WHERE d.firstName = :name")
    List<Movie> findMovieByDirectorName(String name);

    @Query("SELECT m FROM Movie m JOIN m.director d WHERE d.lastName = :surname")
    List<Movie> findMovieByDirectorSurname(String surname);

    @Query("SELECT m FROM Movie m JOIN m.director d WHERE d.nationality = :nationality")
    List<Movie> findMovieByDirectorNationality(String nationality);
}