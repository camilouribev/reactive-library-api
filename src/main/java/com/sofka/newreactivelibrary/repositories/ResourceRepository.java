package com.sofka.newreactivelibrary.repositories;

import com.sofka.newreactivelibrary.collections.Resource;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends ReactiveMongoRepository<Resource, String> {


}