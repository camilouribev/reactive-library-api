package com.sofka.newreactivelibrary.routers;

import com.sofka.newreactivelibrary.dtos.ResourceDTO;
import com.sofka.newreactivelibrary.usecases.SpecificRecommendationUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class SpecificRecommendationRouter {
    @Bean
    public RouterFunction<ServerResponse> recommendByTypeAndGenre(SpecificRecommendationUseCase specificRecommendationUseCase) {
        return route(
                GET("/api/resources/recommendspecific/{type}/{genre}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(specificRecommendationUseCase.recommendByTypeAndGenre(request.pathVariable("type"), request.pathVariable("genre")), ResourceDTO.class)
                        ).onErrorResume((Error) -> ServerResponse.badRequest().build())
        );
    }
}
