package ca.ulaval.glo4003.sulvlo.e2e;

import static ca.ulaval.glo4003.sulvlo.e2e.helpers.BaseE2ETestHelper.EMAIL;
import static ca.ulaval.glo4003.sulvlo.e2e.helpers.BaseE2ETestHelper.EXPIRE_IN;
import static ca.ulaval.glo4003.sulvlo.e2e.helpers.BaseE2ETestHelper.NAME;
import static ca.ulaval.glo4003.sulvlo.e2e.helpers.BaseE2ETestHelper.TOKEN;
import static ca.ulaval.glo4003.sulvlo.e2e.helpers.BaseE2ETestHelper.USER_EMAIL;
import static ca.ulaval.glo4003.sulvlo.e2e.helpers.BaseE2ETestHelper.USER_VALID_PWD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.SulvloMain;
import ca.ulaval.glo4003.sulvlo.auth.jwt.exceptions.InvalidAuthenticationTokenException;
import ca.ulaval.glo4003.sulvlo.e2e.helpers.BaseE2ETestHelper;
import ca.ulaval.glo4003.sulvlo.e2e.helpers.UserResourceE2ETestHelper;
import io.jsonwebtoken.Claims;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import jakarta.ws.rs.core.Response.Status;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;

class UserResourceE2ETest {

  private static final String ADMIN_USER_NAME = "BIG DEVS";
  private static final String ADMIN_USER_EMAIL = "BIGDEVS@nospam.today";
  private static final String USER_INVALID_PWD = "xXuser777Xx";
  private static final String ADMIN_USER_PWD = "test123";
  private static final String INVALID_JWT_TOKEN = "trolololo";
  private static final String DEFAULT_USER_ROLE = "NORMAL";
  private static final String ADMIN_USER_ROLE = "DEVELOPER";
  private static final int JWT_VALID_FOR_TIME = 3600;
  private static final String ROLE = "role";
  private final UserResourceE2ETestHelper userResourceE2ETestHelper;
  private SulvloMain server;
  private Response response;

  public UserResourceE2ETest() {
    this.userResourceE2ETestHelper = new UserResourceE2ETestHelper();
  }

  @BeforeEach
  public void setUp() throws SchedulerException {
    RestAssured.port = 8080;
    server = new SulvloMain();
    server.run();

    response = userResourceE2ETestHelper.registerUser();
  }

  @AfterEach
  public void tearDown() {
    server.stopServer();
  }

  @Test
  void givenInvalidUserFormData_whenLoggingIn_thenReturnsExpectedStatusCode() {

    response = userResourceE2ETestHelper.loginUser(USER_EMAIL,
        USER_INVALID_PWD);

    assertEquals(response.statusCode(), Status.BAD_REQUEST.getStatusCode());
  }

  @Test
  void givenValidUserFormData_whenLoggingIn_thenReturnsJWTToken() {

    response = userResourceE2ETestHelper.loginUser(USER_EMAIL,
        USER_VALID_PWD);
    String token = getResponseParameter(response, TOKEN);

    assertNotNull(token);
    assertEquals(response.statusCode(), Status.OK.getStatusCode());
  }

  @Test
  void givenNormalUser_whenLoggingIn_thenReturnsExpectedValues() {
    response = userResourceE2ETestHelper.loginUser(USER_EMAIL,
        USER_VALID_PWD);
    String token = getResponseParameter(response, TOKEN);
    Claims claims = userResourceE2ETestHelper.getTokenDetails(token);

    assertEquals(BaseE2ETestHelper.USER_NAME, claims.get(NAME));
    assertEquals(USER_EMAIL, claims.get(EMAIL));
    assertEquals(DEFAULT_USER_ROLE, claims.get(ROLE));
    assertEquals(response.statusCode(), Status.OK.getStatusCode());
  }

  @Test
  void givenAdminUser_whenLoggingIn_thenReturnsExpectedValues() {
    response = userResourceE2ETestHelper.loginUser(ADMIN_USER_EMAIL, ADMIN_USER_PWD);
    String token = getResponseParameter(response, TOKEN);
    Claims claims = userResourceE2ETestHelper.getTokenDetails(token);

    assertEquals(ADMIN_USER_NAME, claims.get(NAME));
    assertEquals(ADMIN_USER_EMAIL, claims.get(EMAIL));
    assertEquals(ADMIN_USER_ROLE, claims.get(ROLE));
    assertEquals(response.statusCode(), Status.OK.getStatusCode());
  }

  @Test
  void givenValidUserFormData_whenLoggingIn_thenReturnsExpectedExpirationDate() {
    response = userResourceE2ETestHelper.loginUser(USER_EMAIL,
        USER_VALID_PWD);
    LocalDateTime expirationDate = LocalDateTime.parse(
            getResponseParameter(response, EXPIRE_IN))
        .truncatedTo(ChronoUnit.MINUTES);
    LocalDateTime expectedExpirationDate = LocalDateTime.now().plusSeconds(JWT_VALID_FOR_TIME)
        .truncatedTo(ChronoUnit.MINUTES);

    assertEquals(expirationDate, expectedExpirationDate);
    assertEquals(response.statusCode(), Status.OK.getStatusCode());
  }

  @Test
  void givenValidUserFormData_whenLoggingIn_thenThrowsExceptionIfInvalidJWT() {
    response = userResourceE2ETestHelper.loginUser(USER_EMAIL,
        USER_VALID_PWD);

    assertThrows(InvalidAuthenticationTokenException.class,
        () -> userResourceE2ETestHelper.getTokenDetails(INVALID_JWT_TOKEN));
  }

  @Test
  void givenUserWithValidPermissions_whenAccessingProtectedRequest_thenReturnsExpectedStatusCode() {
    String token = getResponseParameter(
        userResourceE2ETestHelper.loginUser(ADMIN_USER_EMAIL, ADMIN_USER_PWD),
        TOKEN);

    response = userResourceE2ETestHelper.getProtectedRequest(token);

    assertEquals(response.statusCode(), Status.ACCEPTED.getStatusCode());
  }

  @Test
  void givenUserWithInsufficientPermissions_whenAccessingProtectedRequest_thenReturnsExpectedStatusCode() {
    String token = getResponseParameter(userResourceE2ETestHelper.loginUser(USER_EMAIL,
        USER_VALID_PWD), TOKEN);

    response = userResourceE2ETestHelper.getProtectedRequest(token);

    assertEquals(response.statusCode(), Status.FORBIDDEN.getStatusCode());
  }

  private String getResponseParameter(Response userResourceE2ETestHelper, String token) {
    return userResourceE2ETestHelper.getBody()
        .jsonPath()
        .getString(token);
  }

}
