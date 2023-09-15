package ca.ulaval.glo4003.sulvlo.unit.domain.travel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.ulaval.glo4003.sulvlo.domain.travel.Travel;
import ca.ulaval.glo4003.sulvlo.domain.travel.TravelFactory;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

class TravelFactoryTest {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(
      "yyyy-MM-dd HH:mm:ss");
  private static final Month OCTOBER = Month.OCTOBER;
  private static final String START_TIME_TRAVEL = "2022-01-10 08:00:00";
  private static final String END_TIME_TRAVEL = "2022-01-10 08:40:00";
  private static final String STATION_CODE_END = "TEST2";
  private static final String STATION_CODE_BEGIN = "TEST777";
  private static final String USER_IDUL = "TESTT1";

  @Test
  void givenRequirementsToCreateTravel_whenCreatingTravel_thenTravelObjectIsCreated() {
    TravelFactory travelFactory = new TravelFactory();

    Travel travel = travelFactory.create(USER_IDUL, STATION_CODE_BEGIN, STATION_CODE_END,
        LocalDateTime.parse(START_TIME_TRAVEL, FORMATTER),
        LocalDateTime.parse(END_TIME_TRAVEL, FORMATTER), OCTOBER);

    assertNotNull(travel);
    assertEquals(OCTOBER, travel.month());
  }
}
