package ca.ulaval.glo4003.sulvlo.api.subscription.dto;

public record SubscriptionTypeDto(
    String type,
    int price,
    String description,
    int duration
) {

}
