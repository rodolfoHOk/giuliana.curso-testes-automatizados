package br.com.hioktec.swplanetapi.common;

import java.util.List;

import br.com.hioktec.swplanetapi.domain.Planet;

public class PlanetConstants {
  
  public static final Planet PLANET = new Planet(
    "name_test",
    "climate_test",
    "terrain_test"
  );

  public static final Planet INVALID_PLANET = new Planet(
    "",
    "",
    ""
  );

  public static final Planet QUERY_PLANET = new Planet(
    "climate_test",
    "terrain_test"
  );

  public static final Long EXISTING_ID = 1L;
  public static final Long NONEXISTING_ID = 1000L;

  public static final String EXISTING_NAME = "name-existing-test";
  public static final String NONEXISTING_NAME = "name-nonexisting-test";

  public static final Planet TATOOINE = new Planet(
    1L,
    "Tatooine",
    "arid",
    "desert"
  );
  public static final Planet ALDERAAN = new Planet(
    2L, 
    "Alderaan", 
    "temperate", 
    "grasslands, mountains"
  );
  public static final Planet YAVINIV = new Planet(
    3L, "Yavin IV",
    "temperate, tropical",
    "jungle, rainforests"
  );
  
  public static final List<Planet> PLANETS = List.of(TATOOINE, ALDERAAN, YAVINIV);

}
