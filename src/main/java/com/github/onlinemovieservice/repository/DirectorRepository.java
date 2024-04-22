package com.github.onlinemovieservice.repository;

import com.github.onlinemovieservice.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRepository extends JpaRepository<Director, Long> {}