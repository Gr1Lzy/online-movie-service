package com.github.onlinemovieservice.repository.spec.impl;

import com.github.onlinemovieservice.model.Director;
import com.github.onlinemovieservice.repository.spec.SpecificationProvider;
import com.github.onlinemovieservice.repository.spec.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class BookSpecificationProviderManager implements SpecificationProviderManager<Director> {
    private final List<SpecificationProvider<Director>> directorSpecificationProviders;

    @Override
    public SpecificationProvider<Director> getSpecificationProvider(String key) {
        return directorSpecificationProviders.stream()
                .filter(p -> p.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Cannot find correct specification" +
                        "provider of key" + key));
    }
}
