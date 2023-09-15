package ca.ulaval.glo4003.sulvlo.unit.domain.payment.fees;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.sulvlo.domain.payment.fees.ExtraFeesCalculator;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExtraFeesCalculatorTest {

  private static final int BASE_SUBSCRIPTION_MAXIMUM_DURATION = 10;
  private ExtraFeesCalculator extraFeesCalculator;

  @BeforeEach
  void setup() {
    extraFeesCalculator = new ExtraFeesCalculator();
  }

  @Test
  void givenSameDurationAndAMaximumTime_whenCalculatingFees_ThenReturnsCorrectAmount() {

    Duration travelDuration = Duration.of(10, ChronoUnit.MINUTES);

    var fees = extraFeesCalculator.calculateFees(travelDuration,
        BASE_SUBSCRIPTION_MAXIMUM_DURATION);

    var expectedFees = BigDecimal.ZERO;
    assertThat(fees).isEqualTo(expectedFees);
  }

  @Test
  void givenASmallerDurationAndBiggerMaximumTime_whenCalculatingFees_ThenReturnsZero() {

    Duration travelDuration = Duration.of(5, ChronoUnit.MINUTES);

    var fees = extraFeesCalculator.calculateFees(travelDuration,
        BASE_SUBSCRIPTION_MAXIMUM_DURATION);

    var expectedFees = BigDecimal.ZERO;
    assertThat(fees).isEqualTo(expectedFees);
  }

  @Test
  void givenABiggerDurationAndSmallerMaximumTime_whenCalculatingFees_ThenReturnsCorrectAmount() {

    Duration travelDuration = Duration.of(20, ChronoUnit.MINUTES);

    var fees = extraFeesCalculator.calculateFees(travelDuration,
        BASE_SUBSCRIPTION_MAXIMUM_DURATION);

    var expectedFees = new BigDecimal("0.5");
    assertThat(fees).isEqualTo(expectedFees);
  }

  @Test
  void givenABiggerDurationWithMoreThan15SecondsAndSmallerMaximumTime_whenCalculatingFees_ThenReturnsRoundedAmount() {

    Duration travelDuration = Duration.of(620, ChronoUnit.SECONDS);

    var fees = extraFeesCalculator.calculateFees(travelDuration,
        BASE_SUBSCRIPTION_MAXIMUM_DURATION);

    var expectedFees = new BigDecimal("0.05");
    assertThat(fees).isEqualTo(expectedFees);
  }

  @Test
  void givenABiggestDurationWithMoreThan15SecondsAndSmallerMaximumTime_whenCalculatingFees_ThenReturnsRoundedAmount() {

    Duration travelDuration = Duration.of(1220, ChronoUnit.SECONDS);

    var fees = extraFeesCalculator.calculateFees(travelDuration,
        BASE_SUBSCRIPTION_MAXIMUM_DURATION);

    var expectedFees = new BigDecimal("0.55");
    assertThat(fees).isEqualTo(expectedFees);
  }


}