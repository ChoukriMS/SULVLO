package ca.ulaval.glo4003.sulvlo.infrastructure.subscription.type;

import ca.ulaval.glo4003.sulvlo.domain.subscription.type.SubscriptionType;
import java.util.List;

public class SubscriptionTypeDevDataFactory {

  public List<SubscriptionType> createMockData() {
    SubscriptionType base = new SubscriptionType("Base", 30, "10 minutes ilimités", 10);
    SubscriptionType premium = new SubscriptionType("Premium", 50, "30 minutes illimités", 30);
    SubscriptionType student = new SubscriptionType("Student", 50, "30 minutes illimités", 30);

    return List.of(base, premium, student);
  }
}
