package com.sofka.newreactivelibrary.usecases;

import com.sofka.newreactivelibrary.mappers.ResourceMapper;
import com.sofka.newreactivelibrary.repositories.ResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@Validated
public class BorrowResourceUseCases {
    private final ResourceRepository resourceRepository;
    private final ResourceMapper mapper;
    private final UpdateResourceUseCase updateResourceUseCase;

    public BorrowResourceUseCases(ResourceRepository resourceRepository, ResourceMapper mapper, UpdateResourceUseCase updateResourceUseCase) {
        this.resourceRepository = resourceRepository;
        this.mapper = mapper;
        this.updateResourceUseCase = updateResourceUseCase;
    }

      public Mono<String> borrowResource(String id) {
        return resourceRepository.findById(id).flatMap(
                resource -> {
                    if (resource.getTotalCopies() > resource.getBorrowedCopies()) {
                        resource.setLoanDate(LocalDate.now());
                        resource.setBorrowedCopies(resource.getBorrowedCopies() + 1);
                        return  updateResourceUseCase.updateResource(mapper.convertToDTO(resource)).thenReturn("You borrowed a copy of the" + resource.getType() + " " + resource.getName() + " on " + resource.getLoanDate());
                    }
                    return Mono.just("All copies of the " + resource.getType() + " " + resource.getName() + " are borrowed.") ;
                }
        );
    }
}
