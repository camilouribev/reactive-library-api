package com.sofka.newreactivelibrary.usecases;

import com.sofka.newreactivelibrary.mappers.ResourceMapper;
import com.sofka.newreactivelibrary.repositories.ResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class ReturnResourceUseCase {
    private final ResourceRepository resourceRepository;
    private final ResourceMapper mapper;
    private final UpdateResourceUseCase updateResourceUseCase;

    public ReturnResourceUseCase(ResourceRepository resourceRepository, ResourceMapper mapper, UpdateResourceUseCase updateResourceUseCase) {
        this.resourceRepository = resourceRepository;
        this.mapper = mapper;
        this.updateResourceUseCase = updateResourceUseCase;
    }

    public Mono<String> returnResource(String id) {
        return resourceRepository.findById(id).flatMap(
                resource -> {
                    if (resource.getBorrowedCopies() > 0) {
                        resource.setBorrowedCopies(resource.getBorrowedCopies() - 1);
                        return updateResourceUseCase.updateResource(mapper.convertToDTO(resource)).thenReturn("You returned a copy of the " + resource.getType() + " " + resource.getName());
                    }
                    return Mono.just("No copies of the " + resource.getType() + " " + resource.getName() + " are available.") ;
                }
        );
    }
}
