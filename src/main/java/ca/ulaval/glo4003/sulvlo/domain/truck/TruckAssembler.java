package ca.ulaval.glo4003.sulvlo.domain.truck;

import ca.ulaval.glo4003.sulvlo.api.truck.dto.TruckDto;
import ca.ulaval.glo4003.sulvlo.domain.bike.BikeAssembler;

public class TruckAssembler {

  private final BikeAssembler bikeAssembler;

  public TruckAssembler() {
    this.bikeAssembler = new BikeAssembler();
  }

  public TruckDto createTruckDto(Truck truck) {
    return new TruckDto(truck.getTruckId().getValue(),
        truck.getLoadedBikes().stream().map(bike -> bikeAssembler.createBikeDto(bike)).toList());
  }
}
