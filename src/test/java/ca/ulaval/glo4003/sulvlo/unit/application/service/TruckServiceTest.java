package ca.ulaval.glo4003.sulvlo.unit.application.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.sulvlo.application.service.truck.TruckService;
import ca.ulaval.glo4003.sulvlo.domain.bike.Bike;
import ca.ulaval.glo4003.sulvlo.domain.station.Station;
import ca.ulaval.glo4003.sulvlo.domain.station.service.StationDomainService;
import ca.ulaval.glo4003.sulvlo.domain.truck.Truck;
import ca.ulaval.glo4003.sulvlo.domain.truck.TruckAssembler;
import ca.ulaval.glo4003.sulvlo.domain.truck.TruckId;
import ca.ulaval.glo4003.sulvlo.domain.truck.TruckRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TruckServiceTest {

  private static final String ANY_TRUCK_ID_STRING = "b2569807-c13b-49a2-903d-0709f847f9d0";
  private static final TruckId ANY_TRUCK_ID = TruckId.fromString(ANY_TRUCK_ID_STRING);
  private static final String ANY_FROM_STATION_CODE = "VAN";
  private static final List<String> ANY_BIKES_LOCATIONS_STRING = List.of("2");
  private static final List<Integer> ANY_BIKES_LOCATION = List.of(2);

  @Mock
  private Bike bike;
  @Mock
  private Station station;
  @Mock
  private Truck truck;
  @Mock
  private StationDomainService stationDomainService;
  @Mock
  private TruckRepository truckRepository;
  @Mock
  private TruckAssembler truckAssembler;

  private List<Bike> bikesToBeLoaded;
  private TruckService truckService;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    setupTruckRepositoryMock();
    setupStationMock();
    setupStationRepositoryMock();
    truckService = new TruckService(stationDomainService, truckRepository, truckAssembler);
  }

  private void setupTruckRepositoryMock() {
    when(truckRepository.findAll()).thenReturn(List.of(truck));
    when(truckRepository.findById(ANY_TRUCK_ID)).thenReturn(truck);
  }

  private void setupStationMock() {
    bikesToBeLoaded = List.of(bike);
    when(station.findAvailableBikesByLocation(ANY_BIKES_LOCATION)).thenReturn(bikesToBeLoaded);
  }

  private void setupStationRepositoryMock() {
    when(stationDomainService.findUnderMaintenanceByCode(ANY_FROM_STATION_CODE)).thenReturn(
        station);
  }

  @Test
  void whenFindAll_thenTruckRepositoryFindAllIsCalled() {
    truckService.findAllTrucks();

    verify(truckRepository).findAll();
  }

  @Test
  void givenATruckRepositoryWithOneTruck_whenFindAll_thenTruckAssemblerCreateDtoIsCalledOneTime() {
    truckService.findAllTrucks();

    verify(truckAssembler, times(1)).createTruckDto(truck);
  }

  @Test
  void whenLoadBikes_thenTruckLoadBikesIsCalled() {
    truckService.loadBikes(ANY_TRUCK_ID_STRING, ANY_FROM_STATION_CODE, ANY_BIKES_LOCATIONS_STRING);

    verify(truck).loadBikes(bikesToBeLoaded);
  }

  @Test
  void whenLoadBikes_thenStationRemoveLoadedBikesIsCalled() {
    truckService.loadBikes(ANY_TRUCK_ID_STRING, ANY_FROM_STATION_CODE, ANY_BIKES_LOCATIONS_STRING);

    verify(station).removeLoadedBikes(ANY_BIKES_LOCATION);
  }

}