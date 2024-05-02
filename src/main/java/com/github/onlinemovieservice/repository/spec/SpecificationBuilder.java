package com.github.onlinemovieservice.repository.spec;

import com.github.onlinemovieservice.dto.director.DirectorParameters;
import com.github.onlinemovieservice.dto.director.DirectorSearchParametersWithPageable;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(DirectorParameters directorParameters);
}
