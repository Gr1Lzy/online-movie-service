package com.github.onlinemovieservice.repository.spec;

import com.github.onlinemovieservice.dto.director.DirectorSearchParameters;
import com.github.onlinemovieservice.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DirectorSpecification {
    public Specification<Movie> getSpecification(DirectorSearchParameters parameters) {

        if (parameters.getFirstName() != null && !parameters.getFirstName().isEmpty()) {
            return ((root, query, criteriaBuilder) -> root.get("firstName").in(parameters.getFirstName()));
        }

        if (parameters.getLastName() != null && !parameters.getLastName().isEmpty()) {
            return ((root, query, criteriaBuilder) -> root.get("lastName").in(parameters.getFirstName()));
        }

        if (parameters.getNationality() != null && !parameters.getNationality().isEmpty()) {
            return ((root, query, criteriaBuilder) -> root.get("nationality").in(parameters.getFirstName()));
        }

        return null;
    }
}
