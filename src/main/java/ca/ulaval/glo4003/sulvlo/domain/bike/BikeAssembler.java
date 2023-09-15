package ca.ulaval.glo4003.sulvlo.domain.bike;

import ca.ulaval.glo4003.sulvlo.api.bike.dto.BikeDto;

public class BikeAssembler {

  public BikeDto createBikeDto(Bike bike) {
    return new BikeDto(bike.getLocation(), bike.getEnergyLevel().toString(),
        bike.getStatus().toString());
  }

}
