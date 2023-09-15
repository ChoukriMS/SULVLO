package ca.ulaval.glo4003.sulvlo.unit.domain.station.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.sulvlo.domain.payment.service.PaymentDomainService;
import ca.ulaval.glo4003.sulvlo.domain.station.exception.NoSubcriptionException;
import ca.ulaval.glo4003.sulvlo.domain.station.service.TravelerDomainService;
import ca.ulaval.glo4003.sulvlo.domain.station.traveler.Traveler;
import ca.ulaval.glo4003.sulvlo.domain.station.traveler.TravelerFactory;
import ca.ulaval.glo4003.sulvlo.domain.station.traveler.TravelerRepository;
import ca.ulaval.glo4003.sulvlo.domain.user.User;
import ca.ulaval.glo4003.sulvlo.domain.user.service.UserDomainService;
import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TravelerDomainServiceTest {

  private static final String IDUL_TRAVELER = "Test1";
  private static final String CLIENT_EMAIL = "Test@hotmail.com";
  private static final boolean USER_NOT_BLOCKED = false;
  private static final boolean USER_IS_BLOCKED = true;
  private static final Duration DURATION = Duration.between(LocalDateTime.now(),
      LocalDateTime.now().minusMinutes(1));
  @Mock
  private TravelerFactory travelerFactory;
  @Mock
  private TravelerRepository travelerRepository;
  @Mock
  private UserDomainService userDomainService;
  @Mock
  private PaymentDomainService paymentDomainService;

  @Mock
  private User user;
  private Traveler traveler;
  private TravelerDomainService travelerDomainService;

  @BeforeEach
  void setUp() {
    traveler = new Traveler(IDUL_TRAVELER);

    travelerDomainService = new TravelerDomainService(travelerFactory, travelerRepository,
        userDomainService, paymentDomainService);

  }

  @Test
  void givenAnIdul_whenCreatingTraveler_thenTravelerIsCreated() {
    when(travelerFactory.createTraveler(IDUL_TRAVELER)).thenReturn(traveler);

    Traveler returnTraveler = travelerDomainService.createTraveler(IDUL_TRAVELER);

    assertEquals(traveler, returnTraveler);
  }

  @Test
  void givenAnIdul_whenCheckingIfIdulIsLinkedToTraveler_thenTravelerIsReturned() {
    when(travelerRepository.findByIdul(IDUL_TRAVELER)).thenReturn(traveler);

    Traveler returnTraveler = travelerDomainService.findByIdul(IDUL_TRAVELER);

    assertEquals(traveler, returnTraveler);
  }

  @Test
  void givenTraveler_whenSavingNewTraveler_thenTravelerIsSavedInRepository() {

    travelerDomainService.saveTraveler(traveler);

    verify(travelerRepository, times(1)).save(traveler);
  }

  @Test
  void givenAnEmail_whenCheckAccountValidityIsValid_thenUserIsValidated() {
    when(userDomainService.findByEmail(CLIENT_EMAIL)).thenReturn(user);
    when(user.isBlocked()).thenReturn(USER_NOT_BLOCKED);

    travelerDomainService.checkAccountValidity(CLIENT_EMAIL);

    verify(userDomainService, times(1)).findByEmail(CLIENT_EMAIL);
  }

  @Test
  void givenAnEmail_whenCheckAccountValidityIsNotValid_thenNoSubcriptionException() {
    when(userDomainService.findByEmail(CLIENT_EMAIL)).thenReturn(user);
    when(user.isBlocked()).thenReturn(USER_IS_BLOCKED);

    assertThrows(NoSubcriptionException.class, () -> {
      travelerDomainService.checkAccountValidity(CLIENT_EMAIL);
    });
  }

  @Test
  void givenAnIdulAndDuration_whenSendingTravelerBill_thenCallPaymentDomainService() {
    travelerDomainService.sendTravelerBill(IDUL_TRAVELER, DURATION);

    verify(paymentDomainService, times(1)).sendTravelBill(IDUL_TRAVELER, DURATION);
  }

}
