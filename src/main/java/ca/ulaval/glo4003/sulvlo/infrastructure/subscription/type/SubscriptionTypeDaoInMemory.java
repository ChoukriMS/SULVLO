package ca.ulaval.glo4003.sulvlo.infrastructure.subscription.type;

import ca.ulaval.glo4003.sulvlo.domain.subscription.type.SubscriptionType;
import ca.ulaval.glo4003.sulvlo.domain.subscription.type.SubscriptionTypeDao;
import ca.ulaval.glo4003.sulvlo.infrastructure.subscription.type.exception.InvalidSubscriptionTypeException;
import java.util.List;

public class SubscriptionTypeDaoInMemory implements SubscriptionTypeDao {

  private final List<SubscriptionType> subscriptionTypes;

  public SubscriptionTypeDaoInMemory() {
    SubscriptionTypeDevDataFactory subscriptionTypeDevDataFactory = new SubscriptionTypeDevDataFactory();
    subscriptionTypes = subscriptionTypeDevDataFactory.createMockData();
  }

  @Override
  public List<SubscriptionType> findAll() {
    return subscriptionTypes;
  }

  @Override
  public SubscriptionType findByType(String type) {
    return subscriptionTypes.stream().filter(subscription -> subscription.type()
            .equalsIgnoreCase(type))
        .findFirst()
        .orElseThrow(InvalidSubscriptionTypeException::new);
  }

}
