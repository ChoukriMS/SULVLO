package ca.ulaval.glo4003.sulvlo.unit.domain.payment;


import static ca.ulaval.glo4003.sulvlo.unit.domain.subscription.SubscriptionTestHelper.CREDIT_CARD;
import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import ca.ulaval.glo4003.sulvlo.domain.payment.PaymentAutorizer;
import ca.ulaval.glo4003.sulvlo.domain.payment.PaymentProcessor;
import ca.ulaval.glo4003.sulvlo.domain.payment.exception.PaymentNotAutorized;
import ca.ulaval.glo4003.sulvlo.domain.payment.information.CreditCardInformation;
import ca.ulaval.glo4003.sulvlo.domain.payment.information.PaymentInformation;
import ca.ulaval.glo4003.sulvlo.domain.payment.payer.Informations.Balance;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentProcessorTest {

  @Mock
  private PaymentAutorizer paymentAutorizer;
  private PaymentProcessor paymentProcessor;

  @BeforeEach
  void setup() {
    paymentProcessor = new PaymentProcessor(paymentAutorizer);
  }

  @Test
  void givenABalanceAndPaymentInformationWithSameAmountAsBalance_whenProcessPayment_ThenReturnsNewBalance() {

    PaymentInformation paymentInformation = createPaymentInformation(10);
    Balance balance = new Balance(BigDecimal.TEN);

    given(paymentAutorizer.autorize(paymentInformation)).willReturn(true);

    Balance balanceAfterPayment = paymentProcessor.processPayment(balance, paymentInformation);

    assertThat(balanceAfterPayment).isEqualTo(new Balance(BigDecimal.ZERO));
  }

  @Test
  void givenABalanceAndPaymentInformation_whenProcessPayment_ThenReturnsBalance() {

    PaymentInformation paymentInformation = createPaymentInformation(5);
    Balance balance = new Balance(BigDecimal.TEN);

    given(paymentAutorizer.autorize(paymentInformation)).willReturn(true);

    Balance balanceAfterPayment = paymentProcessor.processPayment(balance, paymentInformation);

    assertThat(balanceAfterPayment).isEqualTo(new Balance(BigDecimal.valueOf(5)));
  }

  @Test
  void givenABalanceAndPaymentInformationBiggerThanBalance_whenProcessPayment_ThenThrowsPaymentNotAuthorized() {

    PaymentInformation paymentInformation = createPaymentInformation(25);
    Balance balance = new Balance(BigDecimal.TEN);

    given(paymentAutorizer.autorize(paymentInformation)).willReturn(true);

    assertThrows(PaymentNotAutorized.class,
        () -> paymentProcessor.processPayment(balance, paymentInformation));
  }

  private PaymentInformation createPaymentInformation(int amountToCharge) {
    return new PaymentInformation(createCreditCardInformation(),
        BigDecimal.valueOf(amountToCharge));
  }

  private CreditCardInformation createCreditCardInformation() {
    return new CreditCardInformation(CREDIT_CARD.creditCardNumber(),
        CREDIT_CARD.expirationMonth(),
        CREDIT_CARD.expirationYear(),
        CREDIT_CARD.ccv());


  }


}