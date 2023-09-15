package ca.ulaval.glo4003.sulvlo.api.subscription.dto;

import ca.ulaval.glo4003.sulvlo.api.payment.dto.CreditCardDto;
import ca.ulaval.glo4003.sulvlo.api.user.dto.IdulDto;

public record SubscriptionDto(
    String subscriptionType,
    String semester,
    String idul,
    String creditCardNumber,
    int expirationMonth,
    int expirationYear,
    int ccv,
    boolean automaticPaymentAfterTravel,
    boolean automaticPaymentEndMonth,
    boolean payWithSchoolFees

) implements IdulDto, CreditCardDto {

}
