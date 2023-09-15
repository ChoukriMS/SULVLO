package ca.ulaval.glo4003.sulvlo.unit.infrastructure.truck;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.sulvlo.domain.truck.Truck;
import ca.ulaval.glo4003.sulvlo.infrastructure.truck.TruckDevDataFactory;
import java.util.List;
import org.junit.jupiter.api.Test;

class TruckDevDataFactoryTest {

  private static final int EXPECTED_TRUCK_LIST_SIZE = 3;

  @Test
  void whenCreateMockData_thenListOfThreeTrucksIsReturned() {
    TruckDevDataFactory truckDevDataFactory = new TruckDevDataFactory();

    List<Truck> actualTruckList = truckDevDataFactory.createMockData();

    assertEquals(EXPECTED_TRUCK_LIST_SIZE, actualTruckList.size());
  }

}