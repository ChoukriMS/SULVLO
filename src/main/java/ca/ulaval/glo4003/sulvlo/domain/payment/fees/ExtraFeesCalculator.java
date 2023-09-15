package ca.ulaval.glo4003.sulvlo.domain.payment.fees;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class ExtraFeesCalculator {


  public BigDecimal calculateFees(Duration duration, int freeMinutes) {

    Duration durationToCharge = duration.minus(freeMinutes * 60L, ChronoUnit.SECONDS);
    long minutes = durationToCharge.toMinutes();
    long seconds = durationToCharge.toSecondsPart();

    if (minutes <= 0.0 && seconds <= 0) {
      return BigDecimal.ZERO;
    }

    if (seconds >= 15) {
      minutes += 1.0;
    }
    return BigDecimal.valueOf(minutes * 0.05);
  }
}
