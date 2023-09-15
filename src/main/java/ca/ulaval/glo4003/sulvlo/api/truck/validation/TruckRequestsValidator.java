package ca.ulaval.glo4003.sulvlo.api.truck.validation;

import ca.ulaval.glo4003.sulvlo.api.truck.dto.LoadBikesDto;
import ca.ulaval.glo4003.sulvlo.api.truck.dto.UnloadBikesDto;
import ca.ulaval.glo4003.sulvlo.api.truck.dto.UnloadBikesDto.UnloadBikeData;
import jakarta.ws.rs.BadRequestException;
import java.util.List;

public class TruckRequestsValidator {

  private static final String INVALID_BIKES_LOCATIONS_MSG = "Invalid bikesLocations";
  private static final int NUMBER_OF_ELEMENTS = 2;

  private final CommonTruckRequestsValidators commonTruckRequestsValidators;

  public TruckRequestsValidator(CommonTruckRequestsValidators commonTruckRequestsValidators) {
    this.commonTruckRequestsValidators = commonTruckRequestsValidators;
  }

  public void validateLoadBikesRequest(String truckId, LoadBikesDto loadBikesDto) {
    commonTruckRequestsValidators.validateTruckId(truckId);

    commonTruckRequestsValidators.commonStationRequestsValidators()
        .validateStationCode(loadBikesDto.fromStationCode());

    checkBikesLocationsNotEmpty(loadBikesDto.bikesLocations());

    loadBikesDto.bikesLocations().forEach(
        commonTruckRequestsValidators.commonStationRequestsValidators()::validateBikeLocation);
  }

  private void checkBikesLocationsNotEmpty(List<String> bikesLocations) {
    if (bikesLocations.isEmpty()) {
      throw new BadRequestException(INVALID_BIKES_LOCATIONS_MSG);
    }
  }

  public void validateUnloadBikesRequest(String truckId, UnloadBikesDto unloadBikesDto) {
    commonTruckRequestsValidators.validateTruckId(truckId);

    validateStationsCodes(unloadBikesDto.unloadBikeDataList());

    ValidateBikesLocations(unloadBikesDto.unloadBikeDataList());
  }

  private void validateStationsCodes(List<UnloadBikeData> unloadBikeDataList) {
    unloadBikeDataList.forEach(
        unloadBikeData -> commonTruckRequestsValidators.commonStationRequestsValidators()
            .validateStationCode(
                unloadBikeData.toStationCode()));
  }

  private void ValidateBikesLocations(List<UnloadBikeData> unloadBikeDataList) {
    unloadBikeDataList.forEach(unloadBikeData -> {
      List<String> bikesLocations = unloadBikeData.bikesLocations();
      if (bikesLocations.size() != NUMBER_OF_ELEMENTS) {
        throw new BadRequestException(INVALID_BIKES_LOCATIONS_MSG);
      }
      bikesLocations.forEach(
          commonTruckRequestsValidators.commonStationRequestsValidators()::validateBikeLocation);
    });
  }

}
