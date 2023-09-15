package ca.ulaval.glo4003.sulvlo.domain.station.service;

import ca.ulaval.glo4003.sulvlo.domain.payment.service.PaymentDomainService;
import ca.ulaval.glo4003.sulvlo.domain.station.exception.NoSubcriptionException;
import ca.ulaval.glo4003.sulvlo.domain.station.traveler.Traveler;
import ca.ulaval.glo4003.sulvlo.domain.station.traveler.TravelerFactory;
import ca.ulaval.glo4003.sulvlo.domain.station.traveler.TravelerRepository;
import ca.ulaval.glo4003.sulvlo.domain.user.User;
import ca.ulaval.glo4003.sulvlo.domain.user.UserType;
import ca.ulaval.glo4003.sulvlo.domain.user.service.UserDomainService;
import java.time.Duration;
import java.util.List;

public class TravelerDomainService {

  private final TravelerFactory travelerFactory;
  private final TravelerRepository travelerRepository;
  private final UserDomainService userDomainService;
  private final PaymentDomainService paymentDomainService;

  public TravelerDomainService(TravelerFactory travelerFactory,
      TravelerRepository travelerRepository, UserDomainService userDomainService,
      PaymentDomainService paymentDomainService) {
    this.travelerFactory = travelerFactory;
    this.travelerRepository = travelerRepository;
    this.userDomainService = userDomainService;
    this.paymentDomainService = paymentDomainService;
  }

  public Traveler createTraveler(String idul) {
    return travelerFactory.createTraveler(idul);
  }

  public Traveler findByIdul(String idul) {
    return travelerRepository.findByIdul(idul);
  }

  public void saveTraveler(Traveler traveler) {
    travelerRepository.save(traveler);
  }

  public void checkAccountValidity(String email) {
    User user = userDomainService.findByEmail(email);
    if (user.isBlocked()) {
      throw new NoSubcriptionException();
    }
  }

  public UserType travelerType(String idul) {
    return userDomainService.findUserByIdul(idul).getUserType();
  }

  public void sendTravelerBill(String idul, Duration amountOfTime) {
    paymentDomainService.sendTravelBill(idul, amountOfTime);
  }

  public List<Traveler> findAllTravelers() {
    return travelerRepository.findAllTravelers();
  }
}
