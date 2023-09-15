package ca.ulaval.glo4003.sulvlo.domain.payment.service.schoolFeesService;

import ca.ulaval.glo4003.sulvlo.api.validation.exception.InvalidSubscriptionParameterException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SchoolFeesService {

  private static final String endpoint = "https://seashell-app-ikooo.ondigitalocean.app/ulaval-semester-balance2/";

  public Boolean isFullTime(String idul, String email, String session) throws IOException {
    URL url = new URL(endpoint + "semesters/" + session + ":enrollment");
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("POST");
    con.setRequestProperty("Content-Type", "application/json");
    con.setRequestProperty("Accept", "application/json");
    con.setDoOutput(true);
    String body = "{\"idul\":\"" + idul + "\",\"email\":\"" + email + "\"}";

    try (OutputStream outputStream = con.getOutputStream()) {
      byte[] input = body.getBytes(java.nio.charset.StandardCharsets.UTF_8);
      outputStream.write(input, 0, input.length);
    }
    try (BufferedReader bufferedReader = new BufferedReader(
        new InputStreamReader(con.getInputStream(), "utf-8"))) {
      StringBuilder response = new StringBuilder();
      String responseLine = null;
      while ((responseLine = bufferedReader.readLine()) != null) {
        response.append(responseLine.trim());
      }
      ObjectMapper mapper = new ObjectMapper();
      Enrollment enrollment = mapper.readValue(response.toString(), Enrollment.class);
      return enrollment.fullTime;
    }
  }

  public void addStudentPassToSchoolFees(String idul, int fee, String session) {
    try {

      URL url = new URL(endpoint + "semesters/" + session + ":fee");
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("POST");
      con.setRequestProperty("Content-Type", "application/json");
      con.setRequestProperty("Accept", "application/json");
      con.setDoOutput(true);
      String body = "{\"idul\":\"" + idul + "\",\"fee\":" + fee + "}";

      try (OutputStream outputStream = con.getOutputStream()) {
        byte[] input = body.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        outputStream.write(input, 0, input.length);
      }
      if (con.getResponseCode() != 200) {
        throw new InvalidSubscriptionParameterException();
      }
    } catch (IOException err) {
      throw new InvalidSubscriptionParameterException();
    }
  }

  public Validate getValidate(String idul, String email, String session) throws IOException {
    URL url = new URL(endpoint + "semesters/" + session + "/validate");
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("POST");
    con.setRequestProperty("Content-Type", "application/json");
    con.setRequestProperty("Accept", "application/json");
    con.setDoOutput(true);
    String body = "{\"idul\":\"" + idul + "\",\"email\":\"" + email + "\"}";

    try (OutputStream outputStream = con.getOutputStream()) {
      byte[] input = body.getBytes(java.nio.charset.StandardCharsets.UTF_8);
      outputStream.write(input, 0, input.length);
    }
    try (BufferedReader bufferedReader = new BufferedReader(
        new InputStreamReader(con.getInputStream(), "utf-8"))) {
      StringBuilder response = new StringBuilder();
      String responseLine = null;
      while ((responseLine = bufferedReader.readLine()) != null) {
        response.append(responseLine.trim());
      }
      ObjectMapper mapper = new ObjectMapper();
      Validate validate = mapper.readValue(response.toString(), Validate.class);
      return validate;
    }
  }
}