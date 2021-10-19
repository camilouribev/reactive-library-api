package com.sofka.newreactivelibrary.routers;

import com.sofka.newreactivelibrary.dtos.ResourceDTO;
import com.sofka.newreactivelibrary.usecases.ResourceUseCases;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ResourceRouters {

    @Bean
    public RouterFunction<ServerResponse> createResource(ResourceUseCases resourceUseCases) {
        return route(POST("/api/new").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(ResourceDTO.class)
                        .flatMap(resDTO -> resourceUseCases.createResource(resDTO)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                        )
        );
    }

    @Bean
    public RouterFunction<ServerResponse> getAll(ResourceUseCases resourceUseCases) {
        return route(
                GET("/api/resources").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(resourceUseCases.getAllResources(), ResourceDTO.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> getResourceById(ResourceUseCases resourceUseCases) {
        return route(
                GET("/api/resources/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(resourceUseCases.getResourceById(request.pathVariable("id")), ResourceDTO.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> updateResource(ResourceUseCases resourceUseCases) {

        return route(PUT("/api/resources").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(ResourceDTO.class)
                        .flatMap(resDTO -> resourceUseCases.updateResource( resDTO)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                        )
        );
    }

    @Bean
    public RouterFunction<ServerResponse> delete(ResourceUseCases resourceUseCases) {
        return route(
                DELETE("/api/resources/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(resourceUseCases.deleteResource(request.pathVariable("id")), Void.class))
        );
    }


    @Bean
    public RouterFunction<ServerResponse> checkIfResourceIsAvailable(ResourceUseCases resourceUseCases) {
        return route(
                GET("/api/resources/{id}/isavailable"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(resourceUseCases.checkIfResourceIsAvailable(request.pathVariable("id")), String.class)
                        ).onErrorResume( Error -> ServerResponse.badRequest().build())

        );
    }



}
