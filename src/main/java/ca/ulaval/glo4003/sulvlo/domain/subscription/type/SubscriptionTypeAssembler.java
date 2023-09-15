package ca.ulaval.glo4003.sulvlo.domain.subscription.type;


import ca.ulaval.glo4003.sulvlo.api.subscription.dto.SubscriptionTypeDto;

public class SubscriptionTypeAssembler {

  public SubscriptionTypeDto create(SubscriptionType subscriptionType) {
    return new SubscriptionTypeDto(subscriptionType.type(), subscriptionType.price(),
        subscriptionType.description(), subscriptionType.duration());
  }
}