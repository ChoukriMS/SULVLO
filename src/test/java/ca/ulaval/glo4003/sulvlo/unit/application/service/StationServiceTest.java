package ca.ulaval.glo4003.sulvlo.unit.application.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.sulvlo.application.service.station.StationService;
import ca.ulaval.glo4003.sulvlo.application.service.station.exception.UnlockBikeException;
import ca.ulaval.glo4003.sulvlo.domain.bike.Bike;
import ca.ulaval.glo4003.sulvlo.domain.station.Station;
import ca.ulaval.glo4003.sulvlo.domain.station.StationAssembler;
import ca.ulaval.glo4003.sulvlo.domain.station.StationRepository;
import ca.ulaval.glo4003.sulvlo.domain.station.UnlockBikeToken;
import ca.ulaval.glo4003.sulvlo.domain.station.service.TravelerDomainService;
import ca.ulaval.glo4003.sulvlo.domain.station.service.TripDomainService;
import ca.ulaval.glo4003.sulvlo.domain.station.service.UniqueCodeDomainService;
import ca.ulaval.glo4003.sulvlo.domain.station.traveler.Traveler;
import ca.ulaval.glo4003.sulvlo.domain.station.traveler.exception.TravelerNotFoundException;
import ca.ulaval.glo4003.sulvlo.domain.station.trip.Trip;
import ca.ulaval.glo4003.sulvlo.domain.user.UserType;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StationServiceTest {

  private static final String EMAIL = "test@hotmail.com";
  private static final String UNIQUE_CODE = "1234567890";
  private static final String IDUL = "piscine7";
  private static final String STATION_CODE = "STAION_CODE";
  private static final String BIKE_LOCATION = "1";
  private static final boolean TRAVELER_ALREADY_RENTED_BIKE = true;
  private static final boolean TRAVELER_HAS_NO_BIKE = false;

  private static final LocalDateTime TIME1 = LocalDateTime.now();
  private static final LocalDateTime TIME2 = LocalDateTime.now().minusMinutes(5);

  private static final Duration DURATION = Duration.between(TIME1, TIME2);
  private final UnlockBikeToken unlockBikeToken = new UnlockBikeToken(UNIQUE_CODE,
      LocalDateTime.now());
  private final UserType userType = UserType.NORMAL;
  @Mock
  private TripDomainService tripDomainService;
  @Mock
  private TravelerDomainService travelerDomainService;
  @Mock
  private UniqueCodeDomainService uniqueCodeDomainService;
  @Mock
  private StationRepository stationRepository;
  @Mock
  private StationAssembler stationAssembler;
  @Mock
  private Station station;
  @Mock
  private Bike bike;
  @Mock
  private Traveler traveler;

  private StationService stationService;
  private List<Station> stationsList;

  @BeforeEach
  public void StationServiceSetup() {
    stationService = new StationService(stationRepository, stationAssembler, tripDomainService,
        travelerDomainService, uniqueCodeDomainService);
    stationsList = new ArrayList<>() {{
      add(station);
    }};
  }

  @Test
  void whenFindAllStations_thenStationRepositoryFindAllShouldBeCalled() {
    when(stationRepository.findAllAvailable()).thenReturn(stationsList);

    stationService.findAllAvailableStations();

    verify(stationAssembler, times(1)).assemble(station);
  }

  @Test
  void givenAStationDto_whenFindingAllStationUnderMaintenance_thenReturnListOfStations() {
    when(stationRepository.findAllUnderMaintenance()).thenReturn(stationsList);

    stationService.findAllUnderMaintenanceStations();

    verify(stationAssembler, times(1)).createStationUnderMaintenanceDto(station);
  }

  @Test
  void givenAEmail_whenCreatingUniqueCode_thenUniqueCodeIsCreated() {
    doNothing().when(travelerDomainService).checkAccountValidity(EMAIL);
    when(uniqueCodeDomainService.generateUnlockBikeToken()).thenReturn(unlockBikeToken);

    stationService.createUniqueCode(EMAIL);

    verify(uniqueCodeDomainService, times(1)).sendUniqueCode(EMAIL, UNIQUE_CODE);
  }

  @Test
  void givenRequirementsToUnlockBike_whenTravelerHasAlreadyHadATravel_thenBikeIsUnlock() {
    doNothing().when(uniqueCodeDomainService).isTokenValid(UNIQUE_CODE);
    when(stationRepository.findAvailableByCode(STATION_CODE)).thenReturn(station);
    when(station.findAvailableBikeByLocation(anyInt())).thenReturn(bike);
    when(travelerDomainService.findByIdul(IDUL)).thenReturn(traveler);
    when(traveler.hasUnlockedBike()).thenReturn(TRAVELER_HAS_NO_BIKE);
    doNothing().when(station).unlockBike(bike);
    doNothing().when(traveler).startTravel(STATION_CODE, bike);

    stationService.unlockBike(UNIQUE_CODE, STATION_CODE, BIKE_LOCATION, IDUL);

    verify(travelerDomainService, times(1)).saveTraveler(traveler);
  }

  @Test
  void givenRequirementsToUnlockBike_whenTravelerHasAlreadyUnlockedBike_thenUnlockBikeException() {
    doNothing().when(uniqueCodeDomainService).isTokenValid(UNIQUE_CODE);
    when(stationRepository.findAvailableByCode(STATION_CODE)).thenReturn(station);
    when(station.findAvailableBikeByLocation(anyInt())).thenReturn(bike);
    when(travelerDomainService.findByIdul(IDUL)).thenReturn(traveler);
    when(traveler.hasUnlockedBike()).thenReturn(TRAVELER_ALREADY_RENTED_BIKE);

    assertThrows(UnlockBikeException.class,
        () -> stationService.unlockBike(UNIQUE_CODE, STATION_CODE, BIKE_LOCATION, IDUL));
  }

  @Test
  void givenRequirementsToUnlockBike_whenFirstTravelFromUser_thenCreateTravelerAndUnlockBike() {
    doNothing().when(uniqueCodeDomainService).isTokenValid(UNIQUE_CODE);
    when(stationRepository.findAvailableByCode(STATION_CODE)).thenReturn(station);
    when(station.findAvailableBikeByLocation(anyInt())).thenReturn(bike);
    when(travelerDomainService.findByIdul(IDUL)).thenThrow(TravelerNotFoundException.class);
    when(travelerDomainService.createTraveler(IDUL)).thenReturn(traveler);
    doNothing().when(station).unlockBike(bike);
    doNothing().when(traveler).startTravel(STATION_CODE, bike);

    stationService.unlockBike(UNIQUE_CODE, STATION_CODE, BIKE_LOCATION, IDUL);

    verify(travelerDomainService, times(1)).saveTraveler(traveler);
  }

  @Test
  void givenStationCodeBikeLocationAndIdul_whenRetuningABike_thenBikeIsReturnToStaion() {
    when(travelerDomainService.findByIdul(IDUL)).thenReturn(traveler);
    when(stationRepository.findAvailableByCode(STATION_CODE)).thenReturn(station);
    when(traveler.getUnlockedBike()).thenReturn(bike);
    doNothing().when(station).returnBike(bike, 1);
    when(bike.calculateTravelDuration()).thenReturn(DURATION);
    when(traveler.getIdul()).thenReturn(IDUL);
    when(stationRepository.findAvailableByCode(STATION_CODE)).thenReturn(station);
    when(traveler.getUnlockStationCode()).thenReturn(STATION_CODE);
    doNothing().when(tripDomainService).save(any(Trip.class));
    doNothing().when(traveler).resetUnlockInfos();

    stationService.returnBike(STATION_CODE, BIKE_LOCATION, IDUL);

    verify(travelerDomainService, times(1)).sendTravelerBill(IDUL, DURATION);
  }


  @Test
  void givenAStationCode_whenTryingToStartMaintenance_thenStationInNowUnderMaintenance() {
    when(stationRepository.findAvailableByCode(STATION_CODE)).thenReturn(station);
    doNothing().when(stationRepository).deleteAvailable(STATION_CODE);

    stationService.startMaintenance(STATION_CODE);

    verify(stationRepository, times(1)).saveUnderMaintenance(station);
  }

  @Test
  void givenAStationCode_whenStoppingMaintenance_thenStationIsNowAvailable() {
    when(stationRepository.findUnderMaintenanceByCode(STATION_CODE)).thenReturn(station);
    doNothing().when(stationRepository).deleteUnderMaintenance(STATION_CODE);

    stationService.endMaintenance(STATION_CODE);

    verify(stationRepository, times(1)).saveAvailable(station);
  }
}
