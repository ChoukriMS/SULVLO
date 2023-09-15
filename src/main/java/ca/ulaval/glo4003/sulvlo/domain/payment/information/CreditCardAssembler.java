package ca.ulaval.glo4003.sulvlo.domain.payment.information;


import ca.ulaval.glo4003.sulvlo.api.payment.dto.CreditCardDto;
import ca.ulaval.glo4003.sulvlo.api.subscription.dto.SubscriptionDto;

public class CreditCardAssembler {

  public CreditCardInformation create(SubscriptionDto subscriptionDto) {
    return new CreditCardInformation(subscriptionDto.creditCardNumber(),
        subscriptionDto.expirationMonth(), subscriptionDto.expirationYear(), subscriptionDto.ccv());
  }

  public CreditCardInformation create(CreditCardDto creditCardDto) {
    return new CreditCardInformation(creditCardDto.creditCardNumber(),
        creditCardDto.expirationMonth(), creditCardDto.expirationYear(), creditCardDto.ccv());
  }

}
