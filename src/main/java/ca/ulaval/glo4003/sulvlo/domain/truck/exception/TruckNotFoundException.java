package ca.ulaval.glo4003.sulvlo.domain.truck.exception;

import ca.ulaval.glo4003.sulvlo.domain.truck.TruckId;
import jakarta.ws.rs.NotFoundException;

public class TruckNotFoundException extends NotFoundException {

  private static final String DESCRIPTION = "Truck '%s' not found.";

  public TruckNotFoundException(TruckId truckId) {
    super(String.format(DESCRIPTION, truckId.getValue()));
  }

}
