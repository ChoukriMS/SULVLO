package ca.ulaval.glo4003.sulvlo.unit.domain.truck;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.sulvlo.domain.bike.Bike;
import ca.ulaval.glo4003.sulvlo.domain.bike.exception.BikeNotFoundException;
import ca.ulaval.glo4003.sulvlo.domain.station.Station;
import ca.ulaval.glo4003.sulvlo.domain.truck.Truck;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TruckTest {

  public static final int BIKE_LOCATION_IN_STATION = 6;
  public static final int CLONED_BIKE_LOCATION_AFTER_LOAD = 1;
  public static final int NON_EXISTING_BIKE_LOCATION_IN_TRUCK = 45;
  public static final int EMPTY_BIKE_LOCATION_IN_STATION = 10;

  @Mock
  private Bike bike, clonedBike;
  @Mock
  private Station station;
  private List<Bike> bikesToBeLoaded;
  private Truck truck;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    when(clonedBike.getLocation()).thenReturn(CLONED_BIKE_LOCATION_AFTER_LOAD);
    when(bike.createClone()).thenReturn(clonedBike);
    when(bike.getLocation()).thenReturn(BIKE_LOCATION_IN_STATION);
    when(station.getEmptyBikeLocations()).thenReturn(List.of(EMPTY_BIKE_LOCATION_IN_STATION));
    bikesToBeLoaded = List.of(bike);
    truck = new Truck();
  }

  @Test
  void initiallyTruckShouldNotHaveAnyLoadedBike() {
    assertTrue(truck.getLoadedBikes().isEmpty());
  }

  @Test
  void givenAListOfOneBikeToBeLoaded_whenLoadBikes_thenThisBikeShouldBeLoadedInTheTruck() {
    truck.loadBikes(bikesToBeLoaded);

    assertEquals(1, truck.getLoadedBikes().size());
  }

  @Test
  void givenTruckWithOneLoadedBike_whenUnloadNonExistingBike_thenBikeNotFoundExceptionShouldBeThrown() {
    truck.loadBikes(bikesToBeLoaded);

    assertThrows(BikeNotFoundException.class, () -> {
      truck.unload(station, NON_EXISTING_BIKE_LOCATION_IN_TRUCK, EMPTY_BIKE_LOCATION_IN_STATION);
    });
  }

  @Test
  void givenTruckWithOneLoadedBike_whenUnloadThisBike_thisBikeShouldBeUnloadedFromTruck() {
    truck.loadBikes(bikesToBeLoaded);

    truck.unload(station, CLONED_BIKE_LOCATION_AFTER_LOAD, EMPTY_BIKE_LOCATION_IN_STATION);

    assertEquals(0, truck.getLoadedBikes().size());
  }

}