package br.com.hioktec.swplanetapi;

import static br.com.hioktec.swplanetapi.common.PlanetConstants.PLANET;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.com.hioktec.swplanetapi.domain.Planet;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/remove_planets.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class PlanetReactiveIT {

  @Autowired
  private WebTestClient webTestClient;
  
  @Test
  public void createPlanet_ReturnsCreated() {
    Planet sut = webTestClient
      .post().uri("/planets").bodyValue(PLANET).exchange()
      .expectStatus().isCreated().expectBody(Planet.class)
      .returnResult().getResponseBody();
    
    assertThat(sut.getId()).isNotNull();
    assertThat(sut.getName()).isEqualTo(PLANET.getName());
    assertThat(sut.getClimate()).isEqualTo(PLANET.getClimate());
    assertThat(sut.getTerrain()).isEqualTo(PLANET.getTerrain());
  }

}
