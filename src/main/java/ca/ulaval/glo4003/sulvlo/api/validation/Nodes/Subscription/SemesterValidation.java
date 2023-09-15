package ca.ulaval.glo4003.sulvlo.api.validation.Nodes.Subscription;

import ca.ulaval.glo4003.sulvlo.api.subscription.dto.SubscriptionDto;
import ca.ulaval.glo4003.sulvlo.api.validation.ValidationNode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class SemesterValidation extends ValidationNode<SubscriptionDto> {

  private static final List<String> SEASONS = List.of("A", "H", "E");

  private static final LocalDate localDate = LocalDate.now(ZoneId.systemDefault());
  private static final int CURRENT_YEAR = localDate.getYear();
  private static int NUMBER;

  @Override
  public boolean isValid(SubscriptionDto model) {
    if (model.semester().length() != 3) {
      return false;
    }

    String season = model.semester().substring(0, 1);
    String year = model.semester().substring(1, 3);

    if (!SEASONS.contains(season)) {
      return false;
    }

    try {
      NUMBER = Integer.parseInt(year);
      if (NUMBER < ((CURRENT_YEAR % 100) + 1)) {
        return false;
      }
    } catch (NumberFormatException ex) {
      return false;
    }

    return validNext(model);
  }

}