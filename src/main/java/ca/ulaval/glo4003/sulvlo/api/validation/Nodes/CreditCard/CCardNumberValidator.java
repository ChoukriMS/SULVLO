package ca.ulaval.glo4003.sulvlo.api.validation.Nodes.CreditCard;

import ca.ulaval.glo4003.sulvlo.api.payment.dto.CreditCardDto;
import ca.ulaval.glo4003.sulvlo.api.validation.ValidationNode;

public class CCardNumberValidator extends ValidationNode<CreditCardDto> {

  @Override
  public boolean isValid(CreditCardDto model) {
    if (model.creditCardNumber() == null || model.creditCardNumber().isBlank()
        || model.creditCardNumber().length() != 16) {
      return false;
    }
    return validNext(model);
  }

}