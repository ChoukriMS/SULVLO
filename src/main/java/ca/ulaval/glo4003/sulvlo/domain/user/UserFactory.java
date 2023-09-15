package ca.ulaval.glo4003.sulvlo.domain.user;

import ca.ulaval.glo4003.sulvlo.api.user.dto.RegisterDto;
import ca.ulaval.glo4003.sulvlo.domain.user.exception.InvalidBirthDateException;
import ca.ulaval.glo4003.sulvlo.domain.user.information.Gender;
import ca.ulaval.glo4003.sulvlo.domain.user.information.UserId;
import ca.ulaval.glo4003.sulvlo.domain.user.information.UserInformation;
import jakarta.ws.rs.BadRequestException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class UserFactory {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  private LocalDate transformDate(String date) throws BadRequestException {
    try {
      return LocalDate.parse(date, FORMATTER);
    } catch (DateTimeParseException e) {
      throw new InvalidBirthDateException();
    }
  }

  private Gender transformGender(String gender) {
    return Gender.valueOf(gender.toUpperCase());
  }

  public User create(RegisterDto info) {
    return new User(createUserInformation(info), UserType.NORMAL, new UserId(info.idul()));
  }

  private UserInformation createUserInformation(RegisterDto userInformation) {
    return new UserInformation(userInformation.name(), userInformation.email(),
        userInformation.age(),
        transformDate(userInformation.birthDate()),
        transformGender(userInformation.gender()), UUID.randomUUID(),
        userInformation.password().hashCode());
  }
}
