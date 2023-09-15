package ca.ulaval.glo4003.sulvlo.unit.domain.station.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.sulvlo.domain.bike.Bike;
import ca.ulaval.glo4003.sulvlo.domain.station.Station;
import ca.ulaval.glo4003.sulvlo.domain.station.service.TripDomainService;
import ca.ulaval.glo4003.sulvlo.domain.station.trip.Trip;
import ca.ulaval.glo4003.sulvlo.domain.travel.Travel;
import ca.ulaval.glo4003.sulvlo.domain.travel.service.TravelDomainService;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TripDomainServiceTest {


  private static final String TRAVELER_IDUL = "IDULTEST";
  private static final String UNLOCKED_CODE = "UNLOCKED_CODE";
  private static final String RETURNED_CODE = "RETURNED_CODE";
  @Mock
  private TravelDomainService travelDomainService;
  @Mock
  private Bike bike;
  @Mock
  private Station unlockStation;
  @Mock
  private Station returnStation;

  private final Travel travel = new Travel(UUID.randomUUID(), TRAVELER_IDUL,
      UNLOCKED_CODE, LocalDateTime.now(), LocalDateTime.now().minusMinutes(1), RETURNED_CODE,
      LocalDateTime.now().getMonth());

  private Trip trip;
  private TripDomainService tripDomainService;

  @Test
  void giveATrip_whenSavingTripIntoRepository_thenTripIsAddedToRepository() {
    trip = new Trip(TRAVELER_IDUL, bike, unlockStation, returnStation);
    tripDomainService = new TripDomainService(travelDomainService);
    when(travelDomainService.create(TRAVELER_IDUL, bike, unlockStation, returnStation)).thenReturn(
        travel);

    tripDomainService.save(trip);

    verify(travelDomainService, times(1)).save(travel);
  }

}
