package ca.ulaval.glo4003.sulvlo.unit.domain.payment.payment.information;

import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.AUTOMATIC_PAY_AFTER_TRAVEL;
import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.AUTOMATIC_PAY_END_MONTH;
import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.CREDIT_CARD;
import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.IDUL;
import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.PAY_WITH_SCHOOL_FEES;
import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.SEMESTER_STRING;
import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.SUBSCRIPTION_TYPE;
import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.sulvlo.api.subscription.dto.SubscriptionDto;
import ca.ulaval.glo4003.sulvlo.domain.payment.information.CreditCardAssembler;
import ca.ulaval.glo4003.sulvlo.domain.payment.information.CreditCardInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreditCardAssemblerTest {

  private CreditCardAssembler creditCardAssembler;

  @BeforeEach
  void setup() {
    creditCardAssembler = new CreditCardAssembler();
  }

  @Test
  void givenASubscriptionDto_whenAssemble_ThenReturnCreditCardInformation() {
    SubscriptionDto subscriptionDto = createSubscriptionDto();

    CreditCardInformation creditCardInformation = creditCardAssembler.create(subscriptionDto);

    CreditCardInformation expectedCreditCardInformation = createCreditCardInformation();
    assertThat(creditCardInformation).isEqualTo(expectedCreditCardInformation);
  }


  private CreditCardInformation createCreditCardInformation() {
    return new CreditCardInformation(CREDIT_CARD.creditCardNumber(),
        CREDIT_CARD.expirationMonth(),
        CREDIT_CARD.expirationYear(),
        CREDIT_CARD.ccv());


  }

  private SubscriptionDto createSubscriptionDto() {
    return new SubscriptionDto(
        SUBSCRIPTION_TYPE.type(),
        SEMESTER_STRING,
        IDUL,
        CREDIT_CARD.creditCardNumber(),
        CREDIT_CARD.expirationMonth(),
        CREDIT_CARD.expirationYear(),
        CREDIT_CARD.ccv(),
        AUTOMATIC_PAY_AFTER_TRAVEL,
        AUTOMATIC_PAY_END_MONTH,
        PAY_WITH_SCHOOL_FEES);
  }


}