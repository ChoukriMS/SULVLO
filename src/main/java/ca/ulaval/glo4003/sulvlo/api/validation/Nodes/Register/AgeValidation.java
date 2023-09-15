package ca.ulaval.glo4003.sulvlo.api.validation.Nodes.Register;

import ca.ulaval.glo4003.sulvlo.api.user.dto.RegisterDto;
import ca.ulaval.glo4003.sulvlo.api.validation.ValidationNode;

public class AgeValidation extends ValidationNode<RegisterDto> {

  @Override
  public boolean isValid(RegisterDto model) {
    if (model.age() <= 0 || model.age() > 122) {
      return false;
    }
    return validNext(model);
  }

}