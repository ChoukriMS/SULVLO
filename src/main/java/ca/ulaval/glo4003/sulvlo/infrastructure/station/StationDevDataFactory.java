package ca.ulaval.glo4003.sulvlo.infrastructure.station;

import ca.ulaval.glo4003.sulvlo.domain.bike.Bike;
import ca.ulaval.glo4003.sulvlo.domain.bike.EnergyLevel;
import ca.ulaval.glo4003.sulvlo.domain.station.Station;
import ca.ulaval.glo4003.sulvlo.infrastructure.config.FileStationDto;
import ca.ulaval.glo4003.sulvlo.infrastructure.config.JsonFileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StationDevDataFactory {

  private final JsonFileReader jsonFileReader;

  public StationDevDataFactory(JsonFileReader jsonFileReader) {
    this.jsonFileReader = jsonFileReader;
  }

  public List<Station> createMockData() {
    Random rand = new Random();
    List<FileStationDto> fileStationDtoList = jsonFileReader.read(
        "src/main/java/ca/ulaval/glo4003/sulvlo/infrastructure/config/campus-stations-location.json");

    List<Station> stations = new ArrayList<>();
    fileStationDtoList.forEach(dto -> {
      Station station = new Station(dto.location(), dto.name(), dto.location().substring(0, 3),
          (int) (dto.capacity() * 0.8), dto.capacity(), new ArrayList<>(), new ArrayList<>());

      List<Bike> bikes = station.getAvailableBikes();
      for (int i = 1; i < station.getCurrentCapacity() + 1; i++) {
        BigDecimal randBigDecimal = BigDecimal.valueOf(rand.nextDouble());
        bikes.add(
            new Bike(i, new EnergyLevel(randBigDecimal.multiply(BigDecimal.valueOf(100.00)))));
      }
      List<Integer> emptyBikeLocations = station.getEmptyBikeLocations();
      for (int i = station.getCurrentCapacity() + 1; i < station.getMaxCapacity() + 1; i++) {
        emptyBikeLocations.add(i);
      }

      stations.add(station);
    });
    return stations;
  }
}
