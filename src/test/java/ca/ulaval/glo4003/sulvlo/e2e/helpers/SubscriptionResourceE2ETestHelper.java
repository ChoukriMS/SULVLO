package ca.ulaval.glo4003.sulvlo.e2e.helpers;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.json.JSONObject;

public class SubscriptionResourceE2ETestHelper extends BaseE2ETestHelper {

  private static final String SUBSCRIPTION_TYPE = "subscriptionType";
  private static final String SEMESTER = "semester";
  private static final String CREDIT_CARD_NUMBER = "creditCardNumber";
  private static final String EXPIRATION_MONTH = "expirationMonth";
  private static final String EXPIRATION_YEAR = "expirationYear";
  private static final String CCV = "ccv";

  private static final String AUTO_PAYMENT_AFTER_TRAVEL = "automaticPaymentAfterTravel";
  private static final String AUTO_PAYMENT_END_MONTH = "automaticPaymentEndMonth";

  private static final String SUBSCRIPTION_PATH = "api/subscriptions";
  private static final String SUBSCRIPTION_SEMESTER = "H2023";
  private static final String USER_CREDIT_CARD = "1234567891234567";
  private static final int USER_CREDIT_CARD_EXPIRATION_MONTH = 12;
  private static final int USER_CREDIT_CARD_EXPIRATION_YEAR = 2045;
  private static final int USER_CREDIT_CARD_CCV = 123;
  private static final boolean PAYMENT_AFTER_TRAVEL_ENABLE = true;
  private static final boolean PAYMENT_END_MONTH_ENABLE = true;
  private static final String SUBSCRIPTION_TYPE_PARAMETER = "type";

  public Response addSubscription(String subscriptionType, String token) {
    JSONObject jsonObject = new JSONObject()
        .put(SUBSCRIPTION_TYPE, subscriptionType)
        .put(SEMESTER, SUBSCRIPTION_SEMESTER)
        .put(IDUL, USER_IDUL)
        .put(CREDIT_CARD_NUMBER, USER_CREDIT_CARD)
        .put(EXPIRATION_MONTH, USER_CREDIT_CARD_EXPIRATION_MONTH)
        .put(EXPIRATION_YEAR, USER_CREDIT_CARD_EXPIRATION_YEAR)
        .put(CCV, USER_CREDIT_CARD_CCV)
        .put(AUTO_PAYMENT_END_MONTH, PAYMENT_END_MONTH_ENABLE)
        .put(AUTO_PAYMENT_AFTER_TRAVEL, PAYMENT_AFTER_TRAVEL_ENABLE);

    return RestAssured.given()
        .contentType(CONTENT_TYPE)
        .body(jsonObject.toString())
        .header(PROTECTED_REQUEST_HEADER_PARAMETER, PROTECTED_REQUEST_HEADER + token)
        .when()
        .post(SUBSCRIPTION_PATH)
        .then()
        .extract()
        .response();
  }

  public Response getAllSubscriptions() {
    return RestAssured.given()
        .contentType(CONTENT_TYPE)
        .when()
        .get(SUBSCRIPTION_PATH)
        .then()
        .extract()
        .response();
  }

  public List convertSubscriptionsToTypes(List<Map<String, Object>> listOfSubscriptions) {
    return listOfSubscriptions
        .stream()
        .map(m -> new ArrayList<>(Stream.of(SUBSCRIPTION_TYPE_PARAMETER)
            .filter(m::containsKey)
            .collect(Collectors.toMap(key -> key, m::get))
            .values()))
        .toList()
        .stream()
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }

}
