package br.com.hioktec.swplanetapi.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.hioktec.swplanetapi.domain.Planet;
import br.com.hioktec.swplanetapi.domain.PlanetService;

@RestController
@RequestMapping("/planets")
public class PlanetController {

  private PlanetService planetService;

  public PlanetController(PlanetService planetService) {
    this.planetService = planetService;
  }

  @PostMapping
  public ResponseEntity<Planet> create(@RequestBody Planet planet) {
    var planetCreated = planetService.create(planet);
    return ResponseEntity.status(HttpStatus.CREATED).body(planetCreated);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Planet> getById(@PathVariable("id") Long id) {
    return planetService.searchById(id)
      .map(planet -> ResponseEntity.ok(planet))
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<Planet> getByName(@PathVariable("name") String name) {
    return planetService.searchByName(name)
      .map(planet -> ResponseEntity.ok(planet))
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<Planet>> list(
    @RequestParam(required = false) String climate,
    @RequestParam(required = false) String terrain
  ) {
    List<Planet> planets = planetService.listBy(climate, terrain);
    return ResponseEntity.ok(planets);
  }

}
