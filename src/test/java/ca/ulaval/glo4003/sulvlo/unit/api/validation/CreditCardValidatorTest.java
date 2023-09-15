package ca.ulaval.glo4003.sulvlo.unit.api.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.ulaval.glo4003.sulvlo.api.subscription.dto.SubscriptionDto;
import ca.ulaval.glo4003.sulvlo.api.validation.Nodes.CreditCard.CCardCcvValidation;
import ca.ulaval.glo4003.sulvlo.api.validation.Nodes.CreditCard.CCardExpirationValidator;
import ca.ulaval.glo4003.sulvlo.api.validation.Nodes.CreditCard.CCardNumberValidator;
import ca.ulaval.glo4003.sulvlo.api.validation.ValidationNode;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class CreditCardValidatorTest {

  private static final String SUBSCRIPTION_TYPE = "Premium";
  private static final String SEMESTER = "H23";
  private static final String IDUL = "recho25";
  private static final String CREDIT_CARD_NUMBER = "1234567891234567";
  private static final String INVALID_CREDIT_CARD_NUMBER = "invalid";
  private static final int EXPIRATION_MONTH = 12;
  private static final int EXPIRATION_YEAR = 2045;
  private static final int CCV = 123;
  private static final int INVALID_CCV = 12345;
  private static final boolean AUTOMATIC_PAY_AFTER_TRAVEL = true;
  private static final boolean AUTOMATIC_PAY_END_MONTH = true;
  private static final boolean PAY_WITH_SCHOOL_FEES = false;
  private static final LocalDate LOCAL_DATE = LocalDate.now();
  private static final int CURRENT_MONTH = LOCAL_DATE.getMonthValue();
  private static final int PAST_MONTH = CURRENT_MONTH - 1;
  private static final int CURRENT_YEAR = LOCAL_DATE.getYear();
  private final ValidationNode<SubscriptionDto> validationChain = ValidationNode.link(
      new CCardNumberValidator(),
      new CCardExpirationValidator(),
      new CCardCcvValidation()
  );
  private SubscriptionDto subscriptionDto;

  @Test
  void givenSubscriptionDtoWithInvalidCreditCardNumber_whenValidateCreditCard_thenThrowInvalidSubscriptionParameterException() {
    subscriptionDto = createSubscriptionDto(INVALID_CREDIT_CARD_NUMBER, EXPIRATION_MONTH,
        EXPIRATION_YEAR, CCV);

    assertFalse(validationChain.isValid(subscriptionDto));
  }

  @Test
  void givenSubscriptionDto_whenValidateCreditCard_shouldNotThrowException() {
    subscriptionDto = createSubscriptionDto(CREDIT_CARD_NUMBER, EXPIRATION_MONTH, EXPIRATION_YEAR,
        CCV);

    assertTrue(validationChain.isValid(subscriptionDto));
  }

  @Test
  void givenSubscriptionDtoWithInvalidCreditCardMonth_whenValidateCreditCard_thenThrowInvalidSubscriptionParameterException() {
    subscriptionDto = createSubscriptionDto(CREDIT_CARD_NUMBER, PAST_MONTH, CURRENT_YEAR, CCV);

    assertFalse(validationChain.isValid(subscriptionDto));
  }

  @Test
  void givenSubscriptionDtoWithInvalidCreditCardCCV_whenValidate_thenThrowInvalidSubscriptionParameterException() {
    subscriptionDto = createSubscriptionDto(CREDIT_CARD_NUMBER, EXPIRATION_MONTH, EXPIRATION_YEAR,
        INVALID_CCV);

    assertFalse(validationChain.isValid(subscriptionDto));
  }

  private SubscriptionDto createSubscriptionDto(String creditCardNumber, int expirationMonth,
      int expirationYear, int ccv) {
    return new SubscriptionDto(
        SUBSCRIPTION_TYPE,
        SEMESTER,
        IDUL,
        creditCardNumber,
        expirationMonth,
        expirationYear,
        ccv,
        AUTOMATIC_PAY_AFTER_TRAVEL,
        AUTOMATIC_PAY_END_MONTH,
        PAY_WITH_SCHOOL_FEES
    );
  }
}
