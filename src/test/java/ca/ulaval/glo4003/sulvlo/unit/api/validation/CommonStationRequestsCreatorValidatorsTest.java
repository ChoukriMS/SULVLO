package ca.ulaval.glo4003.sulvlo.unit.api.validation;

import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.sulvlo.api.station.validation.CommonStationRequestsValidators;
import jakarta.ws.rs.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CommonStationRequestsCreatorValidatorsTest {

  private static final String ANY_INVALID_USER_CODE = "12BB**_";
  private static final String ANY_INVALID_STATION_CODE = "@BN12";
  private static final String ANY_INVALID_BIKE_LOCATION = "13ABC";

  private CommonStationRequestsValidators commonStationRequestsValidators;

  @BeforeEach
  public void setup() {
    commonStationRequestsValidators =
        Mockito.mock(CommonStationRequestsValidators.class, Mockito.CALLS_REAL_METHODS);
  }

  @Test
  public void givenAnyInvalidUserCode_whenValidateUserCode_thenThrowBadRequestException() {
    assertThrows(BadRequestException.class, () -> {
      commonStationRequestsValidators.validateUserCode(ANY_INVALID_USER_CODE);
    });
  }

  @Test
  public void givenAnyInvalidStationCode_whenValidateStationCode_thenThrowBadRequestException() {
    assertThrows(BadRequestException.class, () -> {
      commonStationRequestsValidators.validateUserCode(ANY_INVALID_STATION_CODE);
    });
  }

  @Test
  public void givenAnyInvalidBikeLocation_whenValidateBikeLocation_thenThrowBadRequestException() {
    assertThrows(BadRequestException.class, () -> {
      commonStationRequestsValidators.validateBikeLocation(ANY_INVALID_BIKE_LOCATION);
    });
  }


}