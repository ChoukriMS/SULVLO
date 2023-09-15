package ca.ulaval.glo4003.sulvlo.application.service.subscription;

import ca.ulaval.glo4003.sulvlo.api.subscription.dto.SubscriptionDto;
import ca.ulaval.glo4003.sulvlo.api.subscription.dto.SubscriptionTypeDto;
import ca.ulaval.glo4003.sulvlo.api.validation.exception.NotFullTimeStudentException;
import ca.ulaval.glo4003.sulvlo.api.validation.exception.StudentPassRequiredException;
import ca.ulaval.glo4003.sulvlo.domain.payment.service.PaymentDomainService;
import ca.ulaval.glo4003.sulvlo.domain.payment.service.schoolFeesService.SchoolFeesService;
import ca.ulaval.glo4003.sulvlo.domain.subscription.Subscription;
import ca.ulaval.glo4003.sulvlo.domain.subscription.SubscriptionAssembler;
import ca.ulaval.glo4003.sulvlo.domain.subscription.type.SubscriptionType;
import ca.ulaval.glo4003.sulvlo.domain.subscription.type.SubscriptionTypeAssembler;
import ca.ulaval.glo4003.sulvlo.domain.subscription.type.SubscriptionTypeDao;
import ca.ulaval.glo4003.sulvlo.domain.user.exception.InvalidActivationTokenException;
import ca.ulaval.glo4003.sulvlo.domain.user.service.UserDomainService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubscriptionService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionService.class);
  private final SubscriptionTypeDao subscriptionTypeDao;
  private final SubscriptionTypeAssembler subscriptionTypeAssembler;
  private final SubscriptionAssembler subscriptionAssembler;
  private final UserDomainService userDomainService;
  private final PaymentDomainService paymentDomainService;
  private final SchoolFeesService schoolFeesService;

  public SubscriptionService(SubscriptionTypeDao subscriptionTypeDao,
      SubscriptionTypeAssembler subscriptionTypeAssembler,
      SubscriptionAssembler subscriptionAssembler,
      UserDomainService userDomainService,
      PaymentDomainService paymentDomainService,
      SchoolFeesService schoolFeesService) {

    this.subscriptionTypeDao = subscriptionTypeDao;
    this.subscriptionTypeAssembler = subscriptionTypeAssembler;
    this.subscriptionAssembler = subscriptionAssembler;
    this.userDomainService = userDomainService;
    this.paymentDomainService = paymentDomainService;
    this.schoolFeesService = schoolFeesService;
  }

  private boolean isFullTimeStudent(SubscriptionDto subscriptionDto) {
    try {
      if (schoolFeesService.isFullTime(subscriptionDto.idul(),
          userDomainService.getEmailAddressFromIdul(subscriptionDto.idul()),
          subscriptionDto.semester())) {
        return true;
      }
    } catch (Exception err) {
      throw new NotFullTimeStudentException();
    }
    return false;
  }

  public List<SubscriptionTypeDto> findAllSubscriptionType() {
    LOGGER.info("Get all subscriptions Types");
    List<SubscriptionType> subscriptionTypes = subscriptionTypeDao.findAll();
    return subscriptionTypes.stream().map(subscriptionTypeAssembler::create).toList();
  }

  public void addSubscription(SubscriptionDto subscriptionDto) {
    LOGGER.info("Add Subscription {}", subscriptionDto);
    SubscriptionType subscriptionType = subscriptionTypeDao.findByType(
        subscriptionDto.subscriptionType());
    Subscription subscription = subscriptionAssembler.create(subscriptionType, subscriptionDto);

    if (!userDomainService.isUserAccountVerified(subscription.idul())) {
      throw new InvalidActivationTokenException();
    }
    if (subscription.payWithSchoolFees()) {
      if (subscription.subscriptionType().type() != "Student") {
        throw new StudentPassRequiredException();
      }
      if (!isFullTimeStudent(subscriptionDto)) {
        throw new NotFullTimeStudentException();
      }
      paymentDomainService.addStudentSubscription(subscription);
      schoolFeesService.addStudentPassToSchoolFees(subscriptionDto.idul(), subscriptionType.price(),
          subscription.semester().toString());
    } else {
      paymentDomainService.addSubscription(subscription);
    }
  }

}
