package ca.ulaval.glo4003.sulvlo.unit.api.station;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.sulvlo.api.station.StationResource;
import ca.ulaval.glo4003.sulvlo.api.station.validation.StationRequestsValidator;
import ca.ulaval.glo4003.sulvlo.application.service.station.StationService;
import ca.ulaval.glo4003.sulvlo.domain.station.traveler.TravelerFactory;
import ca.ulaval.glo4003.sulvlo.domain.travel.TravelFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class StationResourceImplTest {

  @Mock
  private StationService stationService;
  @Mock
  private StationRequestsValidator stationRequestsValidator;
  @Mock
  private TravelerFactory travelerFactory;
  @Mock
  private TravelFactory travelFactory;
  private StationResource stationResourceImpl;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    stationResourceImpl = new StationResource(stationService, stationRequestsValidator);
  }

  @Test
  public void whenListStations_thenStationServiceFindAllStationsShouldBeCalled() {
    stationResourceImpl.listAvailableStations();

    verify(stationService, times(1)).findAllAvailableStations();
  }

}