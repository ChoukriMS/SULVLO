package ca.ulaval.glo4003.sulvlo.unit.domain.travel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.ulaval.glo4003.sulvlo.domain.travel.Travel;
import ca.ulaval.glo4003.sulvlo.domain.travel.Travels;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TravelsTest {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
      "yyyy-MM-dd HH:mm:ss");
  private static final UUID TRAVEL_ID = UUID.randomUUID();
  private static final String USER_ID = "1337";
  private static final String STATION_TRAVEL_CODE = "VAN";
  private static final String STATION_TRAVEL_CODE2 = "VAC";
  private static final String STRAT_TIME = "2022-01-01 05:30:00";
  private static final String END_TIME = "2022-01-01 06:00:00";
  private static final Month MONTH_OF_TRAVEL = Month.OCTOBER;
  private static final int EXPECTED_AMOUNT_OF_TIME = 5400000;
  private static final String EXPECTED_FORMET_AMOUNT_OF_TIME = "90:00";
  private static final int EXPECTED_STATION_LIST = 6;
  private Travel firstTravel;
  private Travel travel2;
  private Travel travel3;
  private Travels travels;
  private List<Travel> travelList;

  @BeforeEach
  public void setUp() {
    generateTravels();
  }

  private void generateTravels() {
    travelList = new ArrayList<>();

    firstTravel = new Travel(TRAVEL_ID, USER_ID, STATION_TRAVEL_CODE,
        LocalDateTime.parse(STRAT_TIME, formatter),
        LocalDateTime.parse(END_TIME, formatter),
        STATION_TRAVEL_CODE, MONTH_OF_TRAVEL);
    travel2 = new Travel(TRAVEL_ID, USER_ID, STATION_TRAVEL_CODE,
        LocalDateTime.parse(STRAT_TIME, formatter),
        LocalDateTime.parse(END_TIME, formatter),
        STATION_TRAVEL_CODE2, MONTH_OF_TRAVEL);
    travel3 = new Travel(TRAVEL_ID, USER_ID, STATION_TRAVEL_CODE,
        LocalDateTime.parse(STRAT_TIME, formatter),
        LocalDateTime.parse(END_TIME, formatter),
        STATION_TRAVEL_CODE2, MONTH_OF_TRAVEL);
    travelList.add(firstTravel);
    travelList.add(travel2);
    travelList.add(travel3);

    travels = new Travels(USER_ID, travelList);
  }

  @Test
  void givenAMonth_whenGettingAllTravelInMonthByUser_thenListOfTravelIsReturn() {
    List<Travel> listOfTravel = travels.filterByMonth(MONTH_OF_TRAVEL);

    assertTrue(listOfTravel.contains(travel2));
    assertTrue(listOfTravel.contains(travel3));
    assertTrue(listOfTravel.contains(firstTravel));
  }

  @Test
  void givenAListOfTravel_whenGettingTotalTimeOfTravel_thenReturnAmountOfTime() {
    long amountOfTime = travels.getTotalTime(travelList);

    assertEquals(EXPECTED_AMOUNT_OF_TIME, amountOfTime);
  }

  @Test
  void givenANumberOfMilliseconds_whenConvertingToSeconds_thenReturnTimeInString() {
    String returnFormat = travels.millisecondsToTime(EXPECTED_AMOUNT_OF_TIME);

    assertEquals(EXPECTED_FORMET_AMOUNT_OF_TIME, returnFormat);
  }

  @Test
  void givenAListOfTravel_whenCountingAllNumberOfStationVisited_thenReturnArrayOfStationCode() {
    List<String> listStationVisited = travels.getAllStationsVisited(travelList);

    assertEquals(EXPECTED_STATION_LIST, listStationVisited.size());
  }

  @Test
  void givenAListOfTravel_whenGettingFavoriteStation_thenReturnFavoriteStation() {
    String favoriteStation = travels.getFavoriteStation(travelList);

    assertEquals(STATION_TRAVEL_CODE, favoriteStation);
  }

}
