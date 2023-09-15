package ca.ulaval.glo4003.sulvlo.domain.payment;

import ca.ulaval.glo4003.sulvlo.domain.payment.information.PaymentInformation;

public interface PaymentAutorizer {

  boolean autorize(PaymentInformation paymentInformation);
}
