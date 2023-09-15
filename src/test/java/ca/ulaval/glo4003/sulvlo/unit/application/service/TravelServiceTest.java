package ca.ulaval.glo4003.sulvlo.unit.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.sulvlo.application.service.travel.TravelService;
import ca.ulaval.glo4003.sulvlo.application.service.travel.assembler.TravelAssembler;
import ca.ulaval.glo4003.sulvlo.application.service.travel.assembler.TravelHistoryAssembler;
import ca.ulaval.glo4003.sulvlo.application.service.travel.dto.TravelDto;
import ca.ulaval.glo4003.sulvlo.application.service.travel.dto.TravelHistorySummaryDTO;
import ca.ulaval.glo4003.sulvlo.domain.travel.Travel;
import ca.ulaval.glo4003.sulvlo.domain.travel.TravelRepository;
import ca.ulaval.glo4003.sulvlo.domain.travel.Travels;
import ca.ulaval.glo4003.sulvlo.domain.travel.history.TravelHistorySummary;
import ca.ulaval.glo4003.sulvlo.infrastructure.travel.exception.InvalidTravelIdException;
import jakarta.ws.rs.BadRequestException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TravelServiceTest {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
      "yyyy-MM-dd HH:mm:ss");
  private final static String STATION_CODE_BEGIN = "PEP";
  private final static String USER_ID = "1";
  private final static String STATION_CODE_END = "VAN";
  private final static Month MONTH_OF_TRAVEL = Month.OCTOBER;
  private static final String INVALID_MONTH = "this is a test";
  private final static String STARTING_TRAVEL_TIME = "2022-01-10 08:00:00";
  private final static String END_TRAVEL_TIME = "2022-01-10 08:40:00";
  private final static String MONTH_OF_TRAVEL_IN_STRING = "october";
  private static final UUID TRAVEL_ID = UUID.randomUUID();
  private static final int NUMBER_OF_TRAVELS = 2;
  private static final String TOTAL_TRAVELS_TIME = "30";
  private Travel travel;
  @Mock
  private TravelRepository travelRepository;
  @Mock
  private TravelAssembler travelAssembler;
  @Mock
  private Travels travels;
  private TravelDto travelDto;
  private TravelService travelService;
  private TravelHistoryAssembler travelHistoryAssembler;
  private List<Travel> listOfTravels;


  @BeforeEach
  public void setUp() {
    travel = new Travel(TRAVEL_ID, USER_ID, STATION_CODE_BEGIN,
        LocalDateTime.parse(STARTING_TRAVEL_TIME, formatter),
        LocalDateTime.parse(END_TRAVEL_TIME, formatter),
        STATION_CODE_END, MONTH_OF_TRAVEL);
    travelDto = new TravelDto(travel.travelId().toString(), travel.userIdul(),
        travel.stationStartTravel(), travel.startTravelDate().toString(),
        travel.endTravelDate().toString(), travel.stationEndTravel(), travel.month());
    listOfTravels = new ArrayList<>();
    listOfTravels.add(travel);

    travelHistoryAssembler = new TravelHistoryAssembler();
    travelService = new TravelService(travelRepository, travelAssembler, travelHistoryAssembler);
  }

  @Test
  void givenAUserIdAndMonth_whenGetAllTravelFromRepository_thenShouldReturnArrayOfTravelsFilterByMonth() {
    when(travelRepository.getAll(USER_ID)).thenReturn(travels);
    when(travels.filterByMonth(MONTH_OF_TRAVEL)).thenReturn(listOfTravels);
    when(travelAssembler.create(travel)).thenReturn(travelDto);

    List<TravelDto> travelDtoList = travelService.getAllFilteredByMonth(
        MONTH_OF_TRAVEL_IN_STRING, USER_ID);

    assertEquals(MONTH_OF_TRAVEL, travelDtoList.get(0).month());
  }

  @Test
  void givenAStringID_WhenTryingToLocateTheTravel_thenShouldReturnTravel() throws
      InvalidTravelIdException {
    String id = travelDto.Id();

    when(travelRepository.getById(TRAVEL_ID)).thenReturn(travel);
    when(travelAssembler.create(travel)).thenReturn(travelDto);

    TravelDto serviceReturn = travelService.getById(id);

    assertNotNull(serviceReturn);
  }

  @Test
  void whenGetAllTravelsHistory_thenShouldReturnATravelHistoryDTO() {
    TravelHistorySummary travelHistorySummary = new TravelHistorySummary(TOTAL_TRAVELS_TIME,
        TOTAL_TRAVELS_TIME,
        NUMBER_OF_TRAVELS, STATION_CODE_BEGIN);
    when(travelRepository.getAll(USER_ID)).thenReturn(travels);
    when(travels.getHistorySummary(USER_ID)).thenReturn(travelHistorySummary);

    TravelHistorySummaryDTO returnService = travelService.getHistorySummary(USER_ID);

    assertNotNull(returnService);
  }

  @Test
  void givenAInvalidMonth_whenGetAllTravelsByMonth_ThenShouldThrowException() {
    BadRequestException thrown = assertThrows(
        BadRequestException.class, () ->
            travelService.getAllFilteredByMonth(INVALID_MONTH, USER_ID));

    assertNotNull(thrown);
  }
}
