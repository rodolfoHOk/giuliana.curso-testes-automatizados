package br.com.hioktec.swplanetapi.domain;

import static br.com.hioktec.swplanetapi.common.PlanetConstants.INVALID_PLANET;
import static br.com.hioktec.swplanetapi.common.PlanetConstants.PLANET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

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

}
