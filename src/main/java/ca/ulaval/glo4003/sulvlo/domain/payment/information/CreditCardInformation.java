package ca.ulaval.glo4003.sulvlo.domain.payment.information;

public record CreditCardInformation(
    String creditCardNumber,
    int expirationMonth,
    int expirationYear,
    int ccv
) {

}
