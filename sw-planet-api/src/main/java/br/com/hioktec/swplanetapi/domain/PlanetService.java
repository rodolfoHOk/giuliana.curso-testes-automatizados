package br.com.hioktec.swplanetapi.domain;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class PlanetService {

  private PlanetRepository planetRepository;

  public PlanetService(PlanetRepository planetRepository) {
    this.planetRepository = planetRepository;
  }

  public Planet create(Planet planet) {
    return planetRepository.save(planet);
  }

  public Optional<Planet> searchById(Long id) {
    return planetRepository.findById(id);
  }

  public Optional<Planet> searchByName(String name) {
    return planetRepository.findByName(name);
  }
  
}
