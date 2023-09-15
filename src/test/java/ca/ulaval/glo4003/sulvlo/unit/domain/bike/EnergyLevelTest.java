package ca.ulaval.glo4003.sulvlo.unit.domain.bike;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.sulvlo.domain.bike.EnergyLevel;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class EnergyLevelTest {


  private static EnergyLevel createEnergyLevel(double level) {
    return new EnergyLevel(new BigDecimal(level));
  }

  @Test
  void givenAnEnergyLevelMoreThan100_whenCreatingEnergyLevel_thenEnergyLevelEqual100() {
    EnergyLevel energyLevel = createEnergyLevel(120);

    assertThat(energyLevel).isEqualTo(createEnergyLevel(100));
  }

  @Test
  void givenAnEnergyLevelLessThan100_whenCreatingEnergyLevel_thenEnergyLevelIsTheSame() {
    EnergyLevel energyLevel = createEnergyLevel(80);

    assertThat(energyLevel).isEqualTo(createEnergyLevel(80));
  }

  @Test
  void givenAnEnergyLevelWithFractionalNumbers_whenCreatingEnergyLevel_thenEnergyLevelIsRoundedToThousandth() {
    EnergyLevel energyLevel = createEnergyLevel(80.0005);

    assertThat(energyLevel).isEqualTo(createEnergyLevel(80.001));
  }
}