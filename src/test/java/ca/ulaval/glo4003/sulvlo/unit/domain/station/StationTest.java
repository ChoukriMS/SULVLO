package ca.ulaval.glo4003.sulvlo.unit.domain.station;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.sulvlo.domain.bike.Bike;
import ca.ulaval.glo4003.sulvlo.domain.bike.EnergyLevel;
import ca.ulaval.glo4003.sulvlo.domain.bike.exception.BikeNotFoundException;
import ca.ulaval.glo4003.sulvlo.domain.station.Station;
import ca.ulaval.glo4003.sulvlo.domain.station.exception.InvalidReturnInStationException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StationTest {


  private static final LocalDateTime RETURNABLE_BIKE_UNLOCK_DATETIME = LocalDateTime.now()
      .minusMinutes(6);
  private static final LocalDateTime UNRETURNABLE_BIKE_UNLOCK_DATETIME = LocalDateTime.now()
      .minusMinutes(13);
  private static final int EXISTING_AVAILABLE_BIKE_LOCATION = 2;
  private static final int RETURN_LOCATION = 3;
  private static final int NON_EXISTING_AVAILABLE_BIKE_LOCATION = 34;
  private static final int EXISTING_UNLOCKED_BIKE_LOCATION = 3;
  private static final int ANOTHER_EXISTING_UNLOCKED_BIKE_LOCATION = 5;
  private static final double SPECIFIC_PERCENTAGE = 0.002 * 5;
  private static final String ANY_STATION_LOCATION = "anyLocation";
  private static final String ANY_STATION_NAME = "anyName";
  private static final String ANY_STATION_CODE = "anyCode";

  @Mock
  private Bike mockBike;

  private Bike availableBike, returnableBike, unreturnableBike;
  private Station station;

  @BeforeEach
  public void setup() {
    availableBike = new Bike(EXISTING_AVAILABLE_BIKE_LOCATION,
        new EnergyLevel(BigDecimal.valueOf(46)));
    returnableBike = new Bike(EXISTING_UNLOCKED_BIKE_LOCATION,
        new EnergyLevel(BigDecimal.valueOf(50)));
    returnableBike.setUnlockDateTime(RETURNABLE_BIKE_UNLOCK_DATETIME);
    unreturnableBike = new Bike(ANOTHER_EXISTING_UNLOCKED_BIKE_LOCATION,
        new EnergyLevel(BigDecimal.valueOf(68)));
    unreturnableBike.setUnlockDateTime(UNRETURNABLE_BIKE_UNLOCK_DATETIME);

    station = new Station(ANY_STATION_LOCATION, ANY_STATION_NAME, ANY_STATION_CODE, 1, 2,
        new ArrayList<>() {
          {
            add(availableBike);
          }
        }, new ArrayList<>() {
      {
        add(6);
        add(3);
      }
    });
    station.setCurrentDateTime(LocalDateTime.now().minusMinutes(5));
//    station.setUnlockedBikes(new ArrayList<>() {
//      {
//        add(returnableBike);
//        add(unreturnableBike);
//      }
//    });
  }

  @Test
  void givenAStationWithAvailableBikes_whenFindExistingAvailableBikeByLocation_thenReturnThatBike() {
    assertEquals(availableBike,
        station.findAvailableBikeByLocation(EXISTING_AVAILABLE_BIKE_LOCATION));
  }

  @Test
  void givenAStationWithAvailableBikes_whenFindNonExistingAvailableBikeByLocation_thenThrowBikeNotFoundException() {
    assertThrows(BikeNotFoundException.class, () -> {
      station.findAvailableBikeByLocation(NON_EXISTING_AVAILABLE_BIKE_LOCATION);
    });
  }

  @Test
  void givenAStationWithOneAvailableBike_whenRechargeBikes_thenEnergyLevelOfAllAvailableBikesShouldIncreaseByASpecificPercentage() {
    station.rechargeBikes();

    assertEquals(new EnergyLevel(new BigDecimal(46 + SPECIFIC_PERCENTAGE)),
        availableBike.getEnergyLevel());
  }

  @Test
  void givenABike_whenUnlockingBike_thenBikeIsRemoveFromStation() {
    station.unlockBike(availableBike);

    assertThrows(BikeNotFoundException.class, () -> {
      station.findAvailableBikeByLocation(NON_EXISTING_AVAILABLE_BIKE_LOCATION);
    });
  }

  @Test
  void givenABikeAndLocation_whenReturningBike_thenBikeIsAddedToStation() {

    station.returnBike(mockBike, RETURN_LOCATION);

    verify(mockBike, times(1)).dock(RETURN_LOCATION);
  }

  @Test
  void givenABikeAndLocation_whenReturningBikeWithInvalidLocation_thenInvalidReturnInStationException() {

    assertThrows(InvalidReturnInStationException.class, () -> {

      station.returnBike(mockBike, EXISTING_AVAILABLE_BIKE_LOCATION);
    });
  }
}