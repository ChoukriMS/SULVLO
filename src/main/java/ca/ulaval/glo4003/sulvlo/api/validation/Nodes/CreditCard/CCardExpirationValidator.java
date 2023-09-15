package ca.ulaval.glo4003.sulvlo.api.validation.Nodes.CreditCard;

import ca.ulaval.glo4003.sulvlo.api.payment.dto.CreditCardDto;
import ca.ulaval.glo4003.sulvlo.api.validation.ValidationNode;
import java.time.LocalDate;
import java.time.ZoneId;

public class CCardExpirationValidator extends ValidationNode<CreditCardDto> {

  static LocalDate localDate = LocalDate.now(ZoneId.systemDefault());
  private static final int CURRENT_MONTH = localDate.getMonthValue();
  private static final int CURRENT_YEAR = localDate.getYear();

  @Override
  public boolean isValid(CreditCardDto model) {
    if (model.expirationMonth() > 12 || model.expirationMonth() < 1
        || model.expirationYear() < CURRENT_YEAR) {
      return false;
    }
    if (model.expirationYear() == CURRENT_YEAR && model.expirationMonth() < CURRENT_MONTH) {
      return false;
    }
    return validNext(model);
  }

}