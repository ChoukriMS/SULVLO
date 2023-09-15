package ca.ulaval.glo4003.sulvlo.domain.payment.information;

import java.math.BigDecimal;

public record PaymentInformation(
    CreditCardInformation creditCardInformation,
    BigDecimal amount
) {

}