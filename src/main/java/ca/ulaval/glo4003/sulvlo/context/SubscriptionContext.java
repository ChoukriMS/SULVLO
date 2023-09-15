package ca.ulaval.glo4003.sulvlo.context;

import static ca.ulaval.glo4003.sulvlo.infrastructure.util.email.EmailSenderImpl.LOGGER;

import ca.ulaval.glo4003.sulvlo.api.subscription.SubscriptionResource;
import ca.ulaval.glo4003.sulvlo.application.service.subscription.SubscriptionService;
import ca.ulaval.glo4003.sulvlo.domain.payment.information.CreditCardAssembler;
import ca.ulaval.glo4003.sulvlo.domain.payment.service.PaymentDomainService;
import ca.ulaval.glo4003.sulvlo.domain.payment.service.schoolFeesService.SchoolFeesService;
import ca.ulaval.glo4003.sulvlo.domain.subscription.SubscriptionAssembler;
import ca.ulaval.glo4003.sulvlo.domain.subscription.type.SubscriptionTypeAssembler;
import ca.ulaval.glo4003.sulvlo.domain.subscription.type.SubscriptionTypeDao;
import ca.ulaval.glo4003.sulvlo.domain.user.service.UserDomainService;
import ca.ulaval.glo4003.sulvlo.domain.util.semester.SemesterAssembler;
import ca.ulaval.glo4003.sulvlo.infrastructure.subscription.type.SubscriptionTypeDaoInMemory;


public class SubscriptionContext implements Context {

  private final ServiceLocator serviceLocator = ServiceLocator.getInstance();

  private SubscriptionTypeDao subscriptionTypeDao;

  private SubscriptionTypeAssembler subscriptionTypeAssembler;
  private SubscriptionAssembler subscriptionAssembler;
  private SchoolFeesService schoolFeesService;

  @Override
  public void registerContext() {
    registerRepositories();
    create();
  }

  @Override
  public void registerDomainServices() {
  }

  @Override
  public void createResource() {
    createSubscriptionResource();
  }

  private void registerRepositories() {
    subscriptionTypeDao = new SubscriptionTypeDaoInMemory();
  }

  private void create() {
    subscriptionTypeAssembler = new SubscriptionTypeAssembler();
    subscriptionAssembler = new SubscriptionAssembler(
        new CreditCardAssembler(),
        new SemesterAssembler()
    );
    schoolFeesService = new SchoolFeesService();
  }

  private void createSubscriptionResource() {
    LOGGER.info("Setup subscription resource dependencies (DOMAIN + INFRASTRUCTURE)");

    SubscriptionService subscriptionService = new SubscriptionService(subscriptionTypeDao,
        subscriptionTypeAssembler,
        subscriptionAssembler,
        serviceLocator.resolve(UserDomainService.class),
        serviceLocator.resolve(PaymentDomainService.class),
        schoolFeesService
    );

    SubscriptionResource subscriptionResource = new SubscriptionResource(subscriptionService);

    serviceLocator.register(SubscriptionResource.class, subscriptionResource);
  }
}
