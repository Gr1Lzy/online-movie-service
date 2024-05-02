package com.github.onlinemovieservice.repository.spec.impl;

import com.github.onlinemovieservice.dto.director.DirectorParameters;
import com.github.onlinemovieservice.dto.director.DirectorSearchParametersWithPageable;
import com.github.onlinemovieservice.model.Director;
import com.github.onlinemovieservice.repository.spec.SpecificationBuilder;
import com.github.onlinemovieservice.repository.spec.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DirectorSpecificationBuilder implements SpecificationBuilder<Director> {
    private static final String NAME_KEY = "firstName";
    private static final String SURNAME_KEY = "lastName";
    private static final String NATIONALITY_KEY = "nationality";
    private final SpecificationProviderManager<Director> directorSpecificationProviderManager;

    @Override
    public Specification<Director> build(DirectorParameters searchParameters) {
        Specification<Director> specification = Specification.where(null);
        if (searchParameters.getFirstName() != null && !searchParameters.getFirstName().isEmpty()) {
            specification = specification.and(directorSpecificationProviderManager.getSpecificationProvider(NAME_KEY)
                    .getSpecification(searchParameters.getFirstName()));
        }

        if (searchParameters.getLastName() != null && !searchParameters.getLastName().isEmpty()) {
            specification = specification.and(directorSpecificationProviderManager.getSpecificationProvider(SURNAME_KEY)
                    .getSpecification(searchParameters.getLastName()));
        }

        if (searchParameters.getNationality() != null && !searchParameters.getNationality().isEmpty()) {
            specification = specification.and(directorSpecificationProviderManager.getSpecificationProvider(NATIONALITY_KEY)
                    .getSpecification(searchParameters.getNationality()));
        }

        return specification;
    }
}
