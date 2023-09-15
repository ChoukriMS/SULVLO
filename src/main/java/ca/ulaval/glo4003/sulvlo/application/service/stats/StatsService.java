package ca.ulaval.glo4003.sulvlo.application.service.stats;

import ca.ulaval.glo4003.sulvlo.api.stats.dto.StatsDto;
import ca.ulaval.glo4003.sulvlo.domain.station.Station;
import ca.ulaval.glo4003.sulvlo.domain.station.service.StationDomainService;
import ca.ulaval.glo4003.sulvlo.domain.station.service.TravelerDomainService;
import ca.ulaval.glo4003.sulvlo.domain.station.traveler.Traveler;
import ca.ulaval.glo4003.sulvlo.domain.stats.ManagerNotifierDomainService;
import ca.ulaval.glo4003.sulvlo.domain.stats.StatsAssembler;
import java.util.List;

public class StatsService {

  private final StatsAssembler statsAssembler;
  private final StationDomainService stationDomainService;
  private final TravelerDomainService travelerDomainService;
  private final ManagerNotifierDomainService managerNotifierDomainService;

  public StatsService(StatsAssembler statsAssembler,
      StationDomainService stationDomainService,
      TravelerDomainService travelerDomainService,
      ManagerNotifierDomainService managerNotifierDomainService) {
    this.statsAssembler = statsAssembler;
    this.stationDomainService = stationDomainService;
    this.travelerDomainService = travelerDomainService;
    this.managerNotifierDomainService = managerNotifierDomainService;
  }

  public StatsDto findAllBikesAvailabilities() {
    List<Station> stations = stationDomainService.findAllStations();
    List<Traveler> travelers = travelerDomainService.findAllTravelers();
    int numberOfAvailableBikes = stations.stream().mapToInt(Station::getNumberOfAvailableBikes)
        .sum();
    int numberOfTakenBikes = travelers.size();
    return statsAssembler.create(numberOfAvailableBikes, numberOfTakenBikes);
  }

  public void verifyCurrentBikesAvailabilities() {
    List<Station> stations = stationDomainService.findAllStations();
    int numberOfAvailableBikes = stations.stream().mapToInt(Station::getNumberOfAvailableBikes)
        .sum();
    if (numberOfAvailableBikes == 0) {
      managerNotifierDomainService.sendEmailNotification();
    }
  }

}
