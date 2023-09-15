package ca.ulaval.glo4003.sulvlo.api.validation.Nodes;

import ca.ulaval.glo4003.sulvlo.api.user.dto.PasswordDto;
import ca.ulaval.glo4003.sulvlo.api.validation.ValidationNode;

public class PasswordValidation extends ValidationNode<PasswordDto> {

  @Override
  public boolean isValid(PasswordDto model) {
    if (model.password() == null || model.password().isBlank()) {
      return false;
    }
    return validNext(model);
  }

}