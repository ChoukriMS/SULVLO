package ca.ulaval.glo4003.sulvlo.domain.subscription.type;


import java.util.List;

public interface SubscriptionTypeDao {

  List<SubscriptionType> findAll();

  SubscriptionType findByType(String subscriptionType);

}
