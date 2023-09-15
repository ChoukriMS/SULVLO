package ca.ulaval.glo4003.sulvlo.domain.station;

import ca.ulaval.glo4003.sulvlo.domain.bike.Bike;
import ca.ulaval.glo4003.sulvlo.domain.bike.EnergyLevel;
import ca.ulaval.glo4003.sulvlo.domain.bike.exception.BikeNotFoundException;
import ca.ulaval.glo4003.sulvlo.domain.station.exception.InvalidPlaceInStationException;
import ca.ulaval.glo4003.sulvlo.domain.station.exception.InvalidReturnInStationException;
import ca.ulaval.glo4003.sulvlo.domain.station.exception.NoMoreEmptySpaceInThisStationException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Station {

  private final String location;
  private final String name;
  private final String code;
  private final int maxCapacity;
  private final List<Bike> availableBikes;
  private final List<Integer> emptyBikeLocations;
  private LocalDateTime currentDateTime = LocalDateTime.now();
  private int currentCapacity;

  public Station(String location, String name, String code, int currentCapacity, int maxCapacity,
      List<Bike> availableBikes, List<Integer> emptyBikeLocations) {
    this.location = location;
    this.name = name;
    this.code = code;
    this.currentCapacity = currentCapacity;
    this.maxCapacity = maxCapacity;
    this.availableBikes = availableBikes;
    this.emptyBikeLocations = emptyBikeLocations;
  }

  public LocalDateTime getCurrentDateTime() {
    return currentDateTime;
  }

  public void setCurrentDateTime(LocalDateTime currentDateTime) {
    this.currentDateTime = currentDateTime;
  }

  public String getLocation() {
    return location;
  }

  public String getName() {
    return name;
  }

  public String getCode() {
    return code;
  }

  public int getCurrentCapacity() {
    return currentCapacity;
  }

  public List<Bike> getAvailableBikes() {
    return availableBikes;
  }

  public int getNumberOfAvailableBikes() {
    return availableBikes.size();
  }

  public int getMaxCapacity() {
    return maxCapacity;
  }

  public List<Integer> getEmptyBikeLocations() {
    return emptyBikeLocations;
  }

  public List<Bike> findAvailableBikesByLocation(List<Integer> locations) {
    return locations.stream().map(this::findAvailableBikeByLocation).toList();
  }

  public void removeLoadedBikes(List<Integer> bikesLocations) {
    bikesLocations.forEach(location -> {
      Bike bike = findAvailableBikeByLocation(location);
      emptyBikeLocations.add(bike.getLocation());
      availableBikes.remove(bike);
      currentCapacity--;
    });
  }

  public Bike findAvailableBikeByLocation(int location) {
    for (Bike bike : availableBikes) {
      if (bike.locatedAt(location)) {
        return bike;
      }
    }
    throw new BikeNotFoundException(location);
  }

  public List<Bike> rechargeBikes() {
    LocalDateTime newCurrentDateTime = LocalDateTime.now();
    LocalDateTime stationDateTime = currentDateTime;

    if (newCurrentDateTime.isAfter(stationDateTime)) {
      currentDateTime = newCurrentDateTime;
      for (Bike bike : availableBikes) {
        double newBikeEnergyLevel = calculNewEnergyLevel(newCurrentDateTime, stationDateTime, bike);
        bike.setEnergyLevel(new EnergyLevel(BigDecimal.valueOf(newBikeEnergyLevel)));
      }
    }

    return availableBikes;
  }

  private double calculNewEnergyLevel(LocalDateTime newCurrentDateTime,
      LocalDateTime currentDateTime, Bike bike) {

    return bike.updateEnergyLevel(newCurrentDateTime, currentDateTime);
  }

  public void unlockBike(Bike bike) {
    bike.unlock();
    emptyBikeLocations.add(bike.getLocation());
    availableBikes.remove(bike);
    currentCapacity--;
  }

  public void returnBike(Bike bike, int returnLocation) {

    validateStation(returnLocation);

    bike.dock(returnLocation);

    availableBikes.add(bike);
    currentCapacity++;
    emptyBikeLocations.remove(Integer.valueOf(bike.getLocation()));
  }

  private void validateStation(int returnLocation) {
    checkStationCapacity();
    checkReturnLocationValidity(returnLocation);
  }


  private void checkStationCapacity() {
    if (currentCapacity == maxCapacity) {
      throw new NoMoreEmptySpaceInThisStationException(code);
    }
  }

  private void checkReturnLocationValidity(int returnLocation) {
    if (!isReturnLocationEmpty(returnLocation)) {
      throw new InvalidReturnInStationException(code);
    }
  }

  private boolean isReturnLocationEmpty(int returnLocation) {
    return emptyBikeLocations.contains(returnLocation);
  }

  public void placeBike(int toStationBikeLocation, Bike loadedBike) {
    checkStationCapacity();
    if (emptyBikeLocations.contains(toStationBikeLocation)) {
      availableBikes.add(loadedBike);
      availableBikes.stream().filter(bike -> bike.equals(loadedBike)).toList().get(0)
          .setLocation(toStationBikeLocation);

      emptyBikeLocations.remove(Integer.valueOf(toStationBikeLocation));
      currentCapacity++;
    } else {
      throw new InvalidPlaceInStationException(code);
    }
  }
}
