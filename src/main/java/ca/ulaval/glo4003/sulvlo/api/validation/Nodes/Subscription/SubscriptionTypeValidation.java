package ca.ulaval.glo4003.sulvlo.api.validation.Nodes.Subscription;

import ca.ulaval.glo4003.sulvlo.api.subscription.dto.SubscriptionDto;
import ca.ulaval.glo4003.sulvlo.api.validation.ValidationNode;
import java.util.Arrays;
import java.util.List;


public class SubscriptionTypeValidation extends ValidationNode<SubscriptionDto> {

  private static final List<String> validSubscriptionTypes = Arrays.asList("Premium", "Base",
      "Student");

  private static boolean isValidSubscriptionType(String subscriptionType) {
    return validSubscriptionTypes.contains(subscriptionType);
  }

  @Override
  public boolean isValid(SubscriptionDto model) {
    if (model.subscriptionType() == null || !isValidSubscriptionType(model.subscriptionType())) {
      return false;
    }
    return validNext(model);
  }

}
