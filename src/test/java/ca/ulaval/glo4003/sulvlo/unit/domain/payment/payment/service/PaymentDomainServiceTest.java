package ca.ulaval.glo4003.sulvlo.unit.domain.payment.payment.service;


import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.AUTOMATIC_PAY_AFTER_TRAVEL;
import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.AUTOMATIC_PAY_END_MONTH;
import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.CREDIT_CARD;
import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.PAY_WITH_SCHOOL_FEES;
import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.SEMESTER;
import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.SUBSCRIPTION_TYPE;
import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;

import ca.ulaval.glo4003.sulvlo.domain.payment.PaymentProcessor;
import ca.ulaval.glo4003.sulvlo.domain.payment.fees.ExtraFeesCalculator;
import ca.ulaval.glo4003.sulvlo.domain.payment.payer.Payer;
import ca.ulaval.glo4003.sulvlo.domain.payment.payer.PayerFactory;
import ca.ulaval.glo4003.sulvlo.domain.payment.payer.PayerRepository;
import ca.ulaval.glo4003.sulvlo.domain.payment.service.PaymentDomainService;
import ca.ulaval.glo4003.sulvlo.domain.subscription.Subscription;
import ca.ulaval.glo4003.sulvlo.domain.summary.SalesSummary;
import ca.ulaval.glo4003.sulvlo.domain.user.service.UserDomainService;
import ca.ulaval.glo4003.sulvlo.domain.util.email.EmailSender;
import ca.ulaval.glo4003.sulvlo.infrastructure.subscription.type.SemesterDevDataFactory;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentDomainServiceTest {

  private static final String IDUL = "idul1";
  private static final String EMAIL = "email@test.ca";
  private static final String SUBJECT = "Facture SULVLO";
  @Mock
  private PayerRepository payerRepository;
  @Mock
  private EmailSender emailSender;
  @Mock
  private UserDomainService userDomainService;
  @Mock
  private PaymentProcessor paymentProcessor;
  @Mock
  private PayerFactory payerFactory;
  @Mock
  private ExtraFeesCalculator extraFeesCalculator;
  @Mock
  private SalesSummary salesSummary;
  @Mock
  private SemesterDevDataFactory semesterDevDataFactory;
  private PaymentDomainService paymentDomainService;

  private static String createEmailBillBody(Payer payer) {
    return String.format(
        "Fin du mois. Facture de %s$CAD. Total de vos frais supplémentaires accumuler sur vos différentes courses du mois.",
        payer.getExtraFeesAmount().toString());
  }

  private static String createEmailBillBodyWithDebt(Payer payer) {
    return String.format(
        "%s\nVous devez également vous aquitter des dettes non payés du mois précédent. Soit %s$CAD supplémentaires",
        createEmailBillBody(payer), payer.getDebtAmount().toString());
  }

  @BeforeEach
  void setup() {
    paymentDomainService = new PaymentDomainService(payerRepository, emailSender, userDomainService,
        paymentProcessor, payerFactory, extraFeesCalculator, semesterDevDataFactory, salesSummary);
  }

  @Test
  void givenAPayer_whenSendBillUsers_thenSendEmailWithTheBill() {
    Payer payer = new Payer(IDUL);
    payer.addExtraFees(BigDecimal.valueOf(10));

    given(payerRepository.findAll()).willReturn(List.of(payer));
    given(userDomainService.getEmailAddressFromIdul(payer.getIdul())).willReturn(EMAIL);

    String body = createEmailBillBody(payer);
    paymentDomainService.sendBillUsers();

    Mockito.verify(emailSender).send(EMAIL, body, SUBJECT);
  }

  @Test
  void givenAPayerWithDebt_whenSendBillUsers_thenSendEmailWithTheBillWithDebtInfo() {
    Payer payer = new Payer(IDUL);
    payer.addExtraFees(BigDecimal.valueOf(10));
    payer.feesBecomeDebt();

    given(payerRepository.findAll()).willReturn(List.of(payer));
    given(userDomainService.getEmailAddressFromIdul(payer.getIdul())).willReturn(EMAIL);

    String body = createEmailBillBodyWithDebt(payer);
    paymentDomainService.sendBillUsers();

    Mockito.verify(emailSender).send(EMAIL, body, SUBJECT);
  }

  @Test
  void givenAPayerWithoutExtraFees_whenAutomaticPayExtraFeesUsers_thenPayerIsNotCharged() {
    Payer payer = new Payer(IDUL);

    given(payerRepository.findAll()).willReturn(List.of(payer));

    paymentDomainService.automaticPayExtraFeesUsers();

    Mockito.verifyNoMoreInteractions(payerRepository);
  }

  @Test
  void givenAPayerWithExtraFees_whenAutomaticPayExtraFeesUsers_thenPayerIsCharged() {
    Payer payer = new Payer(IDUL);
    payer.addExtraFees(BigDecimal.valueOf(10));

    given(payerRepository.findAll()).willReturn(List.of(payer));

    paymentDomainService.automaticPayExtraFeesUsers();

    Mockito.verify(payerRepository).update(payer);
  }

  @Test
  void givenAPayerWithExtraFeesAndAutomaticPayment_whenAutomaticPayExtraFeesUsers_thenPayerIsCharged() {
    Payer payer = new Payer(IDUL);
    payer.addExtraFees(BigDecimal.valueOf(10));
    payer.setAutomaticPaymentEndMonth(true);

    given(payerRepository.findAll()).willReturn(List.of(payer));

    paymentDomainService.automaticPayExtraFeesUsers();

    assertThat(payer.getDebtAmount()).isEqualTo(BigDecimal.valueOf(0));
    Mockito.verify(payerRepository).update(payer);
  }

  @Test
  void givenAPayerWithDebt_whenBlockUserAccountNotPaidBill_UserIsBlocked() {
    Payer payer = new Payer(IDUL);
    payer.addExtraFees(BigDecimal.valueOf(10));
    payer.feesBecomeDebt();

    given(payerRepository.findAll()).willReturn(List.of(payer));

    paymentDomainService.blockUserAccountNotPaidBill();

    Mockito.verify(userDomainService).blockAccount(payer.getIdul());
  }

  @Test
  void givenAPayerWithoutDebt_whenBlockUserAccountNotPaidBill_UserIsNotBlocked() {
    Payer payer = new Payer(IDUL);

    given(payerRepository.findAll()).willReturn(List.of(payer));

    paymentDomainService.blockUserAccountNotPaidBill();

    Mockito.verifyNoInteractions(userDomainService);
  }

  @Test
  void givenASubscription_whenAddSubscription_PayerIsCreatedAndSubscriptionIsAdded() {

    Subscription subscription = createSubscription();
    Payer payer = new Payer(IDUL);

    given(payerFactory.create(subscription.idul())).willReturn(payer);

    paymentDomainService.addSubscription(subscription);
    Mockito.verify(payerRepository).save(payer);
    Mockito.verify(userDomainService).unblockAccount(payer.getIdul());
    Mockito.verify(salesSummary).addSubscriptionType(subscription.subscriptionType());
    Mockito.verify(payerRepository).update(payer);
  }

  @Test
  void givenAPayerWithASubscription_whenSendTravelBill_PayerIsCharged() {

    Payer payer = new Payer(IDUL);
    payer.addSubscription(createSubscription());
    given(payerRepository.findByIdul(payer.getIdul())).willReturn(payer);

    Duration duration = Duration.of(20, ChronoUnit.MINUTES);

    given(extraFeesCalculator.calculateFees(duration, 10)).willReturn(BigDecimal.valueOf(0.5));

    paymentDomainService.sendTravelBill(IDUL, duration);

    Mockito.verify(payerRepository).update(payer);
  }

  private Subscription createSubscription() {
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