package ca.ulaval.glo4003.sulvlo.unit.domain.travel;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.ulaval.glo4003.sulvlo.application.service.travel.assembler.TravelHistoryAssembler;
import ca.ulaval.glo4003.sulvlo.domain.travel.history.TravelHistorySummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TravelHistoryAssemblerTest {

  private final String STATION_CODE = "TEST2";
  private final String AMOUNT_OF_TIME_TRAVEL = "30";
  private final String AVERAGE_TIME_OF_TRAVEL = "45";
  private final int NUMBER_OF_TRAVEL = 2;
  private TravelHistorySummary travelHistorySummary;
  private TravelHistoryAssembler travelHistoryAssembler;

  @BeforeEach
  void setUp() {
    travelHistorySummary = new TravelHistorySummary(AMOUNT_OF_TIME_TRAVEL, AVERAGE_TIME_OF_TRAVEL,
        NUMBER_OF_TRAVEL, STATION_CODE);
    travelHistoryAssembler = new TravelHistoryAssembler();
  }

  @Test
  void whenCreatingTravelHistoryDTO_ThenShouldReturnNewTravelHistoryDTO() {
    assertNotNull(travelHistoryAssembler.create(travelHistorySummary));
  }

}


