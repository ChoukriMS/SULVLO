package ca.ulaval.glo4003.sulvlo.unit.infrastructure.station;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.sulvlo.domain.station.Station;
import ca.ulaval.glo4003.sulvlo.domain.station.exception.StationNotFoundException;
import ca.ulaval.glo4003.sulvlo.infrastructure.station.StationDevDataFactory;
import ca.ulaval.glo4003.sulvlo.infrastructure.station.StationRepositoryInMemory;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class StationRepositoryInMemoryTest {

  private static final String ANY_STATION_CODE = "CODE_1";
  private static final String ANOTHER_ANY_STATION_CODE = "CODE_2";
  private static final String UNEXISTING_STATION_CODE = "CODE_113";
  private static final int NB_OF_AVAILABLE_STATIONS = 1;
  private static final int NB_OF_AVAILABLE_STATIONS_AFTER_SAVE = 2;

  @Mock
  private Station station, anotherStation;
  @Mock
  private StationDevDataFactory stationDataFactory;
  private StationRepositoryInMemory stationRepositoryInMemory;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    Mockito.when(station.getCode()).thenReturn(ANY_STATION_CODE);
    Mockito.when(stationDataFactory.createMockData()).thenReturn(List.of(station));
    stationRepositoryInMemory = new StationRepositoryInMemory(stationDataFactory);
  }

  @Test
  public void givenRepositoryWithOneStation_whenFindAll_thenReturnListOfStationsWithOneElement() {
    assertEquals(NB_OF_AVAILABLE_STATIONS, stationRepositoryInMemory.findAllAvailable().size());
  }

  @Test
  public void givenAnExistingStationCode_whenFindAvailableByCode_thenReturnTheCorrespondingStation() {
    assertEquals(station, stationRepositoryInMemory.findAvailableByCode(ANY_STATION_CODE));
  }

  @Test
  public void givenAnUnexistingStationCode_whenFindAvailableByCode_thenThrowStationNotFoundException() {
    assertThrows(StationNotFoundException.class, () -> {
      stationRepositoryInMemory.findAvailableByCode(UNEXISTING_STATION_CODE);
    });
  }

  @Test
  public void givenAnotherStation_whenSaveAvailable_thenShouldBeAddedToRepository() {
    Mockito.when(anotherStation.getCode()).thenReturn(ANOTHER_ANY_STATION_CODE);

    stationRepositoryInMemory.saveAvailable(anotherStation);

    assertEquals(NB_OF_AVAILABLE_STATIONS_AFTER_SAVE,
        stationRepositoryInMemory.findAllAvailable().size());
    assertEquals(anotherStation,
        stationRepositoryInMemory.findAvailableByCode(ANOTHER_ANY_STATION_CODE));
  }

}
