package ca.ulaval.glo4003.sulvlo.domain.truck;

import ca.ulaval.glo4003.sulvlo.domain.bike.Bike;
import ca.ulaval.glo4003.sulvlo.domain.bike.exception.BikeNotFoundException;
import ca.ulaval.glo4003.sulvlo.domain.station.Station;
import java.util.ArrayList;
import java.util.List;

public class Truck {

  private final TruckId truckId;
  private final List<Bike> loadedBikes;

  public Truck() {
    this.truckId = new TruckId();
    this.loadedBikes = new ArrayList<>();
  }

  public TruckId getTruckId() {
    return truckId;
  }

  public List<Bike> getLoadedBikes() {
    return loadedBikes;
  }

  public void loadBikes(List<Bike> bikes) {
    for (int i = 0; i < bikes.size(); i++) {
      Bike bike = bikes.get(i).createClone();
      bike.setToAvailable();
      bike.setLocation(i + 1);
      loadedBikes.add(bike);
    }
  }

  public void unload(Station station, int inTruckBikeLocation, int toStationBikeLocation) {
    Bike loadedBike = findLoadedBikeByLocation(inTruckBikeLocation);
    loadedBike.setToAvailable();
    station.placeBike(toStationBikeLocation, loadedBike);
    loadedBikes.remove(loadedBike);
  }

  public Bike findLoadedBikeByLocation(int location) {
    for (Bike bike : loadedBikes) {
      if (bike.getLocation() == location) {
        return bike;
      }
    }
    throw new BikeNotFoundException(location);
  }

}
