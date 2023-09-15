package ca.ulaval.glo4003.sulvlo.domain.subscription.type;

public record SubscriptionType(
    String type,
    int price,
    String description,
    int duration
) {

}

