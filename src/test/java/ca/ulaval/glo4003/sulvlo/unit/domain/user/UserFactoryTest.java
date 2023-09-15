package ca.ulaval.glo4003.sulvlo.unit.domain.user;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.sulvlo.api.user.dto.RegisterDto;
import ca.ulaval.glo4003.sulvlo.domain.user.User;
import ca.ulaval.glo4003.sulvlo.domain.user.UserFactory;
import ca.ulaval.glo4003.sulvlo.domain.user.UserType;
import ca.ulaval.glo4003.sulvlo.domain.user.exception.InvalidBirthDateException;
import ca.ulaval.glo4003.sulvlo.domain.user.information.Gender;
import ca.ulaval.glo4003.sulvlo.domain.user.information.UserId;
import ca.ulaval.glo4003.sulvlo.domain.user.information.UserInformation;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserFactoryTest {

  private static final UserType userType = UserType.NORMAL;
  private static final String INVAlID_BIRTH_DATE = "zd/11/2000";
  private static final String BIRTH_DATE = "30/12/2000";
  private static final String NAME = "Thomas";
  private static final String EMAIL = "thomas@test.fr";
  private static final String IDUL = "tht5";
  private static final int AGE = 21;
  private static final String PASSWORD = "password";
  private static final String MALE = "MALE";
  private UserFactory factory;

  @BeforeEach
  void setup() {
    factory = new UserFactory();
  }

  @Test
  void givenAInvalidBirthDate_whenCreateAUser_thenThrowBadRequestException() {
    RegisterDto info = new RegisterDto(NAME, EMAIL, IDUL, AGE, PASSWORD, INVAlID_BIRTH_DATE,
        MALE);

    assertThrows(InvalidBirthDateException.class, () -> {
      factory.create(info);
    });
  }

  @Test
  void givenAValidDate_whenCreateAUser_thenCreateUser() {
    RegisterDto info = new RegisterDto(NAME, EMAIL, IDUL, AGE, PASSWORD, BIRTH_DATE, MALE);

    User user = factory.create(info);

    User expectedUser = createExpectedUser();

    assertThat(user.getEmail()).isEqualTo(expectedUser.getEmail());
    assertThat(user.getGender()).isEqualTo(expectedUser.getGender());
    assertThat(user.getName()).isEqualTo(expectedUser.getName());
  }

  private User createExpectedUser() {
    return new User(new UserInformation(NAME, EMAIL, AGE,
        LocalDate.parse(BIRTH_DATE, DateTimeFormatter.ofPattern("dd/MM/yyyy")), Gender.MALE,
        UUID.randomUUID(), PASSWORD.hashCode()), userType, new UserId(IDUL));
  }

}