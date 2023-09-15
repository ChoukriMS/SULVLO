package ca.ulaval.glo4003.sulvlo.unit.api.travel;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.sulvlo.api.travel.TravelResource;
import ca.ulaval.glo4003.sulvlo.application.service.travel.TravelService;
import ca.ulaval.glo4003.sulvlo.application.service.travel.dto.TravelDto;
import ca.ulaval.glo4003.sulvlo.infrastructure.travel.exception.InvalidTravelIdException;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TravelResourceTest {

  private static final Month MONTH = Month.OCTOBER;
  private static final String USER_ID = "1";
  private static final String ID = "IDUL1";
  private static final String STATION_START_TRAVEL = "test";
  private static final String STATION_END_TRAVEL = "test2";
  private static final String START_TRAVEL_DATE = "2022-01-10 08:00:00";
  private static final String END_TRAVEL_DATE = "2022-01-10 08:30:00";
  @Mock
  private TravelService travelService;
  private TravelResource travelResource;
  private TravelDto travelDto;
  private List<TravelDto> travelDtoList;


  @BeforeEach
  void setUp() {

    travelResource = new TravelResource(travelService);
    travelDto = new TravelDto(ID, USER_ID, STATION_START_TRAVEL,
        START_TRAVEL_DATE, END_TRAVEL_DATE, STATION_END_TRAVEL, MONTH);
    travelDtoList = new ArrayList<>();
    travelDtoList.add(travelDto);
  }

  @Test
  void givenATravelId_WhenGettingTravel_ThenShouldReturnTravelDto()
      throws InvalidTravelIdException {
    when(travelService.getById(ID)).thenReturn(travelDto);

    assertNotNull(travelResource.getById(ID).getEntity());
  }

}
