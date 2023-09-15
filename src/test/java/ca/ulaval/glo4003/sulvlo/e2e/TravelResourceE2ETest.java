package ca.ulaval.glo4003.sulvlo.e2e;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.SulvloMain;
import ca.ulaval.glo4003.sulvlo.e2e.helpers.BaseE2ETestHelper;
import ca.ulaval.glo4003.sulvlo.e2e.helpers.TravelResourceE2ETestHelper;
import ca.ulaval.glo4003.sulvlo.e2e.helpers.UserResourceE2ETestHelper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.LinkedHashMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;

class TravelResourceE2ETest {

  private static final String TRAVELER_MONTH_CHOSEN = "APRIL";
  private static final String INVALID_TRAVEL_ID = "TROLOLOL";
  private static final String INVALID_TRAVEL_ID_REQUEST_PARAMETER_MSG = "There is no travel with that ID, please try again!";
  private static final String ERROR = "error";
  private static final String MESSAGE = "message";
  private final UserResourceE2ETestHelper userResourceE2ETestHelper;
  private final TravelResourceE2ETestHelper travelResourceE2ETestHelper;
  private SulvloMain server;
  private Response response;
  private String jwtToken;

  public TravelResourceE2ETest() {
    this.userResourceE2ETestHelper = new UserResourceE2ETestHelper();
    this.travelResourceE2ETestHelper = new TravelResourceE2ETestHelper();
  }

  @BeforeEach
  public void setUp() throws SchedulerException {
    RestAssured.port = 8080;
    server = new SulvloMain();
    server.run();
    response = userResourceE2ETestHelper.registerUser();

    jwtToken = userResourceE2ETestHelper.loginUser(BaseE2ETestHelper.USER_EMAIL,
            BaseE2ETestHelper.USER_VALID_PWD).getBody()
        .jsonPath().getString(BaseE2ETestHelper.LOGIN_USER_BODY_RESPONSE_PARAMETERS[0]);

  }

  @AfterEach
  public void tearDown() {
    server.stopServer();
  }

  @Test
  void givenValidParameters_whenGettingTravelsByMonth_thenReturnsExpectedStatusCode() {
    response = travelResourceE2ETestHelper.getAllTravelsByMonth(TRAVELER_MONTH_CHOSEN, jwtToken);

    assertEquals(Status.OK.getStatusCode(), response.statusCode());
  }

  @Test
  void givenInexistingTravelId_whenGettingATravelById_thenReturnsExpectedStatusCode() {
    response = travelResourceE2ETestHelper.getAllTravelsById(INVALID_TRAVEL_ID, jwtToken);

    assertEquals(Status.BAD_REQUEST.getStatusCode(), response.statusCode());
  }

  @Test
  void givenInexistingTravelId_whenGettingATravelById_thenReturnsExpectedErrorMsg() {
    response = travelResourceE2ETestHelper.getAllTravelsById(INVALID_TRAVEL_ID, jwtToken);
    LinkedHashMap<String, String> map = response.getBody().jsonPath()
        .get(ERROR);
    String errorMsg = map.get(MESSAGE);

    assertEquals(INVALID_TRAVEL_ID_REQUEST_PARAMETER_MSG, errorMsg);
  }

  @Test
  void givenValidUserId_whenGettingTravelHistorySummary_thenReturnsExpectedStatusCode() {
    response = travelResourceE2ETestHelper.getTravelHistorySummary(jwtToken);

    assertEquals(Status.OK.getStatusCode(), response.statusCode());
  }
}
