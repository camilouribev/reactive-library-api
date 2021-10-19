package com.sofka.newreactivelibrary.usecases;

import com.sofka.newreactivelibrary.dtos.ResourceDTO;
import com.sofka.newreactivelibrary.mappers.ResourceMapper;
import com.sofka.newreactivelibrary.repositories.ResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

@Service
@Validated
public class RecommendByGenreUseCase {
    private final ResourceRepository resourceRepository;
    private final ResourceMapper mapper;

    public RecommendByGenreUseCase(ResourceRepository resourceRepository, ResourceMapper mapper) {
        this.resourceRepository = resourceRepository;
        this.mapper = mapper;
    }

    public Flux<ResourceDTO> recommendByGenre(String genre) {
        return resourceRepository.findByGenre(genre).map(mapper::convertToDTO);
    }

}
