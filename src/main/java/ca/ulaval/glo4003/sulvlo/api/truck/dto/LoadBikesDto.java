package ca.ulaval.glo4003.sulvlo.api.truck.dto;

import java.util.List;

public record LoadBikesDto(String fromStationCode, List<String> bikesLocations) {

}
