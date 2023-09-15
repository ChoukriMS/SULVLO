package ca.ulaval.glo4003.sulvlo.infrastructure.util.payment;

import ca.ulaval.glo4003.sulvlo.domain.payment.PaymentAutorizer;
import ca.ulaval.glo4003.sulvlo.domain.payment.information.PaymentInformation;

public class PaymentAutorizerStub implements PaymentAutorizer {

  @Override
  public boolean autorize(PaymentInformation creditCardInformation) {
    return true;
  }
}