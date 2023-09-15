package ca.ulaval.glo4003.sulvlo.unit.domain.payment.payment.payer.Informations;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.sulvlo.domain.payment.payer.Informations.Balance;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class BalanceTest {

  @Test
  void givenABalanceSmallerThanTheAmount_whenCheckingIsSufficient_ThenReturnsTrue() {

    Balance balance = new Balance(BigDecimal.valueOf(9));

    assertThat(balance.isSufficient(BigDecimal.valueOf(10))).isTrue();
  }

  @Test
  void givenABalanceBiggerThanTheAmount_whenCheckingIsSufficient_ThenReturnsTrue() {

    Balance balance = new Balance(BigDecimal.valueOf(111));

    assertThat(balance.isSufficient(BigDecimal.valueOf(10))).isFalse();
  }

  @Test
  void givenABalanceEqualToTheAmount_whenCheckingIsSufficient_ThenReturnsTrue() {

    Balance balance = new Balance(BigDecimal.valueOf(10));

    assertThat(balance.isSufficient(BigDecimal.valueOf(10))).isFalse();
  }


}