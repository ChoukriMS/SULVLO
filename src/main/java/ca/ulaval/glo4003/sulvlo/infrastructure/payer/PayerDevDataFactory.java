package ca.ulaval.glo4003.sulvlo.infrastructure.payer;

import ca.ulaval.glo4003.sulvlo.domain.payment.payer.Payer;
import ca.ulaval.glo4003.sulvlo.domain.subscription.Subscription;
import ca.ulaval.glo4003.sulvlo.domain.subscription.type.SubscriptionType;
import java.util.List;

public class PayerDevDataFactory {

  public List<Payer> createMockData() {
    Payer technicien = new Payer("MAGAU1");

    SubscriptionType subscriptionType = new SubscriptionType(
        "TECHNICIEN",
        0,
        "Pass for technicien",
        30
    );
    Subscription subscription = new Subscription(
        subscriptionType,
        "MAGAU1",
        null,
        null,
        false,
        false,
        false
    );

    technicien.addSubscription(subscription);
    technicien.payTechnicienSubscription();

    return List.of(technicien);
  }

}
