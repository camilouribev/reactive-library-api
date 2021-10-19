package com.sofka.newreactivelibrary.routers;

import com.sofka.newreactivelibrary.NewreactivelibraryApplication;
import com.sofka.newreactivelibrary.collections.Resource;
import com.sofka.newreactivelibrary.configurations.WebFluxConfig;
import com.sofka.newreactivelibrary.dtos.ResourceDTO;
import com.sofka.newreactivelibrary.mappers.ResourceMapper;
import com.sofka.newreactivelibrary.repositories.ResourceRepository;
import com.sofka.newreactivelibrary.usecases.GetAllResourcesUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.when;

@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {GetAllResourcesRouter.class, GetAllResourcesUseCase.class, ResourceMapper.class, NewreactivelibraryApplication.class})
public class GetAllResourcesRouterTest {

    @MockBean
    private ResourceRepository resourceRepository;

    private ResourceMapper mapper;

    @Autowired
    private WebTestClient webTestClient;
    @Test
    public void GetAllResourcesTest(){
        var res1 = new Resource();
        res1.setId("1");
        res1.setName("Pride and Prejudice");
        res1.setType("Book");
        res1.setGenre("Drama");
        res1.setTotalCopies(2);
        res1.setBorrowedCopies(1);

        var res2 = new Resource();
        res2.setId("2");
        res2.setName("Fundamentals of statics");
        res2.setType("Book");
        res2.setGenre("Physics");
        res2.setTotalCopies(3);
        res2.setBorrowedCopies(3);

        when(resourceRepository.findAll()).thenReturn( Flux.just(res1,res2));

        webTestClient.get()
                .uri("/api/resources")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ResourceDTO.class)
                .value(userResponse -> {
                    Assertions.assertThat(userResponse.get(0).getId()).isEqualTo(res1.getId());
                    Assertions.assertThat(userResponse.get(1).getId()).isEqualTo(res2.getId());
                    Assertions.assertThat(userResponse.get(0).getGenre()).isEqualTo(res1.getGenre());
                    Assertions.assertThat(userResponse.get(1).getGenre()).isEqualTo(res2.getGenre());
                    Assertions.assertThat(userResponse.get(0).getBorrowedCopies()).isEqualTo(res1.getBorrowedCopies());
                    Assertions.assertThat(userResponse.get(1).getType()).isEqualTo(res2.getType());

                });
    }

}