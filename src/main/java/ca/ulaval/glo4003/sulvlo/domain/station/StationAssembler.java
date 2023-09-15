package ca.ulaval.glo4003.sulvlo.domain.station;

import ca.ulaval.glo4003.sulvlo.api.bike.dto.BikeDto;
import ca.ulaval.glo4003.sulvlo.api.station.dto.StationDto;
import ca.ulaval.glo4003.sulvlo.domain.bike.Bike;
import ca.ulaval.glo4003.sulvlo.domain.bike.BikeAssembler;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StationAssembler {

  private final BikeAssembler bikeAssembler;

  public StationAssembler() {
    this.bikeAssembler = new BikeAssembler();
  }

  public StationDto assemble(Station station) {
    List<BikeDto> bikes = rechargeBikes(station);

    return assembleStationDto(station, bikes);
  }

  private StationDto assembleStationDto(Station station, List<BikeDto> bikes) {
    return new StationDto(station.getCurrentDateTime()
        .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)),
        station.getLocation(),
        station.getName(),
        station.getCode(),
        station.getCurrentCapacity(),
        sortEmptyLocationList(station.getEmptyBikeLocations()),
        bikes);
  }

  public StationDto createStationUnderMaintenanceDto(Station station) {
    return new StationDto(station.getCurrentDateTime()
        .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)),
        station.getLocation(),
        station.getName(),
        station.getCode(),
        station.getCurrentCapacity(),
        sortEmptyLocationList(station.getEmptyBikeLocations()),
        sortBikeList(station.getAvailableBikes()).stream()
            .map(bikeAssembler::createBikeDto).toList());
  }

  private List<BikeDto> rechargeBikes(Station station) {
    return sortBikeList(station.rechargeBikes()).stream()
        .map(bikeAssembler::createBikeDto).toList();
  }

  private List<Integer> sortEmptyLocationList(List<Integer> emptyLocationList) {
    Collections.sort(emptyLocationList);
    return emptyLocationList;
  }

  private List<Bike> sortBikeList(List<Bike> bikeList) {
    return bikeList.stream().sorted(Comparator.comparing(Bike::getLocation))
        .toList();
  }
}
