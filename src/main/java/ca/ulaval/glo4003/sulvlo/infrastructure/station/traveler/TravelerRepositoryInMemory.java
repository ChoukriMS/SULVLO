package ca.ulaval.glo4003.sulvlo.infrastructure.station.traveler;

import ca.ulaval.glo4003.sulvlo.domain.station.traveler.Traveler;
import ca.ulaval.glo4003.sulvlo.domain.station.traveler.TravelerRepository;
import ca.ulaval.glo4003.sulvlo.domain.station.traveler.exception.TravelerNotFoundException;
import java.util.HashMap;
import java.util.List;

public class TravelerRepositoryInMemory implements TravelerRepository {

  private final HashMap<String, Traveler> travelerHashMap;

  public TravelerRepositoryInMemory() {
    this.travelerHashMap = new HashMap<>();
  }

  @Override
  public List<Traveler> findAllTravelers() {
    return travelerHashMap.values().stream().toList();
  }

  @Override
  public Traveler findByIdul(String idul) {
    if (!travelerHashMap.containsKey(idul)) {
      throw new TravelerNotFoundException(idul);
    }
    return travelerHashMap.get(idul);
  }

  @Override
  public void save(Traveler traveler) {
    travelerHashMap.put(traveler.getIdul(), traveler);
  }
}
