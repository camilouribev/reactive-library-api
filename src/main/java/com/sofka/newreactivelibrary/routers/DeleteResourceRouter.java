package com.sofka.newreactivelibrary.routers;

import com.sofka.newreactivelibrary.usecases.DeleteResourceUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class DeleteResourceRouter {
    @Bean
    public RouterFunction<ServerResponse> delete(DeleteResourceUseCase deleteResourceUseCase) {
        return route(
                DELETE("/api/resources/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(deleteResourceUseCase.deleteResource(request.pathVariable("id")), Void.class))
        );
    }
}
