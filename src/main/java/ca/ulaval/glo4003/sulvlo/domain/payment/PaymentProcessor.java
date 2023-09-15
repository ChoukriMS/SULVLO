package ca.ulaval.glo4003.sulvlo.domain.payment;

import ca.ulaval.glo4003.sulvlo.domain.payment.exception.PaymentNotAutorized;
import ca.ulaval.glo4003.sulvlo.domain.payment.information.PaymentInformation;
import ca.ulaval.glo4003.sulvlo.domain.payment.payer.Informations.Balance;

public class PaymentProcessor {

  private final PaymentAutorizer paymentAutorizer;

  public PaymentProcessor(PaymentAutorizer paymentAutorizer) {
    this.paymentAutorizer = paymentAutorizer;
  }

  public Balance processPayment(Balance balance, PaymentInformation paymentInformation) {
    if (!paymentAutorizer.autorize(paymentInformation)
        || balance.isSufficient(paymentInformation.amount())) {
      throw new PaymentNotAutorized();
    }
    balance.sub(paymentInformation.amount());
    return balance;
  }

}
