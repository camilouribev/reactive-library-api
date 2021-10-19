package com.sofka.newreactivelibrary.repositories;

import com.sofka.newreactivelibrary.collections.Resource;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;


@Repository
public interface ResourceRepository extends ReactiveMongoRepository<Resource, String> {
    Flux<Resource> findByType(String type);

    Flux<Resource> findByGenre(String genre);
    Flux<Resource> findByTypeAndGenre (final String type, final String genre);

}