package ca.ulaval.glo4003.sulvlo.domain.station.service;

import ca.ulaval.glo4003.sulvlo.domain.station.Station;
import ca.ulaval.glo4003.sulvlo.domain.station.StationRepository;
import java.util.List;

public class StationDomainService {

  private final StationRepository stationRepository;

  public StationDomainService(StationRepository stationRepository) {
    this.stationRepository = stationRepository;
  }

  public Station findAvailableByCode(String code) {
    return stationRepository.findAvailableByCode(code);
  }

  public Station findUnderMaintenanceByCode(String code) {
    return stationRepository.findUnderMaintenanceByCode(code);
  }

  public void saveAvailable(Station station) {
    stationRepository.saveAvailable(station);
  }

  public void saveUnderMaintenance(Station station) {
    stationRepository.saveUnderMaintenance(station);
  }

  public List<Station> findAllStations() {
    return stationRepository.findAllStations();
  }
}
