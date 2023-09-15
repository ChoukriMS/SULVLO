package ca.ulaval.glo4003.sulvlo.api.truck.validation;

import ca.ulaval.glo4003.sulvlo.api.station.validation.CommonStationRequestsValidators;
import ca.ulaval.glo4003.sulvlo.domain.truck.TruckId;
import jakarta.ws.rs.BadRequestException;

public record CommonTruckRequestsValidators(
    CommonStationRequestsValidators commonStationRequestsValidators) {

  private static final String BAD_REQUEST_TRUCK_ID_MSG = "Invalid truck id.";

  public void validateTruckId(String truckId) {
    try {
      TruckId.fromString(truckId);
    } catch (Exception e) {
      throw new BadRequestException(BAD_REQUEST_TRUCK_ID_MSG);
    }
  }
}
