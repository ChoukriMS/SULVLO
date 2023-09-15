package ca.ulaval.glo4003.sulvlo.api.validation.Nodes.Register;

import ca.ulaval.glo4003.sulvlo.api.user.dto.RegisterDto;
import ca.ulaval.glo4003.sulvlo.api.validation.ValidationNode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class BirthDateValidation extends ValidationNode<RegisterDto> {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  private static boolean isDate(String date) {
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    dateFormat.setLenient(false);
    try {
      dateFormat.parse(date);
    } catch (ParseException e) {
      return false;
    }
    return true;
  }

  private static boolean isBirthDate(String date) {
    if (!isDate(date)) {
      return false;
    }
    try {
      LocalDate limit1900 = LocalDate.parse("01/01/1900", formatter);
      LocalDate birthDate = LocalDate.parse(date, formatter);
      LocalDate today = LocalDate.now();
      return limit1900.isBefore(birthDate) && today.isAfter(birthDate);
    } catch (DateTimeParseException e) {
      return false;
    }
  }

  @Override
  public boolean isValid(RegisterDto model) {
    if (model.birthDate() == null
        || model.birthDate().isBlank() || !isBirthDate(model.birthDate())) {
      return false;
    }
    return validNext(model);
  }

}