package ca.ulaval.glo4003.sulvlo.unit.domain.station;


import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.ulaval.glo4003.sulvlo.api.bike.dto.BikeDto;
import ca.ulaval.glo4003.sulvlo.api.station.dto.StationDto;
import ca.ulaval.glo4003.sulvlo.domain.bike.Bike;
import ca.ulaval.glo4003.sulvlo.domain.bike.BikeStatus;
import ca.ulaval.glo4003.sulvlo.domain.bike.EnergyLevel;
import ca.ulaval.glo4003.sulvlo.domain.station.Station;
import ca.ulaval.glo4003.sulvlo.domain.station.StationAssembler;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StationAssemblerTest {

  private static final String LOCATION = "LOCATION";
  private static final String NAME = "NAME";
  private static final String CODE = "CODE";
  private static final int CURRENT_CAPACITY = 1;
  private static final int MAX_CAPACITY = 1;
  private static final int BIKE_LOCATION = 1;
  private static final int BIKE_ENERGY_LEVEL = 56;
  private static final ArrayList<Integer> EMPTY_BIKES_LOCATIONS = new ArrayList<>();
  private static final List<Bike> AVAILABLE_BIKE_LIST = List.of(
      new Bike(BIKE_LOCATION, new EnergyLevel(BigDecimal.valueOf(BIKE_ENERGY_LEVEL))));
  private static final List<BikeDto> AVAILABLE_BIKE_DTO_LIST = List.of(
      new BikeDto(BIKE_LOCATION, new EnergyLevel(BigDecimal.valueOf(BIKE_ENERGY_LEVEL)).toString(),
          BikeStatus.AVAILABLE.toString()));

  private StationAssembler stationAssembler;

  private static StationDto createStationDto() {
    String date = LocalDateTime.now().format(
        DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));

    return new StationDto(date, LOCATION, NAME, CODE,
        CURRENT_CAPACITY, EMPTY_BIKES_LOCATIONS, AVAILABLE_BIKE_DTO_LIST);
  }

  @BeforeEach
  void setUp() {
    stationAssembler = new StationAssembler();
  }

  @Test
  void givenStation_whenCreateStationDto_thenStationDtoShouldBeReturned() {
    Station station = new Station(LOCATION, NAME, CODE, CURRENT_CAPACITY,
        MAX_CAPACITY, AVAILABLE_BIKE_LIST, EMPTY_BIKES_LOCATIONS);

    station.setCurrentDateTime(LocalDateTime.now());

    StationDto stationDto = stationAssembler.assemble(station);

    StationDto expectedStationDto = createStationDto();
    assertTrue(stationDto.equals(expectedStationDto));
  }

}