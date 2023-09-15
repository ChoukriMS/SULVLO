package ca.ulaval.glo4003.sulvlo.api.validation.Nodes;

import ca.ulaval.glo4003.sulvlo.api.user.dto.EmailDto;
import ca.ulaval.glo4003.sulvlo.api.validation.ValidationNode;
import java.util.regex.Pattern;

public class EmailValidation extends ValidationNode<EmailDto> {

  private static boolean isEmail(String email) {
    return Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
        .matcher(email)
        .find();
  }

  @Override
  public boolean isValid(EmailDto model) {
    if (model.email() == null || model.email().isBlank() || !isEmail(model.email())) {
      return false;
    }
    return validNext(model);
  }

}