package ca.ulaval.glo4003.sulvlo.domain.station.traveler;

import ca.ulaval.glo4003.sulvlo.domain.bike.Bike;
import ca.ulaval.glo4003.sulvlo.domain.station.traveler.exception.NoBikeWasUnlockedException;

public class Traveler {

  private final String idul;
  private String unlockStationCode;
  private Bike unlockedBike;

  public Traveler(String idul) {
    this.idul = idul;
    this.unlockStationCode = null;
    this.unlockedBike = null;
  }

  public String getUnlockStationCode() {
    return unlockStationCode;
  }

  public Bike getUnlockedBike() {
    if (unlockedBike == null) {
      throw new NoBikeWasUnlockedException();
    }
    return unlockedBike;
  }

  public void resetUnlockInfos() {
    this.unlockStationCode = null;
    this.unlockedBike = null;
  }

  public String getIdul() {
    return idul;
  }

  public boolean hasUnlockedBike() {
    return unlockedBike != null;
  }

  public void startTravel(String stationCode, Bike bike) {
    this.unlockStationCode = stationCode;
    this.unlockedBike = bike;
  }
}
