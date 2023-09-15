package ca.ulaval.glo4003.sulvlo.unit.domain.station.bike;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.sulvlo.domain.bike.Bike;
import ca.ulaval.glo4003.sulvlo.domain.bike.EnergyLevel;
import ca.ulaval.glo4003.sulvlo.domain.bike.exception.InvalidBikeEnergyException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BikeTest {

  private static final LocalDateTime BIKE_UNLOCKED_DATETIME = LocalDateTime.now();
  private static final LocalDateTime BIKE_RETURNED_DATETIME = BIKE_UNLOCKED_DATETIME.plusMinutes(5);
  private static final LocalDateTime NOT_SUPPORTED_BIKE_RETURNED_DATETIME = BIKE_UNLOCKED_DATETIME.minusMinutes(
      17);
  private static final double SPECIFIC_PERCENTAGE = 0.005 * 5;
  private static final int LOCATION = 1;
  private static final int ENERGY_LEVEL = 16;

  private Bike bikeWithPositiveEnergy;

  @BeforeEach
  public void setup() {
    bikeWithPositiveEnergy = new Bike(LOCATION, new EnergyLevel(BigDecimal.valueOf(ENERGY_LEVEL)));
    bikeWithPositiveEnergy.setUnlockDateTime(BIKE_UNLOCKED_DATETIME);
    bikeWithPositiveEnergy.setReturnDateTime(BIKE_RETURNED_DATETIME);
  }

  @Test
  void givenBikeWithZeroEnergy_whenSetEnergyLevelAfterReturn_thenBikeEnergyShouldStillBeZero() {
    Bike bikeWithZeroEnergy = new Bike(LOCATION, new EnergyLevel(BigDecimal.ZERO));
    bikeWithZeroEnergy.setUnlockDateTime(BIKE_UNLOCKED_DATETIME);
    bikeWithZeroEnergy.setReturnDateTime(BIKE_RETURNED_DATETIME);

    bikeWithZeroEnergy.calculateEnergyLevelAfterReturn();

    assertEquals(new EnergyLevel(BigDecimal.ZERO), bikeWithZeroEnergy.getEnergyLevel());
  }

  @Test
  void givenBikeWithPositiveEnergy_whenSetEnergyLevelAfterReturn_thenBikeEnergyShouldDecreaseByASpecificPercentage() {
    bikeWithPositiveEnergy.calculateEnergyLevelAfterReturn();

    assertEquals(new EnergyLevel(new BigDecimal(ENERGY_LEVEL - SPECIFIC_PERCENTAGE)),
        bikeWithPositiveEnergy.getEnergyLevel());
  }

  @Test
  void givenBikeWithLowEnergyLevel_whenCheckBikeEnergyLevelValidity_thenThrowInvalidBikeEnergyException() {
    assertThrows(InvalidBikeEnergyException.class, () -> {
      bikeWithPositiveEnergy.checkBikeEnergyLevelValidity();
    });
  }
}
