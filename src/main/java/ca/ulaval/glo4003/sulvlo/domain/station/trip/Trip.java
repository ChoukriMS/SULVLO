package ca.ulaval.glo4003.sulvlo.domain.station.trip;

import ca.ulaval.glo4003.sulvlo.domain.bike.Bike;
import ca.ulaval.glo4003.sulvlo.domain.station.Station;

public record Trip(String idul, Bike bike, Station unlockStation, Station returnStation) {

}
