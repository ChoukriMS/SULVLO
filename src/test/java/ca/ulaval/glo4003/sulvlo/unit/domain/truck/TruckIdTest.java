package ca.ulaval.glo4003.sulvlo.unit.domain.truck;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.sulvlo.domain.truck.TruckId;
import ca.ulaval.glo4003.sulvlo.domain.truck.exception.InvalidTruckIdException;
import org.junit.jupiter.api.Test;

class TruckIdTest {

  private static final String INVALID_STRING = "123-abc";
  private static final String VALID_STRING = "af9466c6-9dda-4fd1-98fd-f189ccc7143f";

  @Test
  void whenCreatingATruckIdFromInvalidString_thenInvalidTruckIdExceptionShouldBeThrown() {
    assertThrows(InvalidTruckIdException.class, () -> {
      TruckId.fromString(INVALID_STRING);
    });
  }

  @Test
  void whenCreatingATruckIdFromValidString_thenATruckIdShouldBeCreated() {
    assertEquals(VALID_STRING, TruckId.fromString(VALID_STRING).getValue());
  }

}