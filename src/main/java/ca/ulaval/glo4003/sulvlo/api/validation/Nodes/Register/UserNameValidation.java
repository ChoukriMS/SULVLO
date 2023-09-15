package ca.ulaval.glo4003.sulvlo.api.validation.Nodes.Register;

import ca.ulaval.glo4003.sulvlo.api.user.dto.RegisterDto;
import ca.ulaval.glo4003.sulvlo.api.validation.ValidationNode;

public class UserNameValidation extends ValidationNode<RegisterDto> {

  @Override
  public boolean isValid(RegisterDto model) {
    if (model.name() == null || model.name().isBlank() || model.name().length() > 20) {
      return false;
    }
    return validNext(model);
  }

}
