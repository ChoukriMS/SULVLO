package ca.ulaval.glo4003.sulvlo.e2e;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.SulvloMain;
import ca.ulaval.glo4003.sulvlo.e2e.helpers.BaseE2ETestHelper;
import ca.ulaval.glo4003.sulvlo.e2e.helpers.TruckResourceE2ETestHelper;
import ca.ulaval.glo4003.sulvlo.e2e.helpers.UserResourceE2ETestHelper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;

public class TruckResourceE2ETest {

  public static final String TECHNICIAN_EMAIL = "technicien@nospam.today";
  public static final String TECHNICIAN_PWD = "test123";
  private static final String TECHNICIEN_IDDUL = "SYGAU1";
  private static final String INVALID_TECHNICIEN_IDDUL = "INVALID";
  private static final String INVALID_TECHNICIEN_TOKEN = "0123456789";
  private final UserResourceE2ETestHelper userResourceE2ETestHelper;
  private final TruckResourceE2ETestHelper truckResourceE2ETestHelper;
  private String jwtToken;
  private SulvloMain server;
  private Response response;

  public TruckResourceE2ETest() {
    this.userResourceE2ETestHelper = new UserResourceE2ETestHelper();
    this.truckResourceE2ETestHelper = new TruckResourceE2ETestHelper();
  }

  @BeforeEach
  public void setUp() throws SchedulerException {
    RestAssured.port = 8080;
    server = new SulvloMain();
    server.run();

    jwtToken = userResourceE2ETestHelper.loginUser(TECHNICIAN_EMAIL,
            TECHNICIAN_PWD).getBody()
        .jsonPath().getString(BaseE2ETestHelper.LOGIN_USER_BODY_RESPONSE_PARAMETERS[0]);
  }

  @AfterEach
  public void tearDown() {
    server.stopServer();
  }

  @Test
  void givenValidTechnicianData_whenListTruck_thenReturnsExpectedStatusCode() {
    response = truckResourceE2ETestHelper.listTruck(TECHNICIEN_IDDUL, jwtToken);

    assertEquals(response.statusCode(), jakarta.ws.rs.core.Response.Status.OK.getStatusCode());
  }

  @Test
  void givenValidTechnicianData_whenLoadBikes_thenReturnsExpectedStatusCode() {
    response = truckResourceE2ETestHelper.loadBikes(TECHNICIEN_IDDUL, jwtToken);

    assertEquals(response.statusCode(), jakarta.ws.rs.core.Response.Status.OK.getStatusCode());
  }

  @Test
  void givenValidTechnicianData_whenUnloadBikes_thenReturnsExpectedStatusCode() {
    response = truckResourceE2ETestHelper.unloadBikes(TECHNICIEN_IDDUL, jwtToken);

    assertEquals(response.statusCode(), jakarta.ws.rs.core.Response.Status.OK.getStatusCode());
  }

  @Test
  void givenInvalidTechnicianData_whenListTruck_thenReturnsExpectedStatusCode() {
    response = truckResourceE2ETestHelper.listTruck(INVALID_TECHNICIEN_IDDUL,
        INVALID_TECHNICIEN_TOKEN);

    assertEquals(response.statusCode(),
        jakarta.ws.rs.core.Response.Status.BAD_REQUEST.getStatusCode());
  }

}
