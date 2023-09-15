package ca.ulaval.glo4003.sulvlo.domain.subscription;

import ca.ulaval.glo4003.sulvlo.domain.payment.information.CreditCardInformation;
import ca.ulaval.glo4003.sulvlo.domain.subscription.type.SubscriptionType;
import ca.ulaval.glo4003.sulvlo.domain.util.semester.Semester;

public record Subscription(
    SubscriptionType subscriptionType,
    String idul,
    CreditCardInformation creditCard,
    Semester semester,
    boolean automaticPaymentAfterTravel,
    boolean automaticPaymentEndMonth,
    boolean payWithSchoolFees

) {

  public int subscriptionTypePrice() {
    return subscriptionType.price();
  }

  @Override
  public String toString() {
    return "Subscription{" +
        "subscriptionType=" + subscriptionType +
        ", idul='" + idul + '\'' +
        ", creditCard=" + creditCard +
        ", semester=" + semester +
        '}';
  }
}
