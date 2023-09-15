package ca.ulaval.glo4003.sulvlo.domain.payment.payer;

import ca.ulaval.glo4003.sulvlo.domain.payment.PaymentProcessor;
import ca.ulaval.glo4003.sulvlo.domain.payment.exception.PaymentNotAutorized;
import ca.ulaval.glo4003.sulvlo.domain.payment.information.CreditCardInformation;
import ca.ulaval.glo4003.sulvlo.domain.payment.information.PaymentInformation;
import ca.ulaval.glo4003.sulvlo.domain.payment.payer.Informations.Balance;
import ca.ulaval.glo4003.sulvlo.domain.subscription.Subscription;
import ca.ulaval.glo4003.sulvlo.domain.subscription.type.SubscriptionType;
import ca.ulaval.glo4003.sulvlo.domain.util.semester.Semester;
import java.math.BigDecimal;

public class Payer {

  private final String idul;
  private Balance debt;
  private Balance fees;
  private Semester semester;
  private CreditCardInformation creditCardInformation;
  private SubscriptionType subscriptionType;
  private boolean automaticPaymentEndMonth;
  private boolean automaticPaymentAfterTravel;
  private boolean hasPaidSubscription;

  public Payer(String idul) {
    this.idul = idul;
    this.debt = new Balance();
    this.fees = new Balance();
    this.subscriptionType = null;
    this.creditCardInformation = null;
    this.automaticPaymentEndMonth = false;
    this.automaticPaymentAfterTravel = false;
    this.hasPaidSubscription = false;
  }

  public void addSubscription(Subscription subscription) {
    subscriptionType = subscription.subscriptionType();
    creditCardInformation = subscription.creditCard();
    automaticPaymentEndMonth = subscription.automaticPaymentEndMonth();
    automaticPaymentAfterTravel = subscription.automaticPaymentAfterTravel();
    semester = subscription.semester();
  }

  public void paySubscription(PaymentProcessor paymentProcessor) {
    Balance subBalance = new Balance(new BigDecimal(subscriptionType.price()));
    PaymentInformation paymentInformation = new PaymentInformation(creditCardInformation,
        subBalance.amount());

    paymentProcessor.processPayment(subBalance, paymentInformation);
    hasPaidSubscription = true;
  }

  public void payExtraFees(PaymentProcessor paymentProcessor) throws PaymentNotAutorized {
    Balance feesBalance = new Balance(fees);
    PaymentInformation paymentInformation = new PaymentInformation(creditCardInformation,
        feesBalance.amount());

    feesBalance = paymentProcessor.processPayment(feesBalance, paymentInformation);
    fees = feesBalance;
  }

  public void payDebt(PaymentProcessor paymentProcessor) throws PaymentNotAutorized {
    Balance debtBalance = new Balance(debt);
    PaymentInformation paymentInformation = new PaymentInformation(creditCardInformation,
        debtBalance.amount());

    debtBalance = paymentProcessor.processPayment(debtBalance, paymentInformation);
    debt = debtBalance;
  }

  public void feesBecomeDebt() {
    debt.add(fees.amount());
    fees = new Balance(new BigDecimal(0));
  }


  public void payTravelFeesDirectly(PaymentProcessor paymentProcessor)
      throws PaymentNotAutorized {
    Balance feesBalance = new Balance(fees);
    PaymentInformation paymentInformation = new PaymentInformation(creditCardInformation,
        fees.amount());

    feesBalance = paymentProcessor.processPayment(feesBalance, paymentInformation);
    fees = feesBalance;
  }

  public void addExtraFees(BigDecimal amount) {
    fees.add(amount);
  }

  public String getIdul() {
    return this.idul;
  }

  public Semester getSemester() {
    return this.semester;
  }

  public int getSubscriptionTypeDuration() {
    return subscriptionType.duration();
  }

  public SubscriptionType getSubscriptionType() {
    return subscriptionType;
  }

  public BigDecimal getDebtAmount() {
    return debt.amount();
  }

  public BigDecimal getExtraFeesAmount() {
    return fees.amount();
  }

  public boolean hasExtraFees() {
    return !fees.isEmpty();
  }

  public boolean hasDebt() {
    return !debt.isEmpty();
  }

  public boolean hasAutomaticPaymentEndMonth() {
    return this.automaticPaymentEndMonth;
  }

  public boolean hasPaidSubscription() {
    return hasPaidSubscription;
  }

  public boolean isAutomaticPaymentAfterTravel() {
    return automaticPaymentAfterTravel;
  }

  public void setAutomaticPaymentAfterTravel(boolean automaticPaymentAfterTravel) {
    this.automaticPaymentAfterTravel = automaticPaymentAfterTravel;
  }

  public void payTechnicienSubscription() {
    hasPaidSubscription = true;
  }

  public void setAutomaticPaymentEndMonth(boolean automaticPaymentEndMonth) {
    this.automaticPaymentEndMonth = automaticPaymentEndMonth;
  }

}
