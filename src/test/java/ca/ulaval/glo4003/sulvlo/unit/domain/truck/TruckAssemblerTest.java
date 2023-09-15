package ca.ulaval.glo4003.sulvlo.unit.domain.truck;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.sulvlo.api.bike.dto.BikeDto;
import ca.ulaval.glo4003.sulvlo.api.truck.dto.TruckDto;
import ca.ulaval.glo4003.sulvlo.domain.bike.Bike;
import ca.ulaval.glo4003.sulvlo.domain.bike.BikeStatus;
import ca.ulaval.glo4003.sulvlo.domain.bike.EnergyLevel;
import ca.ulaval.glo4003.sulvlo.domain.truck.Truck;
import ca.ulaval.glo4003.sulvlo.domain.truck.TruckAssembler;
import ca.ulaval.glo4003.sulvlo.domain.truck.TruckId;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TruckAssemblerTest {

  private static final String TRUCK_ID_VALUE = "ae06444f-9aa7-4406-a52f-abe8c2afcb56";
  private static final TruckId TRUCK_ID = TruckId.fromString(TRUCK_ID_VALUE);
  private static final int ANY_BIKE_LOCATION = 2;
  private static final EnergyLevel ANY_ENERGY_LEVEL = new EnergyLevel(BigDecimal.valueOf(45));
  private static final BikeStatus ANY_BIKE_STATUS = BikeStatus.IN_TRANSIT;
  private static final BikeDto BIKE_DTO = new BikeDto(ANY_BIKE_LOCATION,
      ANY_ENERGY_LEVEL.toString(), ANY_BIKE_STATUS.toString());
  private static final TruckDto TRUCK_DTO = new TruckDto(TRUCK_ID_VALUE, List.of(BIKE_DTO));
  private final TruckAssembler truckAssembler = new TruckAssembler();
  @Mock
  private Bike bike;
  @Mock
  private Truck truck;

  @Test
  void givenATruck_whenCreateTruckDto_thenATruckDtoIsReturned() {
    when(bike.getLocation()).thenReturn(ANY_BIKE_LOCATION);
    when(bike.getEnergyLevel()).thenReturn(ANY_ENERGY_LEVEL);
    when(bike.getStatus()).thenReturn(ANY_BIKE_STATUS);
    when(truck.getTruckId()).thenReturn(TRUCK_ID);
    when(truck.getLoadedBikes()).thenReturn(List.of(bike));

    TruckDto actualTruckDto = truckAssembler.createTruckDto(truck);

    assertEquals(TRUCK_DTO, actualTruckDto);
  }

}