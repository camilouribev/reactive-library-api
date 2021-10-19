package com.sofka.newreactivelibrary.usecases;

import com.sofka.newreactivelibrary.dtos.ResourceDTO;
import com.sofka.newreactivelibrary.mappers.ResourceMapper;
import com.sofka.newreactivelibrary.repositories.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

@Service
@Validated
public class GetAllResourcesUseCase {
    private final ResourceRepository resourceRepository;
    private final ResourceMapper mapper;

    @Autowired
    public GetAllResourcesUseCase(ResourceRepository resourceRepository, ResourceMapper mapper) {
        this.resourceRepository = resourceRepository;
        this.mapper = mapper;
    }


    public Flux<ResourceDTO> getAllResources() {
        return resourceRepository.findAll().switchIfEmpty(Flux.empty()).map(mapper::convertToDTO);
    }


}
