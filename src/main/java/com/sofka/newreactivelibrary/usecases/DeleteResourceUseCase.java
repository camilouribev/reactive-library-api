package com.sofka.newreactivelibrary.usecases;

import com.sofka.newreactivelibrary.mappers.ResourceMapper;
import com.sofka.newreactivelibrary.repositories.ResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class DeleteResourceUseCase {
    private final ResourceRepository resourceRepository;
    private final ResourceMapper mapper;

    public DeleteResourceUseCase(ResourceRepository resourceRepository, ResourceMapper mapper) {
        this.resourceRepository = resourceRepository;
        this.mapper = mapper;
    }

    public Mono<Void> deleteResource(String id) {
        return resourceRepository.deleteById(id);
    }

}
