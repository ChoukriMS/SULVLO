package ca.ulaval.glo4003.sulvlo.unit.domain.payment.payment.payer;

import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.AUTOMATIC_PAY_AFTER_TRAVEL;
import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.AUTOMATIC_PAY_END_MONTH;
import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.CREDIT_CARD;
import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.PAY_WITH_SCHOOL_FEES;
import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.SEMESTER;
import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.SUBSCRIPTION_TYPE;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.sulvlo.domain.payment.PaymentProcessor;
import ca.ulaval.glo4003.sulvlo.domain.payment.information.CreditCardInformation;
import ca.ulaval.glo4003.sulvlo.domain.payment.information.PaymentInformation;
import ca.ulaval.glo4003.sulvlo.domain.payment.payer.Informations.Balance;
import ca.ulaval.glo4003.sulvlo.domain.payment.payer.Payer;
import ca.ulaval.glo4003.sulvlo.domain.subscription.Subscription;
import ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PayerTest {

  private static final String IDUL = "idul1";
  private static final BigDecimal SUBSCRIPTION_PRICE = BigDecimal.valueOf(
      SUBSCRIPTION_TYPE.price());
  private static final BigDecimal EXTRA_FEES = BigDecimal.valueOf(10);
  @Mock
  PaymentProcessor paymentProcessor;
  private Payer payer;

  @BeforeEach
  void setup() {
    payer = new Payer(IDUL);
  }

  @Test
  void givenPayerWithASubscription_whenPaySubscription_ThenCallPaymentProcessor() {
    payer.addSubscription(createSubscription());
    payer.paySubscription(paymentProcessor);

    var paymentInformation = new PaymentInformation(createCreditCardInformation(),
        SUBSCRIPTION_PRICE);

    verify(
        paymentProcessor).processPayment(new Balance(SUBSCRIPTION_PRICE),
        paymentInformation);
  }

  @Test
  void givenPayerWithExtraFees_whenPayTravelFeesDirectly_ThenCallPaymentProcessor() {
    payer.addSubscription(createSubscription());
    payer.addExtraFees(EXTRA_FEES);
    payer.payTravelFeesDirectly(paymentProcessor);

    var paymentInformation = new PaymentInformation(createCreditCardInformation(),
        EXTRA_FEES);

    verify(
        paymentProcessor).processPayment(new Balance(EXTRA_FEES),
        paymentInformation);
  }


  private CreditCardInformation createCreditCardInformation() {
    return new CreditCardInformation(CREDIT_CARD.creditCardNumber(),
        CREDIT_CARD.expirationMonth(),
        CREDIT_CARD.expirationYear(),
        CREDIT_CARD.ccv());


  }

  private Subscription createSubscription() {
    return new Subscription(
        SUBSCRIPTION_TYPE,
        SubscriptionTestHelper.IDUL,
        CREDIT_CARD,
        SEMESTER,
        AUTOMATIC_PAY_AFTER_TRAVEL,
        AUTOMATIC_PAY_END_MONTH,
        PAY_WITH_SCHOOL_FEES
    );
  }


}