package ca.ulaval.glo4003.sulvlo.unit.api.truck;

import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.sulvlo.api.truck.TruckResource;
import ca.ulaval.glo4003.sulvlo.api.truck.dto.LoadBikesDto;
import ca.ulaval.glo4003.sulvlo.api.truck.dto.UnloadBikesDto;
import ca.ulaval.glo4003.sulvlo.api.truck.dto.UnloadBikesDto.UnloadBikeData;
import ca.ulaval.glo4003.sulvlo.api.truck.validation.TruckRequestsValidator;
import ca.ulaval.glo4003.sulvlo.application.service.truck.TruckService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TruckResourceTest {

  private static final String ANY_TRUCK_ID = "a7348b64-c7b7-4235-9f27-c33f99ff08c5";
  private static final String ANY_STATION_UNDER_MAINTENANCE_CODE = "VAN";
  private static final List<String> ANY_BIKES_LOCATIONS_FROM_STATION_UNDER_MAINTENANCE = List.of(
      "2");
  private static final LoadBikesDto ANY_LOAD_BIKES_DTO = new LoadBikesDto(
      ANY_STATION_UNDER_MAINTENANCE_CODE, ANY_BIKES_LOCATIONS_FROM_STATION_UNDER_MAINTENANCE);
  private static final String ANY_AVAILABLE_STATION_CODE = "DES";
  private static final List<String> ANY_EMPTY_LOCATION_IN_AVAILABLE_STATION = List.of("6");
  private static final UnloadBikeData ANY_UNLOAD_BIKE_DATE = new UnloadBikeData(
      ANY_AVAILABLE_STATION_CODE, ANY_EMPTY_LOCATION_IN_AVAILABLE_STATION);
  private static final List<UnloadBikeData> ANY_UNLOAD_BIKE_DATA_LIST = List.of(
      ANY_UNLOAD_BIKE_DATE);
  private static final UnloadBikesDto UNLOAD_BIKES_DTO = new UnloadBikesDto(
      ANY_UNLOAD_BIKE_DATA_LIST);

  @Mock
  private TruckService truckService;
  @Mock
  private TruckRequestsValidator truckRequestsValidator;
  private TruckResource truckResource;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    truckResource = new TruckResource(truckService, truckRequestsValidator);
  }

  @Test
  void whenListTrucks_thenTruckServiceFindAllTrucksIsCalled() {
    truckResource.listTrucks();

    verify(truckService).findAllTrucks();
  }

  @Test
  void whenLoadBikes_thenTruckRequestsValidatorValidateLoadBikesRequestIsCalled() {
    truckResource.loadBikes(ANY_TRUCK_ID, ANY_LOAD_BIKES_DTO);

    verify(truckRequestsValidator).validateLoadBikesRequest(ANY_TRUCK_ID, ANY_LOAD_BIKES_DTO);
  }

  @Test
  void whenLoadBikes_thenTruckServiceLoadBikesIsCalled() {
    truckResource.loadBikes(ANY_TRUCK_ID, ANY_LOAD_BIKES_DTO);

    verify(truckService).loadBikes(ANY_TRUCK_ID, ANY_STATION_UNDER_MAINTENANCE_CODE,
        ANY_BIKES_LOCATIONS_FROM_STATION_UNDER_MAINTENANCE);
  }

  @Test
  void whenUnloadBikes_thenTruckRequestsValidatorValidateUnloadBikesRequestIsCalled() {
    truckResource.unloadBikes(ANY_TRUCK_ID, UNLOAD_BIKES_DTO);

    verify(truckRequestsValidator).validateUnloadBikesRequest(ANY_TRUCK_ID, UNLOAD_BIKES_DTO);
  }

  @Test
  void whenUnloadBikes_thenTruckServiceUnloadIsCalled() {
    truckResource.unloadBikes(ANY_TRUCK_ID, UNLOAD_BIKES_DTO);

    verify(truckService).unload(ANY_TRUCK_ID, ANY_UNLOAD_BIKE_DATA_LIST);
  }

}