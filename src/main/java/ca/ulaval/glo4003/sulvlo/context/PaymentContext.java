package ca.ulaval.glo4003.sulvlo.context;

import static ca.ulaval.glo4003.sulvlo.infrastructure.util.email.EmailSenderImpl.LOGGER;

import ca.ulaval.glo4003.sulvlo.api.payment.PaymentResource;
import ca.ulaval.glo4003.sulvlo.application.service.payment.PaymentService;
import ca.ulaval.glo4003.sulvlo.domain.payment.PaymentAutorizer;
import ca.ulaval.glo4003.sulvlo.domain.payment.PaymentProcessor;
import ca.ulaval.glo4003.sulvlo.domain.payment.cron.SchedulePaymentJob;
import ca.ulaval.glo4003.sulvlo.domain.payment.fees.ExtraFeesCalculator;
import ca.ulaval.glo4003.sulvlo.domain.payment.payer.PayerFactory;
import ca.ulaval.glo4003.sulvlo.domain.payment.payer.PayerRepository;
import ca.ulaval.glo4003.sulvlo.domain.payment.service.PaymentDomainService;
import ca.ulaval.glo4003.sulvlo.domain.summary.SalesSummary;
import ca.ulaval.glo4003.sulvlo.domain.user.service.UserDomainService;
import ca.ulaval.glo4003.sulvlo.domain.util.email.EmailSender;
import ca.ulaval.glo4003.sulvlo.infrastructure.config.JsonFileReader;
import ca.ulaval.glo4003.sulvlo.infrastructure.payer.PayerRepositoryInMemory;
import ca.ulaval.glo4003.sulvlo.infrastructure.subscription.type.SemesterDevDataFactory;
import ca.ulaval.glo4003.sulvlo.infrastructure.util.payment.PaymentAutorizerStub;
import org.quartz.SchedulerException;

public class PaymentContext implements Context {

  private final ServiceLocator serviceLocator = ServiceLocator.getInstance();

  private PayerRepository payerRepository;
  private PaymentDomainService paymentDomainService;

  private PaymentAutorizer paymentAutorizer;
  private PaymentProcessor paymentProcessor;
  private PayerFactory payerFactory;
  private ExtraFeesCalculator extraFeesCalculator;
  private SemesterDevDataFactory semesterDevDataFactory;
  private SalesSummary salesSummary;

  @Override
  public void registerContext() {
    registerRepositories();
    create();
  }

  @Override
  public void registerDomainServices() {
    createDomainServices();
  }

  @Override
  public void createResource() {
    createPaymentResource();
    createJob();
  }

  private void registerRepositories() {
    payerRepository = new PayerRepositoryInMemory();
  }

  private void create() {
    salesSummary = new SalesSummary(serviceLocator.resolve(EmailSender.class));
    paymentAutorizer = new PaymentAutorizerStub();
    paymentProcessor = new PaymentProcessor(paymentAutorizer);
    payerFactory = new PayerFactory();
    extraFeesCalculator = new ExtraFeesCalculator();
    semesterDevDataFactory = new SemesterDevDataFactory(new JsonFileReader());
  }

  private void createDomainServices() {
    paymentDomainService = new PaymentDomainService(
        payerRepository,
        serviceLocator.resolve(EmailSender.class),
        serviceLocator.resolve(UserDomainService.class),
        paymentProcessor,
        payerFactory,
        extraFeesCalculator,
        semesterDevDataFactory,
        salesSummary
    );

    serviceLocator.register(PaymentDomainService.class, paymentDomainService);
  }

  private void createPaymentResource() {
    LOGGER.info("Setup payment resource dependencies (DOMAIN + INFRASTRUCTURE)");

    PaymentService paymentService = new PaymentService(
        payerRepository,
        serviceLocator.resolve(UserDomainService.class),
        paymentProcessor,
        serviceLocator.resolve(EmailSender.class)
    );

    PaymentResource paymentResource = new PaymentResource(paymentService);

    serviceLocator.register(PaymentResource.class, paymentResource);
  }

  private void createJob() {
    try {
      new SchedulePaymentJob(paymentDomainService).start();
    } catch (SchedulerException exception) {
      LOGGER.error("Payment jobs has not started : " + exception);
    }
  }

}
