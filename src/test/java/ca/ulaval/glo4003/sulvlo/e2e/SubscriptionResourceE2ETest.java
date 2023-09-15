package ca.ulaval.glo4003.sulvlo.e2e;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.SulvloMain;
import ca.ulaval.glo4003.sulvlo.e2e.helpers.SubscriptionResourceE2ETestHelper;
import ca.ulaval.glo4003.sulvlo.e2e.helpers.UserResourceE2ETestHelper;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;

class SubscriptionResourceE2ETest {

  private static final int DEFAULT_SUBSCRIPTION_TYPES_SIZE = 3;
  private static final String PREMIUM = "Premium";
  private static final String BASE = "Base";
  private static final String STUDENT = "Student";
  private final SubscriptionResourceE2ETestHelper subscriptionResourceE2ETestHelper;
  private final UserResourceE2ETestHelper userResourceE2ETestHelper;
  private SulvloMain server;
  private Response response;


  public SubscriptionResourceE2ETest() {
    this.subscriptionResourceE2ETestHelper = new SubscriptionResourceE2ETestHelper();
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
  void whenFetchingForSubscriptions_thenReturnsExpectedStatusCode() {
    response = subscriptionResourceE2ETestHelper.getAllSubscriptions();

    assertEquals(response.statusCode(), Status.OK.getStatusCode());
  }

  @Test
  void whenFetchingForSubscriptions_thenReturnsExpectedDefaultSubscriptions() {
    response = subscriptionResourceE2ETestHelper.getAllSubscriptions();

    List<Map<String, Object>> listOfSubscriptions = response.body().as(new TypeRef<>() {
    });
    var listOfTypes = subscriptionResourceE2ETestHelper.convertSubscriptionsToTypes(
        listOfSubscriptions);

    assertThat(listOfTypes).contains(PREMIUM);
    assertThat(listOfTypes).contains(BASE);
    assertThat(listOfTypes).contains(STUDENT);
    assertThat(listOfSubscriptions.size()).isEqualTo(DEFAULT_SUBSCRIPTION_TYPES_SIZE);
  }
}
