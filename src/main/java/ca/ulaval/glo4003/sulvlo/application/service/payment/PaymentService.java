package ca.ulaval.glo4003.sulvlo.application.service.payment;

import ca.ulaval.glo4003.sulvlo.api.payment.dto.AutomaticPaymentDto;
import ca.ulaval.glo4003.sulvlo.api.payment.dto.BalanceDto;
import ca.ulaval.glo4003.sulvlo.application.exception.AlreadyPaidException;
import ca.ulaval.glo4003.sulvlo.domain.payment.PaymentProcessor;
import ca.ulaval.glo4003.sulvlo.domain.payment.payer.Payer;
import ca.ulaval.glo4003.sulvlo.domain.payment.payer.PayerRepository;
import ca.ulaval.glo4003.sulvlo.domain.user.service.UserDomainService;
import ca.ulaval.glo4003.sulvlo.domain.util.email.EmailSender;

public class PaymentService {

  private final PayerRepository payerRepository;
  private final UserDomainService userDomainService;
  private final PaymentProcessor paymentProcessor;
  private final EmailSender emailSender;

  public PaymentService(PayerRepository payerRepository,
      UserDomainService userDomainService,
      PaymentProcessor paymentProcessor,
      EmailSender emailSender) {
    this.payerRepository = payerRepository;
    this.userDomainService = userDomainService;
    this.paymentProcessor = paymentProcessor;
    this.emailSender = emailSender;
  }

  public void addAutomaticPaymentEndMonth(String idul, AutomaticPaymentDto dto) {
    Payer payer = payerRepository.findByIdul(idul);

    payer.setAutomaticPaymentEndMonth(dto.automaticPayment());
    payerRepository.update(payer);
  }

  public void addAutomaticPaymentAfterTravel(String idul, AutomaticPaymentDto dto) {
    Payer payer = payerRepository.findByIdul(idul);

    payer.setAutomaticPaymentAfterTravel(dto.automaticPayment());
    payerRepository.update(payer);
  }

  public void payExtraFees(String idul) {
    Payer payer = payerRepository.findByIdul(idul);

    if (!payer.hasExtraFees()) {
      throw new AlreadyPaidException();
    }
    payer.payExtraFees(paymentProcessor);
    payerRepository.update(payer);
  }

  public void payDebt(String idul) {
    Payer payer = payerRepository.findByIdul(idul);

    if (!payer.hasDebt()) {
      throw new AlreadyPaidException();
    }
    payer.payDebt(paymentProcessor);
    payerRepository.update(payer);
  }

  public void paySubscription(String idul) {
    Payer payer = payerRepository.findByIdul(idul);

    if (payer.hasPaidSubscription()) {
      throw new AlreadyPaidException();
    }
    payer.paySubscription(paymentProcessor);
    payerRepository.update(payer);

    userDomainService.unblockAccount(idul);
    emailSender.send(userDomainService.getEmailAddressFromIdul(idul),
        "You have paid your subscribtion.", "SULVLO Facture");
  }

  public BalanceDto getBalance(String idul) {
    Payer payer = payerRepository.findByIdul(idul);

    return new BalanceDto(payer.getDebtAmount().toString(), payer.getExtraFeesAmount().toString());
  }

}
