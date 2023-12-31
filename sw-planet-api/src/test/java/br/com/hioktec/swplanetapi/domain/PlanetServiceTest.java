package br.com.hioktec.swplanetapi.domain;

import static br.com.hioktec.swplanetapi.common.PlanetConstants.EXISTING_ID;
import static br.com.hioktec.swplanetapi.common.PlanetConstants.EXISTING_NAME;
import static br.com.hioktec.swplanetapi.common.PlanetConstants.INVALID_PLANET;
import static br.com.hioktec.swplanetapi.common.PlanetConstants.NONEXISTING_ID;
import static br.com.hioktec.swplanetapi.common.PlanetConstants.NONEXISTING_NAME;
import static br.com.hioktec.swplanetapi.common.PlanetConstants.PLANET;
import static br.com.hioktec.swplanetapi.common.PlanetConstants.QUERY_PLANET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;


@ExtendWith(MockitoExtension.class)
public class PlanetServiceTest {

  @Mock
  private PlanetRepository planetRepository;

  @InjectMocks
  private PlanetService planetService;
  
  @Test
  public void createPlanet_WithValidData_ReturnsPlanet() {
    when(planetRepository.save(PLANET)).thenReturn(PLANET);

    Planet sut = planetService.create(PLANET);

    assertThat(sut).isEqualTo(PLANET);
  }

  @Test
  public void createPlanet_WithInValidData_ThrowsException() {
    when(planetRepository.save(INVALID_PLANET))
      .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> planetService.create(INVALID_PLANET))
      .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void searchPlanetById_ByExistingId_ReturnsPlanet() {
    when(planetRepository.findById(EXISTING_ID)).thenReturn(Optional.of(PLANET));

    Optional<Planet> sut = planetService.searchById(EXISTING_ID);

    assertThat(sut).isPresent();
    assertThat(sut.get()).isEqualTo(PLANET);
  }

  @Test
  public void searchPlanetById_ByNonexistingId_ReturnsEmpty() {
    when(planetRepository.findById(NONEXISTING_ID)).thenReturn(Optional.empty());

    Optional<Planet> sut = planetService.searchById(NONEXISTING_ID);

    assertThat(sut).isEmpty();
  }

  @Test
  public void searchPlanetByName_ByExistingName_ReturnsPlanet() {
    when(planetRepository.findByName(EXISTING_NAME)).thenReturn(Optional.of(PLANET));

    Optional<Planet> sut = planetService.searchByName(EXISTING_NAME);

    assertThat(sut).isPresent();
    assertThat(sut.get()).isEqualTo(PLANET);
  }

  @Test
  public void searchPlanetByName_ByNonexistingName_ReturnsEmpty() {
    when(planetRepository.findByName(NONEXISTING_NAME)).thenReturn(Optional.empty());

    Optional<Planet> sut = planetService.searchByName(NONEXISTING_NAME);

    assertThat(sut).isEmpty();
  }

  @Test
  public void listPlanets_ReturnAllPlanets() {
    var query = QueryBuilder.makeQuery(QUERY_PLANET);
    when(planetRepository.findAll(query)).thenReturn(List.of(PLANET));

    List<Planet> sut = planetService.listBy(
      QUERY_PLANET.getClimate(),
      QUERY_PLANET.getTerrain()
    );

    assertThat(sut).isNotEmpty();
    assertThat(sut).hasSize(1);
    assertThat(sut.get(0)).isEqualTo(PLANET);
  }

  @Test
  public void listPlanets_ReturnNoPlanets() {
    when(planetRepository.findAll(any())).thenReturn(Collections.emptyList());

    List<Planet> sut = planetService.listBy("", "");

    assertThat(sut).isEmpty();
    assertThat(sut).hasSize(0);
  }

  @Test
  public void removePlanet_WithExistingId_DoesNotThrowAnyException() {
    when(planetRepository.findById(EXISTING_ID)).thenReturn(Optional.of(PLANET));
    doNothing().when(planetRepository).delete(any());

    assertThatCode(() -> planetService.remove(EXISTING_ID)).doesNotThrowAnyException();
  }

  @Test
  public void removePlanet_WithNonexistingId_ThrowsException() {
    when(planetRepository.findById(NONEXISTING_ID)).thenReturn(Optional.empty());
    
    assertThatThrownBy(() -> planetService.remove(NONEXISTING_ID))
      .isInstanceOf(EmptyResultDataAccessException.class);
  }

}
