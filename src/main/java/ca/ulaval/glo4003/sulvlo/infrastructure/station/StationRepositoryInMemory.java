package ca.ulaval.glo4003.sulvlo.infrastructure.station;

import ca.ulaval.glo4003.sulvlo.domain.station.Station;
import ca.ulaval.glo4003.sulvlo.domain.station.StationRepository;
import ca.ulaval.glo4003.sulvlo.domain.station.exception.StationNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StationRepositoryInMemory implements StationRepository {

  private final StationDevDataFactory stationDevDataFactory;
  private final Map<String, Station> availableStationHashMap = new HashMap<>();
  private final Map<String, Station> underMaintenanceStationHashMap = new HashMap<>();
  private Map<String, Station> allStationsHashMap = new HashMap<>();


  public StationRepositoryInMemory(StationDevDataFactory stationDataFactory) {
    this.stationDevDataFactory = stationDataFactory;
    List<Station> stations = this.stationDevDataFactory.createMockData();

    stations.stream().forEach(station -> saveAvailable(station));
  }

  @Override
  public List<Station> findAllAvailable() {
    return availableStationHashMap.values().stream().toList();
  }

  @Override
  public List<Station> findAllUnderMaintenance() {
    return underMaintenanceStationHashMap.values().stream().toList();
  }

  @Override
  public List<Station> findAllStations() {
    allStationsHashMap = Stream.of(availableStationHashMap, underMaintenanceStationHashMap)
        .flatMap(m -> m.entrySet().stream())
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    return allStationsHashMap.values().stream().toList();
  }

  @Override
  public Station findAvailableByCode(String code) {
    Station station = availableStationHashMap.get(code);
    return getStation(code, station);
  }

  @Override
  public Station findUnderMaintenanceByCode(String code) {
    Station station = underMaintenanceStationHashMap.get(code);
    return getStation(code, station);
  }

  private Station getStation(String code, Station station) {
    if (station != null) {
      return station;
    } else {
      throw new StationNotFoundException(code);
    }
  }

  @Override
  public void saveAvailable(Station station) {
    availableStationHashMap.put(station.getCode(), station);
  }

  @Override
  public void saveUnderMaintenance(Station station) {
    underMaintenanceStationHashMap.put(station.getCode(), station);
  }

  @Override
  public void deleteAvailable(String code) {
    availableStationHashMap.remove(code);
  }

  @Override
  public void deleteUnderMaintenance(String code) {
    underMaintenanceStationHashMap.remove(code);
  }
}
