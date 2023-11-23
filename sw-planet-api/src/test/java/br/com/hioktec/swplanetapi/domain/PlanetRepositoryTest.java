package br.com.hioktec.swplanetapi.domain;

import static br.com.hioktec.swplanetapi.common.PlanetConstants.ALDERAAN;
import static br.com.hioktec.swplanetapi.common.PlanetConstants.NONEXISTING_ID;
import static br.com.hioktec.swplanetapi.common.PlanetConstants.NONEXISTING_NAME;
import static br.com.hioktec.swplanetapi.common.PlanetConstants.PLANET;
import static br.com.hioktec.swplanetapi.common.PlanetConstants.TATOOINE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;


@DataJpaTest
public class PlanetRepositoryTest {

  @Autowired
  private PlanetRepository planetRepository;

  @Autowired
  private TestEntityManager testEntityManager;

  @AfterEach
  public void afterEach() {
    PLANET.setId(null);
  }
  
  @Test
  public void createPlanet_WithValidData_ReturnsPlanet() {
    Planet planet = planetRepository.save(PLANET);

    Planet sut = testEntityManager.find(Planet.class, planet.getId());

    System.out.println(planet);

    assertThat(sut).isNotNull();
    assertThat(sut.getName()).isEqualTo(PLANET.getName());
    assertThat(sut.getClimate()).isEqualTo(PLANET.getClimate());
    assertThat(sut.getTerrain()).isEqualTo(PLANET.getTerrain());
  }

  @Test
  public void createPlanet_WithInValidData_ThrowsException() { 
    Planet emptyPlanet = new Planet();
    Planet invalidPlanet = new Planet("", "", "");

    assertThatThrownBy(() -> planetRepository.save(emptyPlanet))
      .isInstanceOf(RuntimeException.class);
    assertThatThrownBy(() -> planetRepository.save(invalidPlanet))
      .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void createPlanet_WithExistingName_ThrowsException() {
    Planet planet = testEntityManager.persistFlushFind(PLANET);
    testEntityManager.detach(planet);
    planet.setId(null);

    assertThatThrownBy(() -> planetRepository.save(planet))
      .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void getPlanetById_ByExistingId_ReturnsPlanet() throws Exception {
    Planet planet = testEntityManager.persistFlushFind(PLANET);

    Optional<Planet> sut = planetRepository.findById(planet.getId());

    assertThat(sut).isPresent();
    assertThat(sut.get()).isEqualTo(planet);
  }

  @Test
  public void getPlanetById_ByNonexistingId_ReturnsEmpty() throws Exception {
    Optional<Planet> sut = planetRepository.findById(NONEXISTING_ID);

    assertThat(sut).isEmpty();
  }

  @Test
  public void getPlanetByName_ByExistingName_ReturnsPlanet() throws Exception {
    Planet planet = testEntityManager.persistFlushFind(PLANET);

    Optional<Planet> sut = planetRepository.findByName(planet.getName());

    assertThat(sut).isPresent();
    assertThat(sut.get()).isEqualTo(planet);
  }

  @Test
  public void getPlanetByName_ByNonexistingName_ReturnsEmpty() throws Exception {
    Optional<Planet> sut = planetRepository.findByName(NONEXISTING_NAME);

    assertThat(sut).isEmpty();
  }

  @Test
  @Sql(scripts = "/imports_planets.sql")
  public void listPlanets_ReturnsFilteredPlanets() throws Exception {
    var query1 = QueryBuilder.makeQuery(new Planet());
    var query2 = QueryBuilder.makeQuery(new Planet(TATOOINE.getClimate(), null));
    var query3 = QueryBuilder.makeQuery(new Planet(null, TATOOINE.getTerrain()));
    var query4 = QueryBuilder.makeQuery(new Planet(TATOOINE.getClimate(), TATOOINE.getTerrain()));
    var query5 = QueryBuilder.makeQuery(new Planet(ALDERAAN.getClimate(), TATOOINE.getTerrain()));
    
    List<Planet> sut1 = planetRepository.findAll(query1);
    List<Planet> sut2 = planetRepository.findAll(query2);
    List<Planet> sut3 = planetRepository.findAll(query3);
    List<Planet> sut4 = planetRepository.findAll(query4);
    List<Planet> sut5 = planetRepository.findAll(query5);

    assertThat(sut1).isNotEmpty();
    assertThat(sut1).hasSize(3);
    assertThat(sut2).isNotEmpty();
    assertThat(sut2).hasSize(1);
    assertThat(sut3).isNotEmpty();
    assertThat(sut3).hasSize(1);
    assertThat(sut4).isNotEmpty();
    assertThat(sut4).hasSize(1);
    assertThat(sut5).isEmpty();
  }

  @Test
  public void listPlanets_ReturnsEmptyList() throws Exception {
    var query = QueryBuilder.makeQuery(new Planet());
    List<Planet> sut = planetRepository.findAll(query);

    assertThat(sut).isEmpty();
  }

}
