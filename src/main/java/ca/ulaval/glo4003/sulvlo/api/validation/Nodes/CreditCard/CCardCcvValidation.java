package ca.ulaval.glo4003.sulvlo.api.validation.Nodes.CreditCard;

import ca.ulaval.glo4003.sulvlo.api.payment.dto.CreditCardDto;
import ca.ulaval.glo4003.sulvlo.api.validation.ValidationNode;

public class CCardCcvValidation extends ValidationNode<CreditCardDto> {

  @Override
  public boolean isValid(CreditCardDto model) {
    if (model.ccv() < 100 || model.ccv() > 999) {
      return false;
    }
    return validNext(model);
  }

}