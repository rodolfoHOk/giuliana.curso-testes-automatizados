package br.com.hioktec.swplanetapi.domain;

import static br.com.hioktec.swplanetapi.common.PlanetConstants.PLANET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.boot.test.context.SpringBootTest;

// @SpringBootTest(classes = PlanetService.class) // não eficiente
@ExtendWith(MockitoExtension.class)
public class PlanetServiceTest {

  // @MockBean // removed @SpringBootTest
  @Mock
  private PlanetRepository planetRepository;

  // @Autowired // removed @SpringBootTest
  @InjectMocks
  private PlanetService planetService;
  
  @Test
  public void createPlanet_WithValidData_ReturnsPlanet() {
    when(planetRepository.save(PLANET)).thenReturn(PLANET);

    Planet sut = planetService.create(PLANET);

    assertThat(sut).isEqualTo(PLANET);
  }

}
