package com.github.onlinemovieservice.repository;

import com.github.onlinemovieservice.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long>, JpaSpecificationExecutor<Director> {
    @Query("SELECT COUNT(d) > 0 FROM Director d WHERE d.firstName = ?1 AND d.lastName = ?2")
    boolean existsDirectorByFirstNameAndLastName(String firstName, String lastName);


}