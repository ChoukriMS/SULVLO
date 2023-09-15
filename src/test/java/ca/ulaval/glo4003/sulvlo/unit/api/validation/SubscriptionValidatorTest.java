package ca.ulaval.glo4003.sulvlo.unit.api.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;

import ca.ulaval.glo4003.sulvlo.api.subscription.dto.SubscriptionDto;
import ca.ulaval.glo4003.sulvlo.api.validation.Nodes.CreditCard.CCardCcvValidation;
import ca.ulaval.glo4003.sulvlo.api.validation.Nodes.CreditCard.CCardExpirationValidator;
import ca.ulaval.glo4003.sulvlo.api.validation.Nodes.CreditCard.CCardNumberValidator;
import ca.ulaval.glo4003.sulvlo.api.validation.Nodes.IdulValidation;
import ca.ulaval.glo4003.sulvlo.api.validation.Nodes.Subscription.SemesterValidation;
import ca.ulaval.glo4003.sulvlo.api.validation.Nodes.Subscription.SubscriptionTypeValidation;
import ca.ulaval.glo4003.sulvlo.api.validation.ValidationNode;
import org.junit.jupiter.api.Test;

class SubscriptionValidatorTest {

  private static final String SUBSCRIPTION_TYPE = "Premium";
  private static final String SEMESTER = "H23";
  private static final String CREDIT_CARD_NUMBER = "12344567891234567";
  private static final int EXPIRATION_MONTH = 12;
  private static final boolean AUTOMATIC_PAY_AFTER_TRAVEL = true;
  private static final boolean AUTOMATIC_PAY_END_MONTH = true;
  private static final boolean PAY_WITH_SCHOOL_FEES = false;
  private static final String IDUL = "idul23";
  private static final String INVALID_IDUL = "";
  private static final int EXPIRATION_YEAR = 2045;
  private static final int CCV = 123;
  private static final String INVALID_SEMESTER = "UneMauvaiseSaison";

  private final ValidationNode<SubscriptionDto> validationChain = ValidationNode.link(
      new IdulValidation(),
      new SemesterValidation(),
      new SubscriptionTypeValidation(),
      new CCardNumberValidator(),
      new CCardExpirationValidator(),
      new CCardCcvValidation()
  );

  @Test
  void givenSubscription_whenInvalidIdul_shouldThrowException() {
    SubscriptionDto invalidIdul = createSubscriptionDto(SEMESTER, INVALID_IDUL);

    assertFalse(validationChain.isValid(invalidIdul));
  }


  @Test
  void givenSubscription_whenInvalidSemester_shouldThrowException() {
    SubscriptionDto invalidSemester = createSubscriptionDto(INVALID_SEMESTER, IDUL);

    assertFalse(validationChain.isValid(invalidSemester));
  }

  private SubscriptionDto createSubscriptionDto(String semester, String idul) {
    return new SubscriptionDto(
        SUBSCRIPTION_TYPE,
        semester,
        idul,
        CREDIT_CARD_NUMBER,
        EXPIRATION_MONTH,
        EXPIRATION_YEAR,
        CCV,
        AUTOMATIC_PAY_AFTER_TRAVEL,
        AUTOMATIC_PAY_END_MONTH,
        PAY_WITH_SCHOOL_FEES
    );
  }

}
