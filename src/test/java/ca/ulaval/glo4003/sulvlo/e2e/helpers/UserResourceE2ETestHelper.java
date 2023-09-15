package ca.ulaval.glo4003.sulvlo.e2e.helpers;

import ca.ulaval.glo4003.sulvlo.auth.jwt.TokenAuthentificationService;
import io.jsonwebtoken.Claims;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;

public class UserResourceE2ETestHelper extends BaseE2ETestHelper {

  private static final String ACTIVATION_PATH = "/activation";
  private static final String SEND_UNIQUE_CODE_PATH = "/unique-code";
  private static final String PROTECTED_REQUEST_PATH = "/protected";
  private static final String SEND_UNIQUE_CODE_BODY_PARAMETERS = "email";
  private static final String ACTIVATION_TOKEN = "activation-token";
  private JSONObject jsonObject;


  public Claims getTokenDetails(String token) {
    TokenAuthentificationService tokenAuthentificationService = new TokenAuthentificationService();
    return tokenAuthentificationService.getClaims(token);
  }

  public Response getProtectedRequest(String token) {
    return RestAssured.given()
        .contentType(CONTENT_TYPE)
        .header(PROTECTED_REQUEST_HEADER_PARAMETER, PROTECTED_REQUEST_HEADER + token)
        .when()
        .get(BASE_PATH + PROTECTED_REQUEST_PATH)
        .then()
        .extract()
        .response();
  }

  public Response sendUniqueCode(String token) {
    jsonObject = new JSONObject()
        .put(SEND_UNIQUE_CODE_BODY_PARAMETERS, USER_EMAIL);

    return RestAssured.given()
        .contentType(CONTENT_TYPE)
        .header(PROTECTED_REQUEST_HEADER_PARAMETER, PROTECTED_REQUEST_HEADER + token)
        .body(jsonObject.toString())
        .when()
        .post(BASE_PATH + SEND_UNIQUE_CODE_PATH)
        .then()
        .extract()
        .response();
  }

  public Response activateAccount(String activationToken, String token) {
    jsonObject = new JSONObject()
        .put(EMAIL, USER_EMAIL);

    return RestAssured.given()
        .contentType(CONTENT_TYPE)
        .header(ACTIVATION_TOKEN, activationToken)
        .header(PROTECTED_REQUEST_HEADER_PARAMETER, PROTECTED_REQUEST_HEADER + token)
        .body(jsonObject.toString())
        .when()
        .post(BASE_PATH + ACTIVATION_PATH)
        .then()
        .extract()
        .response();
  }
}
