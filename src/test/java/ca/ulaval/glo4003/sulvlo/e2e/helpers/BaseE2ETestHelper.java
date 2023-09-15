package ca.ulaval.glo4003.sulvlo.e2e.helpers;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;


public class BaseE2ETestHelper {

  // Ici c'est la seule classe ou on peut avoir des variables public pour eviter la duplication, aucune autre variable public dans les autres classes
  // Si on utilise la variable en question dans deux ou plus differents fichier => on la set public ici, sinon => set private dans sa classe de test

  public static final String CONTENT_TYPE = "application/json";
  public static final String USER_NAME = "Normal User";
  public static final String USER_EMAIL = "normalUser@mail.com";
  public static final String USER_VALID_PWD = "xXuser69Xx";
  public static final int USER_AGE = 69;
  public static final String REGISTER_USER_HEADER_RESPONSE_PARAMETER = "activationToken";
  public static final String PROTECTED_REQUEST_HEADER_PARAMETER = "Authorization";
  public static final String PROTECTED_REQUEST_HEADER = "Bearer ";
  public static final String TOKEN = "token";
  public static final String EXPIRE_IN = "expireIn";
  public static final String[] LOGIN_USER_BODY_RESPONSE_PARAMETERS = {TOKEN, EXPIRE_IN};
  public static final String BASE_PATH = "api/users";
  public static final String NAME = "name";
  public static final String EMAIL = "email";
  public static final String USER_IDUL = "user69";
  public static final String IDUL = "idul";

  private static final String REGISTER_PATH = "/register";
  private static final String LOGIN_PATH = "/login";
  private static final String USER_BIRTH_DATE = "30/12/2011";
  private static final String USER_GENDER = "MALE";
  private static final String AGE = "age";
  private static final String PASSWORD = "password";
  private static final String BIRTH_DATE = "birthDate";
  private static final String GENDER = "gender";
  private JSONObject jsonObject;


  public Response registerUser() {
    jsonObject = new JSONObject()
        .put(NAME, USER_NAME)
        .put(EMAIL, USER_EMAIL)
        .put(IDUL, USER_IDUL)
        .put(AGE, USER_AGE)
        .put(PASSWORD, USER_VALID_PWD)
        .put(BIRTH_DATE, USER_BIRTH_DATE)
        .put(GENDER, USER_GENDER);

    return RestAssured.given()
        .contentType(CONTENT_TYPE)
        .body(jsonObject.toString())
        .when()
        .post(BASE_PATH + REGISTER_PATH)
        .then()
        .extract()
        .response();
  }

  public Response loginUser(String email, String pwd) {
    jsonObject = new JSONObject()
        .put(EMAIL, email)
        .put(PASSWORD, pwd);

    return RestAssured.given()
        .contentType(CONTENT_TYPE)
        .body(jsonObject.toString())
        .when()
        .post(BASE_PATH + LOGIN_PATH)
        .then()
        .extract()
        .response();
  }

}
