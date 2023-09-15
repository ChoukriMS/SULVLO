package ca.ulaval.glo4003.sulvlo.domain.payment.service.schoolFeesService;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Validate {

  public final String idul;
  public final Boolean balancePaid;
  public final int balance;

  public Validate(@JsonProperty("idul") String idul,
      @JsonProperty("balance_paid") Boolean balancePaid,
      @JsonProperty("balance") int balance) {
    this.idul = idul;
    this.balancePaid = balancePaid;
    this.balance = balance;
  }
}
