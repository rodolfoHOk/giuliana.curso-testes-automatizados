package br.com.hioktec.swplanetapi.domain;

import static br.com.hioktec.swplanetapi.common.PlanetConstants.EXISTING_ID;
import static br.com.hioktec.swplanetapi.common.PlanetConstants.INVALID_PLANET;
import static br.com.hioktec.swplanetapi.common.PlanetConstants.NONEXISTING_ID;
import static br.com.hioktec.swplanetapi.common.PlanetConstants.PLANET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


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

}
