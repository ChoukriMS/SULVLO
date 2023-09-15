package ca.ulaval.glo4003.sulvlo.domain.subscription;


import ca.ulaval.glo4003.sulvlo.api.subscription.dto.SubscriptionDto;
import ca.ulaval.glo4003.sulvlo.domain.payment.information.CreditCardAssembler;
import ca.ulaval.glo4003.sulvlo.domain.payment.information.CreditCardInformation;
import ca.ulaval.glo4003.sulvlo.domain.subscription.type.SubscriptionType;
import ca.ulaval.glo4003.sulvlo.domain.util.semester.Semester;
import ca.ulaval.glo4003.sulvlo.domain.util.semester.SemesterAssembler;

public class SubscriptionAssembler {

  private final CreditCardAssembler creditCardAssembler;
  private final SemesterAssembler semesterAssembler;

  public SubscriptionAssembler(CreditCardAssembler creditCardAssembler,
      SemesterAssembler semesterAssembler) {
    this.creditCardAssembler = creditCardAssembler;
    this.semesterAssembler = semesterAssembler;
  }

  public Subscription create(SubscriptionType subscriptionType, SubscriptionDto subscriptionDto) {
    CreditCardInformation creditCard = creditCardAssembler.create(subscriptionDto);
    Semester semester = semesterAssembler.create(subscriptionDto);

    return new Subscription(subscriptionType, subscriptionDto.idul(), creditCard, semester,
        subscriptionDto.automaticPaymentAfterTravel(),
        subscriptionDto.automaticPaymentEndMonth(),
        subscriptionDto.payWithSchoolFees());

  }
}