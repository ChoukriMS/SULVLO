package ca.ulaval.glo4003.sulvlo.infrastructure.truck;

import ca.ulaval.glo4003.sulvlo.domain.truck.Truck;
import ca.ulaval.glo4003.sulvlo.domain.truck.TruckId;
import ca.ulaval.glo4003.sulvlo.domain.truck.TruckRepository;
import ca.ulaval.glo4003.sulvlo.domain.truck.exception.TruckNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TruckRepositoryInMemory implements TruckRepository {

  private final TruckDevDataFactory truckDevDataFactory;

  private final Map<TruckId, Truck> trucksHashMap = new HashMap<>();

  public TruckRepositoryInMemory(TruckDevDataFactory truckDevDataFactory) {
    this.truckDevDataFactory = truckDevDataFactory;
    List<Truck> trucks = this.truckDevDataFactory.createMockData();
    trucks.stream().forEach(truck -> save(truck));
  }

  @Override
  public List<Truck> findAll() {
    return trucksHashMap.values().stream().toList();
  }

  @Override
  public Truck findById(TruckId truckId) {
    Truck truck = trucksHashMap.get(truckId);
    if (truck != null) {
      return truck;
    }
    throw new TruckNotFoundException(truckId);
  }

  @Override
  public void save(Truck truck) {
    trucksHashMap.put(truck.getTruckId(), truck);
  }
}
