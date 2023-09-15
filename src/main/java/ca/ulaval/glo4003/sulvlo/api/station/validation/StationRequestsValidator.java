package ca.ulaval.glo4003.sulvlo.api.station.validation;

public class StationRequestsValidator {

  private final CommonStationRequestsValidators commonStationRequestsValidators;

  public StationRequestsValidator(CommonStationRequestsValidators commonStationRequestsValidators) {
    this.commonStationRequestsValidators = commonStationRequestsValidators;
  }

  public void validateUnlockBikeRequest(String userCode, String stationCode, String bikeLocation) {
    commonStationRequestsValidators.validateUserCode(userCode);
    commonStationRequestsValidators.validateStationCode(stationCode);
    commonStationRequestsValidators.validateBikeLocation(bikeLocation);
  }

  public void validateReturnBikeRequest(String returnStationCode, String returnBikeLocation) {
    commonStationRequestsValidators.validateStationCode(returnStationCode);
    commonStationRequestsValidators.validateBikeLocation(returnBikeLocation);
  }

  public void validateStartMaintenanceRequest(String stationCode) {
    commonStationRequestsValidators.validateStationCode(stationCode);
  }

  public void validateResumeServiceRequest(String stationCode) {
    commonStationRequestsValidators.validateStationCode(stationCode);
  }

}
