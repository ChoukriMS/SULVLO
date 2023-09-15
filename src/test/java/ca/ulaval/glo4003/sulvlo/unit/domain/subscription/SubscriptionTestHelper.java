package ca.ulaval.glo4003.sulvlo.unit.domain.subscription;

import ca.ulaval.glo4003.sulvlo.domain.payment.information.CreditCardInformation;
import ca.ulaval.glo4003.sulvlo.domain.subscription.Subscription;
import ca.ulaval.glo4003.sulvlo.domain.subscription.type.SubscriptionType;
import ca.ulaval.glo4003.sulvlo.domain.util.semester.Semester;

public class SubscriptionTestHelper {

  public static final String EMAIL = "cabival1@gmail.com";
  public static final String IDUL = "idul";
  public static final String TYPE = "base";
  public static final int PRICE = 1;
  public static final String DESCRIPTION = "description";
  public static final int DURATION = 10;
  public static final SubscriptionType SUBSCRIPTION_TYPE = new SubscriptionType(TYPE, PRICE,
      DESCRIPTION, DURATION);
  public static final String CREDIT_CARD_NUMBER = "creditCardHash";
  public static final int EXPIRATION_MONTH = 1;
  public static final int EXPIRATION_DAY = 20;
  public static final int CCV = 111;
  public static final CreditCardInformation CREDIT_CARD = new CreditCardInformation(
      CREDIT_CARD_NUMBER, EXPIRATION_MONTH,
      EXPIRATION_DAY,
      CCV);
  public static final Semester SEMESTER = new Semester("H", 23);
  public static final String SEMESTER_STRING = "H23";
  public static final boolean AUTOMATIC_PAY_AFTER_TRAVEL = true;
  public static final boolean AUTOMATIC_PAY_END_MONTH = true;
  public static final boolean PAY_WITH_SCHOOL_FEES = false;

  public Subscription createSubscription() {
    return new Subscription(
        SUBSCRIPTION_TYPE,
        IDUL,
        CREDIT_CARD,
        SEMESTER,
        AUTOMATIC_PAY_AFTER_TRAVEL,
        AUTOMATIC_PAY_END_MONTH,
        PAY_WITH_SCHOOL_FEES
    );
  }


}
