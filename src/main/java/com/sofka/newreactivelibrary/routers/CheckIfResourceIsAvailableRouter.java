package com.sofka.newreactivelibrary.routers;

import com.sofka.newreactivelibrary.usecases.CheckIfResourceIsAvailableUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class CheckIfResourceIsAvailableRouter {
    @Bean
    public RouterFunction<ServerResponse> checkIfResourceIsAvailable(CheckIfResourceIsAvailableUseCase checkIfResourceIsAvailableUseCase) {
        return route(
                GET("/api/resources/{id}/isavailable"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(checkIfResourceIsAvailableUseCase.checkIfResourceIsAvailable(request.pathVariable("id")), String.class)
                        ).onErrorResume( Error -> ServerResponse.badRequest().build())

        );
    }
}
