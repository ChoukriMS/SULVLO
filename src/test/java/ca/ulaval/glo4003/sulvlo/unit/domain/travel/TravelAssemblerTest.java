package ca.ulaval.glo4003.sulvlo.unit.domain.travel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.ulaval.glo4003.sulvlo.application.service.travel.assembler.TravelAssembler;
import ca.ulaval.glo4003.sulvlo.application.service.travel.dto.TravelDto;
import ca.ulaval.glo4003.sulvlo.domain.travel.Travel;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TravelAssemblerTest {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(
      "yyyy-MM-dd HH:mm:ss");
  private final String STRAT_TIME_TRAVEL = "2022-01-10 08:00:00";
  private final String END_TIME_TRAVEL = "2022-01-10 08:40:00";
  private final String STATION_CODE_END = "TEST2";
  private final String STATION_CODE_BEGIN = "TEST777";
  private final String USER_ID = "1";
  private Travel travel;
  private TravelAssembler travelAssembler;

  @BeforeEach
  public void setUp() {
    travel = new Travel(UUID.randomUUID(), USER_ID, STATION_CODE_BEGIN,
        LocalDateTime.parse(STRAT_TIME_TRAVEL, FORMATTER),
        LocalDateTime.parse(END_TIME_TRAVEL, FORMATTER),
        STATION_CODE_END, Month.SEPTEMBER);

    travelAssembler = new TravelAssembler();
  }

  @Test
  void givenATravel_WhenAssemblingTravel_ThenShouldReturnATravelDTO() {
    assertEquals(TravelDto.class, travelAssembler.create(travel).getClass());
    assertNotNull(travelAssembler.create(travel));
  }
}
