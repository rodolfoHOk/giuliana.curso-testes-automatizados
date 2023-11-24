package br.com.hioktec.swplanetapi;

import static br.com.hioktec.swplanetapi.common.PlanetConstants.ALDERAAN;
import static br.com.hioktec.swplanetapi.common.PlanetConstants.PLANET;
import static br.com.hioktec.swplanetapi.common.PlanetConstants.TATOOINE;
import static br.com.hioktec.swplanetapi.common.PlanetConstants.YAVINIV;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import br.com.hioktec.swplanetapi.domain.Planet;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/imports_planets.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/remove_planets.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
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

  @Test
  public void getPlanetById_ReturnsOkAndPlanet() {
    ResponseEntity<Planet> sut = testRestTemplate
      .getForEntity("/planets/1", Planet.class);

    assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(sut.getBody()).isEqualTo(TATOOINE);
  }

  @Test
  public void getPlanetByName_ReturnsOkAndPlanet() {
    ResponseEntity<Planet> sut = testRestTemplate
      .getForEntity("/planets/name/" + TATOOINE.getName(), Planet.class);

    assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(sut.getBody()).isEqualTo(TATOOINE);
  }

  @Test
  public void listPlanets_ReturnsOkAndAllPlanets() {
    ResponseEntity<Planet[]> sut = testRestTemplate
      .getForEntity("/planets", Planet[].class);

    assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(sut.getBody()).hasSize(3);
    assertThat(sut.getBody()).contains(TATOOINE, ALDERAAN, YAVINIV);
  }

  @Test
  public void listPlanets_ByClimate_ReturnsOkAndPlanets() {
    ResponseEntity<Planet[]> sut = testRestTemplate
      .getForEntity("/planets?climate=" + ALDERAAN.getClimate(), Planet[].class);

    assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(sut.getBody()).hasSize(1);
    assertThat(sut.getBody()).contains(ALDERAAN);
  }

  @Test
  public void listPlanets_ByTerrain_ReturnsOkAndPlanets() {
    ResponseEntity<Planet[]> sut = testRestTemplate
      .getForEntity("/planets?terrain=" + YAVINIV.getTerrain(), Planet[].class);

    assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(sut.getBody()).hasSize(1);
    assertThat(sut.getBody()).contains(YAVINIV);
  }

  @Test
  public void removePlanets_ReturnsNoContent() {
    ResponseEntity<Void> sut = testRestTemplate
      .exchange("/planets/" + YAVINIV.getId(), HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);

    assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

}
