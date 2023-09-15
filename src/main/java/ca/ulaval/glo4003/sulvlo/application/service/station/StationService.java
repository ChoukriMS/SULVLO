package ca.ulaval.glo4003.sulvlo.application.service.station;

import ca.ulaval.glo4003.sulvlo.api.station.dto.StationDto;
import ca.ulaval.glo4003.sulvlo.application.service.station.exception.UnlockBikeException;
import ca.ulaval.glo4003.sulvlo.domain.bike.Bike;
import ca.ulaval.glo4003.sulvlo.domain.station.Station;
import ca.ulaval.glo4003.sulvlo.domain.station.StationAssembler;
import ca.ulaval.glo4003.sulvlo.domain.station.StationRepository;
import ca.ulaval.glo4003.sulvlo.domain.station.UnlockBikeToken;
import ca.ulaval.glo4003.sulvlo.domain.station.service.TravelerDomainService;
import ca.ulaval.glo4003.sulvlo.domain.station.service.TripDomainService;
import ca.ulaval.glo4003.sulvlo.domain.station.service.UniqueCodeDomainService;
import ca.ulaval.glo4003.sulvlo.domain.station.traveler.Traveler;
import ca.ulaval.glo4003.sulvlo.domain.station.traveler.exception.TravelerNotFoundException;
import ca.ulaval.glo4003.sulvlo.domain.station.trip.Trip;
import java.time.Duration;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StationService {

  private static final Logger LOGGER = LoggerFactory.getLogger(StationService.class);

  private final StationRepository stationRepository;
  private final StationAssembler stationAssembler;
  private final TripDomainService tripDomainService;
  private final TravelerDomainService travelerDomainService;
  private final UniqueCodeDomainService uniqueCodeDomainService;

  public StationService(StationRepository stationRepository,
      StationAssembler stationAssembler,
      TripDomainService tripDomainService,
      TravelerDomainService travelerDomainService,
      UniqueCodeDomainService uniqueCodeDomainService) {
    this.stationRepository = stationRepository;
    this.stationAssembler = stationAssembler;
    this.tripDomainService = tripDomainService;
    this.travelerDomainService = travelerDomainService;
    this.uniqueCodeDomainService = uniqueCodeDomainService;
  }

  public List<StationDto> findAllAvailableStations() {
    List<Station> stations = stationRepository.findAllAvailable();

    return stations.stream().map(stationAssembler::assemble).toList();
  }

  public List<StationDto> findAllUnderMaintenanceStations() {
    List<Station> stations = stationRepository.findAllUnderMaintenance();

    return stations.stream().map(stationAssembler::createStationUnderMaintenanceDto)
        .toList();
  }

  public void createUniqueCode(String email) {
    travelerDomainService.checkAccountValidity(email);
    UnlockBikeToken unlockBikeToken = uniqueCodeDomainService.generateUnlockBikeToken();

    LOGGER.info("Send unique code : " + unlockBikeToken.code());

    uniqueCodeDomainService.sendUniqueCode(email, unlockBikeToken.code());
  }


  public void unlockBike(String userCode, String stationCode, String bikeLocation, String idul) {
    uniqueCodeDomainService.isTokenValid(userCode);

    Station station = stationRepository.findAvailableByCode(stationCode);
    Bike bike = station.findAvailableBikeByLocation(toIntBikeLocation(bikeLocation));

    Traveler traveler = initiateTraveler(idul);

    station.unlockBike(bike);

    traveler.startTravel(stationCode, bike);

    travelerDomainService.saveTraveler(traveler);
  }

  private Traveler initiateTraveler(String idul) {
    Traveler traveler;
    try {
      traveler = travelerDomainService.findByIdul(idul);
      if (traveler.hasUnlockedBike()) {
        throw new UnlockBikeException();
      }
    } catch (TravelerNotFoundException e) {
      traveler = travelerDomainService.createTraveler(idul);
    }
    return traveler;
  }

  private int toIntBikeLocation(String bikeLocation) {
    return Integer.parseInt(bikeLocation);
  }

  public void returnBike(String returnStationCode, String returnBikeLocation, String idul) {
    Traveler traveler = travelerDomainService.findByIdul(idul);
    Station station = stationRepository.findAvailableByCode(returnStationCode);
    Bike bike = traveler.getUnlockedBike();

    station.returnBike(bike, toIntBikeLocation(returnBikeLocation));

    Duration duration = bike.calculateTravelDuration();

    Trip trip = new Trip(traveler.getIdul(), bike,
        stationRepository.findAvailableByCode(traveler.getUnlockStationCode()), station);
    tripDomainService.save(trip);

    traveler.resetUnlockInfos();
    travelerDomainService.sendTravelerBill(traveler.getIdul(), duration);
  }

  public void startMaintenance(String stationCode) {
    Station station = stationRepository.findAvailableByCode(stationCode);

    stationRepository.deleteAvailable(stationCode);
    stationRepository.saveUnderMaintenance(station);
  }

  public void endMaintenance(String stationCode) {
    Station station = stationRepository.findUnderMaintenanceByCode(stationCode);

    stationRepository.deleteUnderMaintenance(stationCode);
    stationRepository.saveAvailable(station);
  }
}
