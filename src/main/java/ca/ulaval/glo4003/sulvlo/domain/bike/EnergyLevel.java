package ca.ulaval.glo4003.sulvlo.domain.bike;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class EnergyLevel {

  private static final BigDecimal MAX_VALUE = new BigDecimal(100).setScale(3, RoundingMode.HALF_UP);
  private final BigDecimal value;

  public EnergyLevel(BigDecimal value) {
    this.value = roundToMaxValue(value);
  }

  private BigDecimal roundToMaxValue(BigDecimal value) {
    if (value.compareTo(MAX_VALUE) > 0) {
      return MAX_VALUE;
    }

    return value.setScale(3, RoundingMode.HALF_UP);
  }

  public double getValue() {
    return value.doubleValue();
  }

  public int compareTo(double anotherValue) {
    return this.value.compareTo(BigDecimal.valueOf(anotherValue));
  }

  @Override
  public String toString() {
    return value + " %";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EnergyLevel that = (EnergyLevel) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
