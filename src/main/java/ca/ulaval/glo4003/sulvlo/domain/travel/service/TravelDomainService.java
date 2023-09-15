package ca.ulaval.glo4003.sulvlo.domain.travel.service;

import ca.ulaval.glo4003.sulvlo.domain.bike.Bike;
import ca.ulaval.glo4003.sulvlo.domain.station.Station;
import ca.ulaval.glo4003.sulvlo.domain.travel.Travel;
import ca.ulaval.glo4003.sulvlo.domain.travel.TravelFactory;
import ca.ulaval.glo4003.sulvlo.domain.travel.TravelRepository;

public class TravelDomainService {

  private final TravelFactory travelFactory;
  private final TravelRepository travelRepository;

  public TravelDomainService(TravelFactory travelFactory, TravelRepository travelRepository) {
    this.travelFactory = travelFactory;
    this.travelRepository = travelRepository;
  }

  public Travel create(String idul, Bike bike, Station unlockStation, Station returnStation) {
    return travelFactory.create(idul, unlockStation.getCode(), returnStation.getCode(),
        bike.getUnlockDateTime(), bike.getReturnDateTime(), bike.getReturnDateTime().getMonth());
  }

  public void save(Travel travel) {
    travelRepository.save(travel);
  }
}
