package com.sofka.newreactivelibrary.routers;

import com.sofka.newreactivelibrary.usecases.ReturnResourceUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ReturnResourceRouter {

      @Bean
    public RouterFunction<ServerResponse> returnResource(ReturnResourceUseCase returnResourceUseCase) {
        return route(
                PUT("/api/resources/{id}/return"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(returnResourceUseCase.returnResource(request.pathVariable("id")), String.class))
                        .onErrorResume((error) -> ServerResponse.badRequest().build())
        );
    }
}
