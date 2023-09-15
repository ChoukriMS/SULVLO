package ca.ulaval.glo4003.sulvlo.e2e.helpers;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TravelResourceE2ETestHelper extends BaseE2ETestHelper {

  private static final String TRAVEL_PATH = "api/travel";
  private static final String GET_TRAVEL_HISTORY_SUMMARY_PATH = "/summary";
  private static final String MONTH_HEADER_PARAMETER = "month";
  private static final String USER_ID_HEADER_PARAMETER = "user-id";


  public Response getAllTravelsByMonth(String monthChosen, String token) {
    return RestAssured.given()
        .contentType(CONTENT_TYPE)
        .header(PROTECTED_REQUEST_HEADER_PARAMETER, PROTECTED_REQUEST_HEADER + token)
        .header(MONTH_HEADER_PARAMETER, monthChosen)
        .when()
        .get(TRAVEL_PATH)
        .then()
        .extract()
        .response();
  }

  public Response getAllTravelsById(String travelId, String token) {
    return RestAssured.given()
        .contentType(CONTENT_TYPE)
        .header(PROTECTED_REQUEST_HEADER_PARAMETER, PROTECTED_REQUEST_HEADER + token)
        .when()
        .get(TRAVEL_PATH + "/" + travelId)
        .then()
        .extract()
        .response();
  }

  public Response getTravelHistorySummary(String token) {
    return RestAssured.given()
        .contentType(CONTENT_TYPE)
        .header(PROTECTED_REQUEST_HEADER_PARAMETER, PROTECTED_REQUEST_HEADER + token)
        .when()
        .get(TRAVEL_PATH + GET_TRAVEL_HISTORY_SUMMARY_PATH)
        .then()
        .extract()
        .response();
  }

}
