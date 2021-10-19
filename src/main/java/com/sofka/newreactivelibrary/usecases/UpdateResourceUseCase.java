package com.sofka.newreactivelibrary.usecases;

import com.sofka.newreactivelibrary.dtos.ResourceDTO;
import com.sofka.newreactivelibrary.mappers.ResourceMapper;
import com.sofka.newreactivelibrary.repositories.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@Service
@Validated
public class UpdateResourceUseCase {
    private final ResourceRepository resourceRepository;
    private final ResourceMapper mapper;

    @Autowired
    public UpdateResourceUseCase(ResourceRepository resourceRepository, ResourceMapper mapper) {
        this.resourceRepository = resourceRepository;
        this.mapper = mapper;
    }

    public Mono<ResourceDTO> updateResource(ResourceDTO resource) {

        return resourceRepository.existsById(resource.getId()).flatMap(resourceExists -> {
            if (resourceExists) {
                return resourceRepository.save(mapper.convertToEntity(resource)).flatMap(value -> Mono.just(mapper.convertToDTO(value)));
            } else {
                throw new NoSuchElementException();
            }

        });
    }


}
