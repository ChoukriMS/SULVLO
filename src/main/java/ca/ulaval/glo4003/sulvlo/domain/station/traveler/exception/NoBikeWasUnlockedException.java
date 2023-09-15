package ca.ulaval.glo4003.sulvlo.domain.station.traveler.exception;

import jakarta.ws.rs.BadRequestException;

public class NoBikeWasUnlockedException extends BadRequestException {

  private static final String DESCRIPTION = "No bike was unlocked";

  public NoBikeWasUnlockedException() {
    super(DESCRIPTION);
  }
}
