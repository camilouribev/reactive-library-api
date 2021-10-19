package com.sofka.newreactivelibrary.usecases;

import com.sofka.newreactivelibrary.dtos.ResourceDTO;
import com.sofka.newreactivelibrary.mappers.ResourceMapper;
import com.sofka.newreactivelibrary.repositories.ResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

@Service
@Validated
public class SpecificRecommendationUseCase {
    private final ResourceRepository resourceRepository;
    private final ResourceMapper mapper;

    public SpecificRecommendationUseCase(ResourceRepository resourceRepository, ResourceMapper mapper) {
        this.resourceRepository = resourceRepository;
        this.mapper = mapper;
    }
    public Flux<ResourceDTO> recommendByTypeAndGenre(String type, String genre) {

        return resourceRepository.findByTypeAndGenre(type, genre).map(mapper::convertToDTO).distinct();


    }
}
