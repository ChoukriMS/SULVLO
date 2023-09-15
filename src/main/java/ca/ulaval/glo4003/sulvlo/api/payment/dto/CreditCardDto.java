package ca.ulaval.glo4003.sulvlo.api.payment.dto;

public interface CreditCardDto {

  String creditCardNumber();

  int expirationMonth();

  int expirationYear();

  int ccv();


}
