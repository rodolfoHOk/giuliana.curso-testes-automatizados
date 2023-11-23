package br.com.hioktec.swplanetapi.common;

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

  public static final Long EXISTING_ID = 1L;

  public static final Long NONEXISTING_ID = 1000L;

}
