package br.com.hioktec.swplanetapi.web;

import static br.com.hioktec.swplanetapi.common.PlanetConstants.EXISTING_ID;
import static br.com.hioktec.swplanetapi.common.PlanetConstants.NONEXISTING_ID;
import static br.com.hioktec.swplanetapi.common.PlanetConstants.NONEXISTING_NAME;
import static br.com.hioktec.swplanetapi.common.PlanetConstants.PLANET;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.hioktec.swplanetapi.domain.Planet;
import br.com.hioktec.swplanetapi.domain.PlanetService;

@WebMvcTest(PlanetController.class)
public class PlanetControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private PlanetService planetService;

  @Test
  public void createPlanet_WithValidData_ReturnsCreated() throws Exception {
    when(planetService.create(PLANET)).thenReturn(PLANET);

    mockMvc
      .perform(post("/planets")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(PLANET)))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$").value(PLANET));
  }

  @Test
  public void createPlanet_WithInvalidData_ReturnsBadRequest() throws Exception {
    Planet emptyPlanet = new Planet();
    Planet invalidPlanet = new Planet("", "", "");

    mockMvc
      .perform(post("/planets")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(emptyPlanet)))
      .andExpect(status().isUnprocessableEntity());

    mockMvc
      .perform(post("/planets")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(invalidPlanet)))
      .andExpect(status().isUnprocessableEntity());
  }

  @Test
  public void createPlanet_WithExistingName_ReturnsConflict() throws Exception {
    when(planetService.create(any())).thenThrow(DataIntegrityViolationException.class);

    mockMvc
      .perform(post("/planets")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(PLANET)))
      .andExpect(status().isConflict());
  }

  @Test
  public void getPlanetById_ByExistingId_ReturnsOkAndPlanet() throws Exception {
    when(planetService.searchById(EXISTING_ID)).thenReturn(Optional.of(PLANET));

    mockMvc
      .perform(get("/planets/" + EXISTING_ID))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").value(PLANET));
  }

  @Test
  public void getPlanetById_ByNonexistingId_ReturnsNotFound() throws Exception {
    mockMvc
      .perform(get("/planets/" + NONEXISTING_ID))
      .andExpect(status().isNotFound());
  }

  @Test
  public void getPlanetByName_ByExistingName_ReturnsOkAndPlanet() throws Exception {
    when(planetService.searchByName(PLANET.getName())).thenReturn(Optional.of(PLANET));

    mockMvc
      .perform(get("/planets/name/" + PLANET.getName()))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").value(PLANET));
  }

  @Test
  public void getPlanetByName_ByNonexistingName_ReturnsNotFound() throws Exception {
    mockMvc
      .perform(get("/planets/name/" + NONEXISTING_NAME))
      .andExpect(status().isNotFound());
  }

}
