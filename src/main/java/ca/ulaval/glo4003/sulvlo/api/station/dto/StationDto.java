package ca.ulaval.glo4003.sulvlo.api.station.dto;

import ca.ulaval.glo4003.sulvlo.api.bike.dto.BikeDto;
import com.google.common.base.Objects;
import java.util.List;

public record StationDto(String currentDate, String location, String name, String code,
                         int currentCapacity, List<Integer> emptyBikesLocations,
                         List<BikeDto> bikes) {

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StationDto that = (StationDto) o;
    return currentCapacity == that.currentCapacity
        && Objects.equal(location, that.location)
        && Objects.equal(name, that.name)
        && Objects.equal(code, that.code)
        && Objects.equal(emptyBikesLocations, that.emptyBikesLocations)
        && Objects.equal(bikes, that.bikes);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(currentDate, location, name, code, currentCapacity, emptyBikesLocations,
        bikes);
  }
}
