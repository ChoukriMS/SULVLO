package ca.ulaval.glo4003.sulvlo.api.validation.Nodes.Register;

import ca.ulaval.glo4003.sulvlo.api.user.dto.RegisterDto;
import ca.ulaval.glo4003.sulvlo.api.validation.ValidationNode;

public class GenderValidation extends ValidationNode<RegisterDto> {

  @Override
  public boolean isValid(RegisterDto model) {
    String gender = model.gender();
    if (gender != null) {
      gender = gender.toUpperCase();
    }
    if (gender == null || (gender.compareTo("MALE") != 0 && gender.compareTo("FEMALE") != 0
        && gender.compareTo("OTHER") != 0)) {
      return false;
    }
    return validNext(model);
  }

}