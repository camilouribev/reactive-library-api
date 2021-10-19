package com.sofka.newreactivelibrary.routers;

import com.sofka.newreactivelibrary.NewreactivelibraryApplication;
import com.sofka.newreactivelibrary.collections.Resource;
import com.sofka.newreactivelibrary.dtos.ResourceDTO;
import com.sofka.newreactivelibrary.mappers.ResourceMapper;
import com.sofka.newreactivelibrary.repositories.ResourceRepository;
import com.sofka.newreactivelibrary.usecases.CreateResourceUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CreateResourceRouter.class, CreateResourceUseCase.class, ResourceMapper.class, NewreactivelibraryApplication.class})
class CreateResourceRouterTest {
    @MockBean
    private ResourceRepository resourceRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testCreateUser() {

        var res = new Resource();
        res.setId("4");
        res.setName("Garden Spiders");
        res.setType("Book");
        res.setGenre("Biology");
        res.setTotalCopies(2);
        res.setBorrowedCopies(0);

        ResourceDTO resDTO = new ResourceDTO(res.getId(), res.getName(),res.getType(),  res.getGenre(), null, res.getBorrowedCopies(), res.getTotalCopies());
        Mono<Resource> resMono = Mono.just(res);
        when(resourceRepository.save(any())).thenReturn(resMono);

        webTestClient.post()
                .uri("/api/new")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(resDTO), ResourceDTO.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ResourceDTO.class)
                .value(userResponse -> {
                            Assertions.assertThat(userResponse.getId()).isEqualTo(res.getId());
                        }
                );

    }


}