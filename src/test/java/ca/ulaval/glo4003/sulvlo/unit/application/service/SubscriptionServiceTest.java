package ca.ulaval.glo4003.sulvlo.unit.application.service;

import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.CREDIT_CARD;
import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.EMAIL;
import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.SEMESTER;
import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.SEMESTER_STRING;
import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.SUBSCRIPTION_TYPE;
import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.TYPE;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;

import ca.ulaval.glo4003.sulvlo.api.subscription.dto.SubscriptionDto;
import ca.ulaval.glo4003.sulvlo.application.service.subscription.SubscriptionService;
import ca.ulaval.glo4003.sulvlo.domain.payment.service.PaymentDomainService;
import ca.ulaval.glo4003.sulvlo.domain.payment.service.schoolFeesService.SchoolFeesService;
import ca.ulaval.glo4003.sulvlo.domain.subscription.Subscription;
import ca.ulaval.glo4003.sulvlo.domain.subscription.SubscriptionAssembler;
import ca.ulaval.glo4003.sulvlo.domain.subscription.type.SubscriptionTypeAssembler;
import ca.ulaval.glo4003.sulvlo.domain.subscription.type.SubscriptionTypeDao;
import ca.ulaval.glo4003.sulvlo.domain.user.User;
import ca.ulaval.glo4003.sulvlo.domain.user.UserType;
import ca.ulaval.glo4003.sulvlo.domain.user.information.Gender;
import ca.ulaval.glo4003.sulvlo.domain.user.information.UserId;
import ca.ulaval.glo4003.sulvlo.domain.user.information.UserInformation;
import ca.ulaval.glo4003.sulvlo.domain.user.service.UserDomainService;
import ca.ulaval.glo4003.sulvlo.domain.util.email.EmailSender;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

  private static final String NAME = "name";
  private static final String IDUL = "idul";
  private static final int AGE = 20;
  private static final int HASHED_PWD = 111;
  private static final User user = new User(
      new UserInformation(NAME, EMAIL, AGE, LocalDate.now(), Gender.MALE,
          UUID.randomUUID(), HASHED_PWD), UserType.NORMAL, new UserId(IDUL));
  private static final boolean AUTOMATIC_PAY_AFTER_TRAVEL = true;
  private static final boolean AUTOMATIC_PAY_END_MONTH = true;
  private static final boolean PAY_WITH_SCHOOL_FEES = false;
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
  private SubscriptionTypeDao subscriptionTypeDao;
  @Mock
  private SubscriptionTypeAssembler subscriptionTypeAssembler;
  @Mock
  private SubscriptionAssembler subscriptionAssembler;
  @Mock
  private UserDomainService userDomainService;
  @Mock
  private PaymentDomainService paymentDomainService;
  @Mock
  private EmailSender emailSender;
  @Mock
  private SchoolFeesService schoolFeesService;

  private SubscriptionService subscriptionService;

  @BeforeEach
  void setup() {
    subscriptionService = new SubscriptionService(
        subscriptionTypeDao,
        subscriptionTypeAssembler,
        subscriptionAssembler,
        userDomainService,
        paymentDomainService,
        schoolFeesService);
  }

  @Test
  void givenSubscriptionDto_whenAddSubscription_thenSaveBankAccountAndSubscriptionAndSendEmail() {
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

    given(subscriptionTypeDao.findByType(TYPE)).willReturn(SUBSCRIPTION_TYPE);
    given(subscriptionAssembler.create(SUBSCRIPTION_TYPE, subscriptionDto)).willReturn(
        SUBSCRIPTION);
    given(userDomainService.isUserAccountVerified(IDUL)).willReturn(true);

    subscriptionService.addSubscription(subscriptionDto);

    verify(paymentDomainService).addSubscription(SUBSCRIPTION);
  }

  @Test
  void whenFindAllSubscriptionType_thenCallRepository() {
    subscriptionService.findAllSubscriptionType();

    verify(subscriptionTypeDao).findAll();
  }

}