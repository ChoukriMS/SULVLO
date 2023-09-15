package ca.ulaval.glo4003.sulvlo.unit.infrastructure.truck;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.sulvlo.domain.truck.Truck;
import ca.ulaval.glo4003.sulvlo.domain.truck.TruckId;
import ca.ulaval.glo4003.sulvlo.domain.truck.exception.TruckNotFoundException;
import ca.ulaval.glo4003.sulvlo.infrastructure.truck.TruckDevDataFactory;
import ca.ulaval.glo4003.sulvlo.infrastructure.truck.TruckRepositoryInMemory;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TruckRepositoryInMemoryTest {

  private static final TruckId INITIAL_TRUCK_ID = new TruckId();
  private static final TruckId ANOTHER_TRUCK_ID = new TruckId();
  private static final TruckId NON_EXISTING_TRUCK_ID = new TruckId();
  private static final int EXPECTED_TRUCK_LIST_SIZE = 1;
  private static final int EXPECTED_TRUCK_LIST_SIZE_AFTER_SAVE = 2;

  @Mock
  private Truck initialTruck, anotherTruck;
  @Mock
  private TruckDevDataFactory truckDevDataFactory;
  private TruckRepositoryInMemory truckRepositoryInMemory;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    when(initialTruck.getTruckId()).thenReturn(INITIAL_TRUCK_ID);
    when(truckDevDataFactory.createMockData()).thenReturn(List.of(initialTruck));
    truckRepositoryInMemory = new TruckRepositoryInMemory(truckDevDataFactory);
  }

  @Test
  void givenRepositoryWithOneTruck_whenFindAll_thenListOfOneTruckIsReturned() {
    List<Truck> actualTruckList = truckRepositoryInMemory.findAll();

    assertEquals(EXPECTED_TRUCK_LIST_SIZE, actualTruckList.size());
  }

  @Test
  void givenRepositoryWithOneTruck_whenSaveAnotherTruck_thenShouldBeAddedToRepository() {
    when(anotherTruck.getTruckId()).thenReturn(ANOTHER_TRUCK_ID);

    truckRepositoryInMemory.save(anotherTruck);

    assertEquals(EXPECTED_TRUCK_LIST_SIZE_AFTER_SAVE, truckRepositoryInMemory.findAll().size());
  }

  @Test
  void givenRepositoryWithOneTruck_whenFindInitialTruckById_thenInitialTruckIsReturned() {
    Truck actualTruck = truckRepositoryInMemory.findById(INITIAL_TRUCK_ID);

    assertEquals(initialTruck, actualTruck);
  }

  @Test
  void givenRepositoryWithOneTruck_whenFindNonExistingTruckById_thenTruckNotFoundExceptionIsThrown() {
    assertThrows(TruckNotFoundException.class, () -> {
      truckRepositoryInMemory.findById(NON_EXISTING_TRUCK_ID);
    });
  }

}