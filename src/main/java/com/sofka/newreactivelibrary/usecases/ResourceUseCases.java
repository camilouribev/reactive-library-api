package com.sofka.newreactivelibrary.usecases;

import com.sofka.newreactivelibrary.collections.Resource;
import com.sofka.newreactivelibrary.dtos.ResourceDTO;
import com.sofka.newreactivelibrary.mappers.ResourceMapper;
import com.sofka.newreactivelibrary.repositories.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Validated
public class ResourceUseCases {

    private final ResourceRepository resourceRepository;
    private final ResourceMapper mapper;

    @Autowired
    public ResourceUseCases(ResourceRepository resourceRepository, ResourceMapper mapper) {
        this.resourceRepository = resourceRepository;
        this.mapper = mapper;
    }


    public Flux<ResourceDTO> getAllResources() {
        return resourceRepository.findAll().switchIfEmpty(Flux.empty()).map(mapper::convertToDTO);
    }


    public Mono<ResourceDTO> createResource(ResourceDTO resource) {
        return resourceRepository.save(mapper.convertToEntity(resource)).flatMap(value -> Mono.just(mapper.convertToDTO(value)));
    }

    public Mono<ResourceDTO> getResourceById(String id) {
        return resourceRepository.findById(id).map(mapper::convertToDTO);
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

    public Mono<Void> deleteResource(String id) {
        return resourceRepository.deleteById(id);
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

    public Mono<String> borrowResource(String id) {
        return resourceRepository.findById(id).flatMap(
                resource -> {
                    if (resource.getTotalCopies() > resource.getBorrowedCopies()) {
                        resource.setLoanDate(LocalDate.now());
                        resource.setBorrowedCopies(resource.getBorrowedCopies() + 1);
                        return updateResource(mapper.convertToDTO(resource)).thenReturn("You borrowed a copy of the" + resource.getType() + " " + resource.getName() + " on " + resource.getLoanDate());
                    }
                    return Mono.just("All copies of the " + resource.getType() + " " + resource.getName() + " are borrowed.") ;
                }
        );
    }

    public Mono<String> returnResource(String id) {
        return resourceRepository.findById(id).flatMap(
                resource -> {
                    if (resource.getBorrowedCopies() > 0) {
                        resource.setBorrowedCopies(resource.getBorrowedCopies() - 1);
                        return updateResource(mapper.convertToDTO(resource)).thenReturn("You returned a copy of the " + resource.getType() + " " + resource.getName());
                    }
                    return Mono.just("No copies of the " + resource.getType() + " " + resource.getName() + " are available.") ;
                }
        );
    }






}
