package ca.ulaval.glo4003.sulvlo.domain.bike.exception;

import jakarta.ws.rs.BadRequestException;

public class InvalidBikeEnergyException extends BadRequestException {

  private static final String DESCRIPTION = "Bike low on energy cannot be unlocked.";

  public InvalidBikeEnergyException() {
    super(DESCRIPTION);
  }

}
