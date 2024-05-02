package com.github.onlinemovieservice.repository.spec.director;

import com.github.onlinemovieservice.model.Director;
import com.github.onlinemovieservice.repository.spec.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class NationalitySpecification implements SpecificationProvider<Director> {
    private final String KEY = "nationality";

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public Specification<Director> getSpecification(String param) {
        return (root, query, criteriaBuilder) -> root.get(KEY)
                .in(param);
    }
}
