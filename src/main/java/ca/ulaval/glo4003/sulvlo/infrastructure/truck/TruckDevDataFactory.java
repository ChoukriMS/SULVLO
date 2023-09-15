package ca.ulaval.glo4003.sulvlo.infrastructure.truck;

import ca.ulaval.glo4003.sulvlo.domain.truck.Truck;
import java.util.List;

public class TruckDevDataFactory {

  public List<Truck> createMockData() {
    return List.of(new Truck(), new Truck(), new Truck());
  }
}
