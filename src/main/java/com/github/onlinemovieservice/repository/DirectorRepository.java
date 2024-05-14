package com.github.onlinemovieservice.repository;

import com.github.onlinemovieservice.model.Director;
import io.micrometer.common.lang.NonNull;
import io.micrometer.common.lang.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long>, JpaSpecificationExecutor<Director> {
    @Query("SELECT COUNT(d) > 0 FROM Director d WHERE d.firstName = ?1 AND d.lastName = ?2")
    boolean existsDirectorByFirstNameAndLastName(String firstName, String lastName);

    @Override
    @NonNull
    List<Director> findAll(@Nullable Specification<Director> specification);

    @Override
    @NonNull
    Page<Director> findAll(@Nullable Specification<Director> specification, @NonNull Pageable pageable);
}