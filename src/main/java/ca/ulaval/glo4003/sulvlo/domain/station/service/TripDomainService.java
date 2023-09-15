package ca.ulaval.glo4003.sulvlo.domain.station.service;

import ca.ulaval.glo4003.sulvlo.domain.station.trip.Trip;
import ca.ulaval.glo4003.sulvlo.domain.travel.Travel;
import ca.ulaval.glo4003.sulvlo.domain.travel.service.TravelDomainService;

public class TripDomainService {

  private final TravelDomainService travelDomainService;

  public TripDomainService(TravelDomainService travelDomainService) {
    this.travelDomainService = travelDomainService;
  }

  public void save(Trip trip) {
    Travel travel = travelDomainService.create(trip.idul(), trip.bike(),
        trip.unlockStation(), trip.returnStation());
    travelDomainService.save(travel);
  }
}
