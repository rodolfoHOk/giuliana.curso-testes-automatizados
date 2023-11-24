package br.com.hioktec.swplanetapi;

import static br.com.hioktec.swplanetapi.common.PlanetConstants.PLANET;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import br.com.hioktec.swplanetapi.domain.Planet;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PlanetIT {

  @Autowired
  private TestRestTemplate testRestTemplate;
  
  @Test
  public void createPlanet_ReturnsCreated() {
    ResponseEntity<Planet> sut = testRestTemplate
      .postForEntity("/planets", PLANET, Planet.class);
    
    assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(sut.getBody().getId()).isNotNull();
    assertThat(sut.getBody().getName()).isEqualTo(PLANET.getName());
    assertThat(sut.getBody().getClimate()).isEqualTo(PLANET.getClimate());
    assertThat(sut.getBody().getTerrain()).isEqualTo(PLANET.getTerrain());
  }

}
