package ca.ulaval.glo4003.sulvlo.e2e.helpers;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TruckResourceE2ETestHelper extends BaseE2ETestHelper {

  private static final String TRUCK_PATH = "api/trucks";
  private static final String USER_IDUL_HEADER_PARAMETER = "user-idul";

  public Response listTruck(String userIdul, String token) {
    return RestAssured.given()
        .contentType(CONTENT_TYPE)
        .header(PROTECTED_REQUEST_HEADER_PARAMETER, PROTECTED_REQUEST_HEADER + token)
        .header(USER_IDUL_HEADER_PARAMETER, userIdul).log().all()
        .when()
        .get(TRUCK_PATH)
        .then()
        .extract()
        .response();
  }

  public Response loadBikes(String userIdul, String token) {
    return RestAssured.given()
        .contentType(CONTENT_TYPE)
        .header(PROTECTED_REQUEST_HEADER_PARAMETER, PROTECTED_REQUEST_HEADER + token)
        .header(USER_IDUL_HEADER_PARAMETER, userIdul)
        .when()
        .get(TRUCK_PATH)
        .then()
        .extract()
        .response();
  }

  public Response unloadBikes(String userIdul, String token) {
    return RestAssured.given()
        .contentType(CONTENT_TYPE)
        .header(PROTECTED_REQUEST_HEADER_PARAMETER, PROTECTED_REQUEST_HEADER + token)
        .header(USER_IDUL_HEADER_PARAMETER, userIdul)
        .when()
        .get(TRUCK_PATH)
        .then()
        .extract()
        .response();
  }

}
