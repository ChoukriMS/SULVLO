package ca.ulaval.glo4003.sulvlo.domain.payment.service;

import ca.ulaval.glo4003.sulvlo.domain.payment.PaymentProcessor;
import ca.ulaval.glo4003.sulvlo.domain.payment.exception.PaymentNotAutorized;
import ca.ulaval.glo4003.sulvlo.domain.payment.fees.ExtraFeesCalculator;
import ca.ulaval.glo4003.sulvlo.domain.payment.payer.Payer;
import ca.ulaval.glo4003.sulvlo.domain.payment.payer.PayerFactory;
import ca.ulaval.glo4003.sulvlo.domain.payment.payer.PayerRepository;
import ca.ulaval.glo4003.sulvlo.domain.payment.service.schoolFeesService.SchoolFeesService;
import ca.ulaval.glo4003.sulvlo.domain.subscription.Subscription;
import ca.ulaval.glo4003.sulvlo.domain.summary.SalesSummary;
import ca.ulaval.glo4003.sulvlo.domain.user.service.UserDomainService;
import ca.ulaval.glo4003.sulvlo.domain.util.email.EmailSender;
import ca.ulaval.glo4003.sulvlo.infrastructure.subscription.type.SemesterDevDataFactory;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaymentDomainService {

  public static final Logger LOGGER = LoggerFactory.getLogger(PaymentDomainService.class);
  private final PayerRepository payerRepository;
  private final EmailSender emailSender;
  private final UserDomainService userDomainService;
  private final PaymentProcessor paymentProcessor;
  private final PayerFactory payerFactory;
  private final ExtraFeesCalculator extraFeesCalculator;
  private final SalesSummary salesSummary;
  private final SemesterDevDataFactory semesterDevDataFactory;

  public PaymentDomainService(PayerRepository payerRepository,
      EmailSender emailSender,
      UserDomainService userDomainService,
      PaymentProcessor paymentProcessor,
      PayerFactory payerFactory,
      ExtraFeesCalculator extraFeesCalculator,
      SemesterDevDataFactory semesterDevDataFactory,
      SalesSummary salesSummary) {
    this.payerRepository = payerRepository;
    this.emailSender = emailSender;
    this.userDomainService = userDomainService;
    this.paymentProcessor = paymentProcessor;
    this.payerFactory = payerFactory;
    this.semesterDevDataFactory = semesterDevDataFactory;
    this.extraFeesCalculator = extraFeesCalculator;
    this.salesSummary = salesSummary;
  }

  private void sendUserBill(String email, Payer payer) {
    String body = String.format(
        "Fin du mois. Facture de %s$CAD. Total de vos frais supplémentaires accumuler sur vos différentes courses du mois.",
        payer.getExtraFeesAmount().toString());

    if (payer.hasDebt()) {
      body = String.format(
          "%s\nVous devez également vous aquitter des dettes non payés du mois précédent. Soit %s$CAD supplémentaires",
          body, payer.getDebtAmount().toString());
    }
    emailSender.send(email, body, "Facture SULVLO");
  }

  public void sendSaleSummary(String email) {
    salesSummary.calculateAverageFee(payerRepository.findAll());
    salesSummary.sendSaleSummary(email);
  }

  public void sendBillUsers() {
    for (Payer payer : payerRepository.findAll()) {
      if (!payer.hasExtraFees() && !payer.hasDebt()) {
        continue;
      }
      String email = userDomainService.getEmailAddressFromIdul(payer.getIdul());
      sendUserBill(email, payer);
    }
  }

  public void automaticPayExtraFeesUsers() {
    for (Payer payer : payerRepository.findAll()) {
      if (!payer.hasExtraFees()) {
        continue;
      }

      if (payer.hasAutomaticPaymentEndMonth()) {
        try {
          payer.payExtraFees(paymentProcessor);
        } catch (PaymentNotAutorized ignored) {
          LOGGER.info(String.format("payment of :%s is not authorized", payer.getIdul()));
        }
      } else {
        payer.feesBecomeDebt();
      }
      payerRepository.update(payer);
    }
  }

  public void validateStudentpassActivation() {
    for (Payer payer : payerRepository.findAll()) {
      if (!(payer.getSubscriptionType().type().equals("Student"))) {
        continue;
      }

      if (semesterDevDataFactory.getStartSemesterDate(payer.getSemester())
          .isBefore(LocalDate.now())) {
        userDomainService.unblockAccount(payer.getIdul());
      }
      payerRepository.update(payer);
    }
  }

  public void blockStudentAccountNotPaidBill(SchoolFeesService schoolFeesService)
      throws IOException {

    for (Payer payer : payerRepository.findAll()) {
      String email = userDomainService.getEmailAddressFromIdul(payer.getIdul());

      if (semesterDevDataFactory.getEndSemesterDate(payer.getSemester())
          .isBefore(LocalDate.now())) {
        if ((!schoolFeesService.getValidate(payer.getIdul(), email,
            payer.getSemester().getSeason() + payer.getSemester().getYear()).balancePaid)
            && (payer.getSubscriptionType().type().equals("Student"))) {
          userDomainService.blockAccount(payer.getIdul());
        }

      }
    }

  }

  public void blockUserAccountNotPaidBill() {
    for (Payer payer : payerRepository.findAll()) {
      if (!payer.hasDebt()) {
        continue;
      }
      userDomainService.blockAccount(payer.getIdul());
    }
  }

  public void addSubscription(Subscription subscription) {
    Payer payer = payerFactory.create(subscription.idul());
    payer.addSubscription(subscription);
    payerRepository.save(payer);

    try {
      payer.paySubscription(paymentProcessor);
      userDomainService.unblockAccount(payer.getIdul());
      salesSummary.addSubscriptionType(subscription.subscriptionType());
    } catch (PaymentNotAutorized exception) {
      userDomainService.blockAccount(payer.getIdul());
      throw new PaymentNotAutorized();
    }
    payerRepository.update(payer);
  }

  public void addStudentSubscription(Subscription subscription) {
    Payer payer = payerFactory.create(subscription.idul());
    payer.addSubscription(subscription);

    payerRepository.save(payer);
    if (semesterDevDataFactory.getStartSemesterDate(subscription.semester())
        .isBefore(LocalDate.now())) {
      userDomainService.unblockAccount(payer.getIdul());
    }
  }

  public void sendTravelBill(String idul, Duration time) {
    Payer payer = payerRepository.findByIdul(idul);
    int maximumTime = payer.getSubscriptionTypeDuration();

    BigDecimal extraFees = extraFeesCalculator.calculateFees(time, maximumTime);
    payer.addExtraFees(extraFees);

    if (payer.isAutomaticPaymentAfterTravel()) {
      payer.payTravelFeesDirectly(paymentProcessor);
    }
    payerRepository.update(payer);
  }

}
