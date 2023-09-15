package ca.ulaval.glo4003.sulvlo.domain.payment.payer.Informations;

import com.google.common.base.Objects;
import java.math.BigDecimal;

public class Balance {

  public BigDecimal balance;

  public Balance(Balance balance) {
    this.balance = balance.amount();
  }

  public Balance(BigDecimal amount) {
    balance = amount;
  }

  public Balance() {
    balance = BigDecimal.valueOf(0);
  }

  public boolean isSufficient(BigDecimal amount) {
    return balance.compareTo(amount) < 0;
  }

  public void add(BigDecimal amount) {
    balance = balance.add(amount);
  }

  public void sub(BigDecimal amount) {
    balance = balance.subtract(amount);
  }

  public BigDecimal amount() {
    return balance;
  }

  public boolean isEmpty() {
    return balance.compareTo(BigDecimal.ZERO) == 0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Balance that = (Balance) o;
    return Objects.equal(balance, that.balance);
  }

}
