package com.sofka.newreactivelibrary.routers;

import com.sofka.newreactivelibrary.usecases.BorrowResourceUseCases;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class BorrowResourceRouter {
    @Bean
    public RouterFunction<ServerResponse> borrowResource(BorrowResourceUseCases borrowResourceUseCase) {
        return route(
                PUT("/api/resources/{id}/borrow"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(borrowResourceUseCase.borrowResource(request.pathVariable("id")), String.class))
                        .onErrorResume((error) -> ServerResponse.badRequest().build())
        );
    }
}
