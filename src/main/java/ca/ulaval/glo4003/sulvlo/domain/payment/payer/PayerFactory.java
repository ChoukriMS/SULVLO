package ca.ulaval.glo4003.sulvlo.domain.payment.payer;

public class PayerFactory {

  public PayerFactory() {
  }

  public Payer create(String idul) {
    return new Payer(idul);
  }

}