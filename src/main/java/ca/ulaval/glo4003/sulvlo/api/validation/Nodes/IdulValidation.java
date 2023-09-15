package ca.ulaval.glo4003.sulvlo.api.validation.Nodes;

import ca.ulaval.glo4003.sulvlo.api.user.dto.IdulDto;
import ca.ulaval.glo4003.sulvlo.api.validation.ValidationNode;
import java.util.regex.Pattern;

public class IdulValidation extends ValidationNode<IdulDto> {

  private static final String VALID_USER_IDUL_PATTERN = "[a-zA-Z0-9]+";

  @Override
  public boolean isValid(IdulDto model) {
    if (model.idul() == null
        || !Pattern.matches(VALID_USER_IDUL_PATTERN, model.idul().trim())) {
      return false;
    }
    return validNext(model);
  }

}
