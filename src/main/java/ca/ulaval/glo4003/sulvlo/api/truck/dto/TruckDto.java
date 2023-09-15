package ca.ulaval.glo4003.sulvlo.api.truck.dto;

import ca.ulaval.glo4003.sulvlo.api.bike.dto.BikeDto;
import java.util.List;

public record TruckDto(String id, List<BikeDto> bikes) {

}
