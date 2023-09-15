package ca.ulaval.glo4003.sulvlo.domain.bike;

import ca.ulaval.glo4003.sulvlo.domain.bike.exception.InvalidBikeEnergyException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Bike {

  public static final double PERCENTAGE_LOST_PER_MINUTE = 0.005;
  public static final double LIMIT_ENERGY_LEVEL = 20;
  public static final int TRAVEL_LIMIT_NORMAL_USERS_IN_MINUTES = 10;
  public static final int TRAVEL_LIMIT_EMPLOYEES_IN_MINUTES = 30;
  private static final double PERCENTAGE_GAIN_PER_MINUTE = 0.002;
  private int location;
  private EnergyLevel energyLevel;
  private LocalDateTime unlockDateTime = null;
  private LocalDateTime returnDateTime = null;
  private BikeStatus status = BikeStatus.AVAILABLE;

  public Bike(int location, EnergyLevel energyLevel) {
    this.location = location;
    this.energyLevel = energyLevel;
  }

  public int getLocation() {
    return location;
  }

  public void setLocation(int location) {
    this.location = location;
  }

  public EnergyLevel getEnergyLevel() {
    return energyLevel;
  }

  public void setEnergyLevel(EnergyLevel energyLevel) {
    this.energyLevel = energyLevel;
  }

  public LocalDateTime getUnlockDateTime() {
    return unlockDateTime;
  }

  public void setUnlockDateTime(LocalDateTime unlockDateTime) {
    this.unlockDateTime = unlockDateTime;
  }

  public LocalDateTime getReturnDateTime() {
    return returnDateTime;
  }

  public void setReturnDateTime(LocalDateTime returnDateTime) {
    this.returnDateTime = returnDateTime;
  }

  public BikeStatus getStatus() {
    return status;
  }

  public void checkBikeEnergyLevelValidity() {
    if (energyLevel.compareTo(LIMIT_ENERGY_LEVEL) <= 0) {
      throw new InvalidBikeEnergyException();
    }
  }

  public Duration calculateTravelDuration() {
    return Duration.between(unlockDateTime, LocalDateTime.now());
  }

  public void calculateEnergyLevelAfterReturn() {
    double energyLevelAfterReturn = this.energyLevel.getValue()
        - Duration.between(unlockDateTime, returnDateTime).toMinutes() * PERCENTAGE_LOST_PER_MINUTE;
    if (energyLevelAfterReturn <= 0) {
      this.energyLevel = new EnergyLevel(BigDecimal.ZERO);
    } else {
      this.energyLevel = new EnergyLevel(BigDecimal.valueOf(energyLevelAfterReturn));
    }
  }

  public void setToInTransit() {
    status = BikeStatus.IN_TRANSIT;
  }

  public void setToAvailable() {
    status = BikeStatus.AVAILABLE;
  }

  public double updateEnergyLevel(LocalDateTime newCurrentDateTime, LocalDateTime currentDateTime) {
    return this.energyLevel.getValue()
        + Duration.between(currentDateTime, newCurrentDateTime).toMinutes()
        * PERCENTAGE_GAIN_PER_MINUTE;
  }

  public void dock(int returnLocation) {
    setLocation(returnLocation);
    setReturnDateTime(LocalDateTime.now());
    calculateEnergyLevelAfterReturn();
  }

  public void unlock() {
    checkBikeEnergyLevelValidity();
    setUnlockDateTime(LocalDateTime.now());
  }

  public boolean locatedAt(int location) {
    return this.location == location;
  }

  public Bike createClone() {
    Bike bike = new Bike(this.location, this.energyLevel);
    bike.unlockDateTime = this.unlockDateTime;
    bike.returnDateTime = this.returnDateTime;
    bike.status = this.status;

    return bike;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Bike bike = (Bike) o;
    return location == bike.location && Objects.equals(energyLevel, bike.energyLevel) &&
        Objects.equals(unlockDateTime, bike.unlockDateTime) &&
        Objects.equals(returnDateTime, bike.returnDateTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(location, energyLevel, unlockDateTime, returnDateTime, status);
  }

}
