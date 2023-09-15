package ca.ulaval.glo4003.sulvlo.application.service.truck;

import ca.ulaval.glo4003.sulvlo.api.truck.dto.TruckDto;
import ca.ulaval.glo4003.sulvlo.api.truck.dto.UnloadBikesDto.UnloadBikeData;
import ca.ulaval.glo4003.sulvlo.domain.bike.Bike;
import ca.ulaval.glo4003.sulvlo.domain.station.Station;
import ca.ulaval.glo4003.sulvlo.domain.station.service.StationDomainService;
import ca.ulaval.glo4003.sulvlo.domain.truck.Truck;
import ca.ulaval.glo4003.sulvlo.domain.truck.TruckAssembler;
import ca.ulaval.glo4003.sulvlo.domain.truck.TruckId;
import ca.ulaval.glo4003.sulvlo.domain.truck.TruckRepository;
import java.util.List;
import java.util.stream.Collectors;

public class TruckService {

  private final StationDomainService stationDomainService;
  private final TruckRepository truckRepository;
  private final TruckAssembler truckAssembler;

  public TruckService(StationDomainService stationDomainService,
      TruckRepository truckRepository,
      TruckAssembler truckAssembler) {
    this.stationDomainService = stationDomainService;
    this.truckRepository = truckRepository;
    this.truckAssembler = truckAssembler;
  }

  public List<TruckDto> findAllTrucks() {
    List<Truck> trucks = truckRepository.findAll();

    return trucks.stream().map(truckAssembler::createTruckDto).collect(Collectors.toList());
  }

  public void loadBikes(String truckId, String fromStationCode, List<String> bikesLocations) {
    Truck truck = findTruckById(truckId);
    Station station = findUnderMaintenanceStationByCode(fromStationCode);

    truck.loadBikes(getBikesToBeLoaded(bikesLocations, station));
    station.removeLoadedBikes(toIntBikesLocations(bikesLocations));

    truckRepository.save(truck);
    stationDomainService.saveUnderMaintenance(station);
  }

  private Truck findTruckById(String truckId) {
    return truckRepository.findById(TruckId.fromString(truckId));
  }

  private Station findUnderMaintenanceStationByCode(String code) {
    return stationDomainService.findUnderMaintenanceByCode(code);
  }

  private List<Bike> getBikesToBeLoaded(List<String> bikesLocations, Station station) {
    return station.findAvailableBikesByLocation(toIntBikesLocations(bikesLocations));
  }

  private List<Integer> toIntBikesLocations(List<String> bikesLocations) {
    return bikesLocations.stream().map(this::toIntBikeLocation).toList();
  }

  private int toIntBikeLocation(String bikeLocation) {
    return Integer.parseInt(bikeLocation);
  }

  public void unload(String truckId, List<UnloadBikeData> unloadBikeDataList) {
    Truck truck = findTruckById(truckId);
    unloadBikeDataList.forEach(unloadBikeData -> {
      Station station = findAvailableStationByCode(unloadBikeData.toStationCode());
      int inTruckBikeLocation = toIntBikeLocation(unloadBikeData.bikesLocations().get(0));
      int toStationBikeLocation = toIntBikeLocation(unloadBikeData.bikesLocations().get(1));
      truck.unload(station, inTruckBikeLocation, toStationBikeLocation);
      stationDomainService.saveAvailable(station);
    });
    truckRepository.save(truck);
  }

  private Station findAvailableStationByCode(String code) {
    return stationDomainService.findAvailableByCode(code);
  }

}
