package ca.ulaval.glo4003.sulvlo.api.validation.Nodes.Station;

import ca.ulaval.glo4003.sulvlo.api.user.dto.RequestMaintenanceDto;
import ca.ulaval.glo4003.sulvlo.api.validation.ValidationNode;
import java.util.regex.Pattern;

public class StationCodeValidation extends ValidationNode<RequestMaintenanceDto> {

  private static final String VALID_STATION_CODE_PATTERN = "^[A-Z]*$";

  @Override
  public boolean isValid(RequestMaintenanceDto model) {
    if (model.stationCode() == null || !Pattern.matches(VALID_STATION_CODE_PATTERN,
        model.stationCode().trim())) {
      return false;
    }
    return validNext(model);
  }

}