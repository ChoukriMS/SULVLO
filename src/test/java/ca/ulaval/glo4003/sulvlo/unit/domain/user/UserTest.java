package ca.ulaval.glo4003.sulvlo.unit.domain.user;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.sulvlo.domain.user.User;
import ca.ulaval.glo4003.sulvlo.domain.user.UserType;
import ca.ulaval.glo4003.sulvlo.domain.user.exception.UserConnectionException;
import ca.ulaval.glo4003.sulvlo.domain.user.information.Gender;
import ca.ulaval.glo4003.sulvlo.domain.user.information.UserId;
import ca.ulaval.glo4003.sulvlo.domain.user.information.UserInformation;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {

  private static final UserType userType = UserType.NORMAL;
  private static final String GOOD_PASSWORD = "J34nP4trickL4S4rd1n3";
  private static final String BAD_PASSWORD = "J34nP4trickL4S4rd1n";
  private static final String DATE_IN_STRING = "16/08/2016";
  private static final String DATE_PATTERN = "dd/MM/yyyy";
  private static final String USER_ID = "cab8d0ea-a636-487b-ae96-6410da85f8ce";
  private static final String USER_NAME = "Thomas";
  private static final String USER_EMAIL = "thomas@test.fr";
  private static final String USER_IDUL = "tht3";
  private static final int USER_AGE = 21;
  private static final Gender USER_GENDER = Gender.MALE;

  private DateTimeFormatter formatter;
  private LocalDate localDate;
  private User user;

  @BeforeEach
  void setup() {
    formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
    localDate = LocalDate.parse(DATE_IN_STRING, formatter);
    user = new User(
        new UserInformation(USER_NAME, USER_EMAIL, USER_AGE, localDate, USER_GENDER,
            UUID.fromString(USER_ID), GOOD_PASSWORD.hashCode()), userType, new UserId(USER_IDUL));
  }

  @Test
  void givenABadPassword_whenCheckPassword_thenThrowUserConnectionException() {
    assertThrows(UserConnectionException.class, () -> {
      user.checkPassword(BAD_PASSWORD.hashCode());
    });
  }

  @Test
  void givenAGoodPassword_whenCheckPassword_thenNotThrow() {
    assertDoesNotThrow(() -> {
      user.checkPassword(GOOD_PASSWORD.hashCode());
    });
  }

}
