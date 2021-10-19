package com.sofka.newreactivelibrary.usecases;


import com.sofka.newreactivelibrary.dtos.ResourceDTO;
import com.sofka.newreactivelibrary.mappers.ResourceMapper;
import com.sofka.newreactivelibrary.repositories.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class CreateResourceUseCase {
    private final ResourceRepository resourceRepository;
    private final ResourceMapper mapper;

    @Autowired
    public CreateResourceUseCase(ResourceRepository resourceRepository, ResourceMapper mapper) {
        this.resourceRepository = resourceRepository;
        this.mapper = mapper;
    }

    public Mono<ResourceDTO> createResource(ResourceDTO resource) {
        return resourceRepository.save(mapper.convertToEntity(resource)).flatMap(value -> Mono.just(mapper.convertToDTO(value)));
    }
}
