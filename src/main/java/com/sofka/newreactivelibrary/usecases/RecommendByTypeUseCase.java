package com.sofka.newreactivelibrary.usecases;

import com.sofka.newreactivelibrary.dtos.ResourceDTO;
import com.sofka.newreactivelibrary.mappers.ResourceMapper;
import com.sofka.newreactivelibrary.repositories.ResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

@Service
@Validated
public class RecommendByTypeUseCase {
    private final ResourceRepository resourceRepository;
    private final ResourceMapper mapper;

    public RecommendByTypeUseCase(ResourceRepository resourceRepository, ResourceMapper mapper) {
        this.resourceRepository = resourceRepository;
        this.mapper = mapper;
    }

    public Flux<ResourceDTO> recommendByType(String type) {
        return resourceRepository.findByType(type).map(mapper::convertToDTO);
    }


}
