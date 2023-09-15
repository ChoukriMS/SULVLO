package ca.ulaval.glo4003.sulvlo.api.truck.dto;

import java.util.List;

public record UnloadBikesDto(List<UnloadBikeData> unloadBikeDataList) {

  public record UnloadBikeData(String toStationCode, List<String> bikesLocations) {

  }

}
