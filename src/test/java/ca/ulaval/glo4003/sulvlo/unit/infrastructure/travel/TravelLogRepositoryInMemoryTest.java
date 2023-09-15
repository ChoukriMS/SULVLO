package ca.ulaval.glo4003.sulvlo.unit.infrastructure.travel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.ulaval.glo4003.sulvlo.domain.travel.Travel;
import ca.ulaval.glo4003.sulvlo.domain.travel.TravelRepository;
import ca.ulaval.glo4003.sulvlo.domain.travel.Travels;
import ca.ulaval.glo4003.sulvlo.infrastructure.travel.TravelLogRepositoryInMemory;
import ca.ulaval.glo4003.sulvlo.infrastructure.travel.exception.InvalidTravelIdException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TravelLogRepositoryInMemoryTest {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(
      "yyyy-MM-dd HH:mm:ss");
  private static final UUID TRAVEL_ID = UUID.randomUUID();
  private static final Month OCTOBER = Month.OCTOBER;
  private static final String ERROR_MESSAGE = "There is no travel with that ID, please try again!";
  private static final String STRAT_TIME_TRAVEL = "2022-01-10 08:00:00";
  private static final String END_TIME_TRAVEL = "2022-01-10 08:40:00";
  private static final String STATION_CODE_END = "TEST2";
  private static final String SATION_CODE_BEGIN = "TEST777";
  private static final String USER_IDUL = "TESTT1";
  private static TravelRepository travelLogRepositoryInMemory;
  private Travel travel;

  @BeforeEach
  public void setUp() throws ParseException {
    travel = new Travel(TRAVEL_ID, USER_IDUL, SATION_CODE_BEGIN,
        LocalDateTime.parse(STRAT_TIME_TRAVEL, FORMATTER),
        LocalDateTime.parse(END_TIME_TRAVEL, FORMATTER),
        STATION_CODE_END, OCTOBER);

    travelLogRepositoryInMemory = new TravelLogRepositoryInMemory();
  }

  @Test
  void givenATravel_WhenTryingToAddToDataBase_ThenShouldSaveTravelInMemory() {
    int beforeSave = travelLogRepositoryInMemory.getListSize();

    travelLogRepositoryInMemory.save(travel);
    int sizeAfterSave = travelLogRepositoryInMemory.getListSize();

    assertEquals(beforeSave + 1, sizeAfterSave);
  }

  @Test
  void givenAUserId_whenTryingToRetrieveEveryTravelDone_ShouldReturnListOfTravels() {
    travelLogRepositoryInMemory.save(travel);

    Travels travels = travelLogRepositoryInMemory.getAll(USER_IDUL);

    assertTrue(travels.getAllFromUser().contains(travel));
  }

  @Test
  void givenAUUID_WhenTryingToLocaleTravelInMemory_ThenShouldReturnTravel()
      throws InvalidTravelIdException {
    UUID getTravelId = travel.travelId();
    travelLogRepositoryInMemory.save(travel);

    Travel getTravel = travelLogRepositoryInMemory.getById(getTravelId);

    assertEquals(travel, getTravel);
  }

  @Test
  void givenAWrongUUID_WhenTryingToLocateTravelInMemory_ThenShouldThrowException() {
    UUID id = UUID.randomUUID();
    travelLogRepositoryInMemory.save(travel);

    assertThrows(InvalidTravelIdException.class,
        () -> travelLogRepositoryInMemory.getById(id)
    );
  }
}
