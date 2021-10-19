package com.sofka.newreactivelibrary.usecases;

import com.sofka.newreactivelibrary.mappers.ResourceMapper;
import com.sofka.newreactivelibrary.repositories.ResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class CheckIfResourceIsAvailableUseCase {
    private final ResourceRepository resourceRepository;
    private final ResourceMapper mapper;

    public CheckIfResourceIsAvailableUseCase(ResourceRepository resourceRepository, ResourceMapper mapper) {
        this.resourceRepository = resourceRepository;
        this.mapper = mapper;
    }
    public Mono<String> checkIfResourceIsAvailable(String id) {
        return resourceRepository.findById(id)
                .map(resource ->  resource.getTotalCopies() > resource.getBorrowedCopies()
                        ? "The " + resource.getType() + " " + resource.getName() + " is available. There are "
                        + (resource.getTotalCopies() - resource.getBorrowedCopies()) + " copies remaining."
                        :
                        "All copies of the " + resource.getType() + " " + resource.getName() + " are borrowed." +
                                " The last copy was borrowed on " + resource.getLoanDate());

    }
}
