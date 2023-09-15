package ca.ulaval.glo4003.sulvlo.domain.station.traveler;

public class TravelerFactory {

  public Traveler createTraveler(String idul) {
    return new Traveler(idul);
  }
}
