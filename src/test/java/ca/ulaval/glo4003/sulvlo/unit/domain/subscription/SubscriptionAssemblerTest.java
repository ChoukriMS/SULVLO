package ca.ulaval.glo4003.sulvlo.unit.domain.subscription;

import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.AUTOMATIC_PAY_AFTER_TRAVEL;
import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.AUTOMATIC_PAY_END_MONTH;
import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.CREDIT_CARD;
import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.DESCRIPTION;
import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.DURATION;
import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.IDUL;
import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.PAY_WITH_SCHOOL_FEES;
import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.PRICE;
import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.SEMESTER;
import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.SEMESTER_STRING;
import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.SUBSCRIPTION_TYPE;
import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.TYPE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

import ca.ulaval.glo4003.sulvlo.api.subscription.dto.SubscriptionDto;
import ca.ulaval.glo4003.sulvlo.domain.payment.information.CreditCardAssembler;
import ca.ulaval.glo4003.sulvlo.domain.subscription.Subscription;
import ca.ulaval.glo4003.sulvlo.domain.subscription.SubscriptionAssembler;
import ca.ulaval.glo4003.sulvlo.domain.subscription.type.SubscriptionType;
import ca.ulaval.glo4003.sulvlo.domain.util.semester.SemesterAssembler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SubscriptionAssemblerTest {

  private static final Subscription SUBSCRIPTION = new Subscription(
      SUBSCRIPTION_TYPE,
      IDUL,
      CREDIT_CARD,
      SEMESTER,
      AUTOMATIC_PAY_AFTER_TRAVEL,
      AUTOMATIC_PAY_END_MONTH,
      PAY_WITH_SCHOOL_FEES
  );
  @Mock
  private CreditCardAssembler creditCardAssembler;
  @Mock
  private SemesterAssembler semesterAssembler;
  private SubscriptionAssembler subscriptionAssembler;


  @BeforeEach
  void setup() {
    subscriptionAssembler = new SubscriptionAssembler(creditCardAssembler, semesterAssembler);
  }


  @Test
  void givenSubscriptionTypeAndSubscriptionDto_whenAssemble_thenReturnsSubscription() {

    SubscriptionType subscriptionType = new SubscriptionType(TYPE, PRICE, DESCRIPTION, DURATION);
    SubscriptionDto subscriptionDto = new SubscriptionDto(
        SUBSCRIPTION_TYPE.type(),
        SEMESTER_STRING,
        IDUL,
        CREDIT_CARD.creditCardNumber(),
        CREDIT_CARD.expirationMonth(),
        CREDIT_CARD.expirationYear(),
        CREDIT_CARD.ccv(),
        AUTOMATIC_PAY_AFTER_TRAVEL,
        AUTOMATIC_PAY_END_MONTH,
        PAY_WITH_SCHOOL_FEES
    );

    given(creditCardAssembler.create(subscriptionDto)).willReturn(CREDIT_CARD);
    given(semesterAssembler.create(subscriptionDto)).willReturn(SEMESTER);

    var subscription = subscriptionAssembler.create(subscriptionType, subscriptionDto);

    assertThat(subscription).isEqualTo(SUBSCRIPTION);
  }


}