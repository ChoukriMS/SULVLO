package ca.ulaval.glo4003.sulvlo.unit.infrastructure.subscription.type;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.sulvlo.domain.subscription.type.SubscriptionType;
import ca.ulaval.glo4003.sulvlo.infrastructure.subscription.type.SubscriptionTypeDaoInMemory;
import ca.ulaval.glo4003.sulvlo.infrastructure.subscription.type.exception.InvalidSubscriptionTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubscriptionTypeDaoInMemoryTest {

  private static final String TYPE = "base";
  private static final SubscriptionType SUBSCRIPTION_TYPE = new SubscriptionType("Base", 30,
      "10 minutes ilimités", 10);
  private static final String INVALID_TYPE = "invalidType";
  private SubscriptionTypeDaoInMemory subscriptionTypeDaoInMemory;

  @BeforeEach
  void setup() {
    subscriptionTypeDaoInMemory = new SubscriptionTypeDaoInMemory();
  }

  @Test
  void givenASubscriptionTypeName_whenFindByName_thenReturnSubscriptionType() {
    SubscriptionType subscriptionType = subscriptionTypeDaoInMemory.findByType(TYPE);

    assertThat(subscriptionType).isEqualTo(SUBSCRIPTION_TYPE);
  }

  @Test
  void givenInvalidSubscriptionTypeName_whenFindByName_thenThrow() {
    assertThrows(InvalidSubscriptionTypeException.class,
        () -> subscriptionTypeDaoInMemory.findByType(INVALID_TYPE));
  }
}