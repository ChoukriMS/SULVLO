package ca.ulaval.glo4003.sulvlo.unit.domain.station.bike;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.sulvlo.api.bike.dto.BikeDto;
import ca.ulaval.glo4003.sulvlo.domain.bike.Bike;
import ca.ulaval.glo4003.sulvlo.domain.bike.BikeAssembler;
import ca.ulaval.glo4003.sulvlo.domain.bike.BikeStatus;
import ca.ulaval.glo4003.sulvlo.domain.bike.EnergyLevel;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class BikeAssemblerTest {

  private final static BikeDto BIKE_DTO = new BikeDto(1, "56.000 %",
      BikeStatus.AVAILABLE.toString());

  private BikeAssembler bikeAssembler;

  @Test
  public void givenBike_whenCreateBikeDto_thenReturnBikeDto() {
    bikeAssembler = new BikeAssembler();
    Bike bike = new Bike(1, new EnergyLevel(new BigDecimal(56)));

    assertEquals(BIKE_DTO, bikeAssembler.createBikeDto(bike));
  }

}