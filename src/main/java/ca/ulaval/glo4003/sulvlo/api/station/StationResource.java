package ca.ulaval.glo4003.sulvlo.api.station;


import ca.ulaval.glo4003.sulvlo.api.mapper.SuccessfulResponse;
import ca.ulaval.glo4003.sulvlo.api.station.dto.StationDto;
import ca.ulaval.glo4003.sulvlo.api.station.validation.StationRequestsValidator;
import ca.ulaval.glo4003.sulvlo.application.service.station.StationService;
import ca.ulaval.glo4003.sulvlo.auth.jwt.AuthenticatedUserDetails;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import java.util.List;

@Path("api/stations")
@Produces(MediaType.APPLICATION_JSON)
public class StationResource {

  private static final String SUCCESSFULLY_UNLOCKED_MSG = "The bike was successfully unlocked.";
  private static final String SUCCESSFULLY_RETURNED_MSG = "The bike was successfully returned.";
  private static final String SUCCESSFULLY_START_MAINTENANCE_MSG = "The station is now under maintenance.";
  private static final String SUCCESSFULLY_RESUME_SERVICE_MSG = "The station is now available.";
  private static final String UNIQUE_CODE_MESSAGE = "Your unique code was sent to your email!";

  private StationService stationService;
  private StationRequestsValidator stationRequestsValidator;


  public StationResource() {
  }

  public StationResource(StationService stationService,
      StationRequestsValidator stationRequestsValidator) {
    this.stationService = stationService;
    this.stationRequestsValidator = stationRequestsValidator;
  }

  @GET
  @Path("available")
  @PermitAll
  public List<StationDto> listAvailableStations() {
    return stationService.findAllAvailableStations();
  }

  @GET
  @Path("under-maintenance")
  @RolesAllowed({"TECHNICIEN"})
  public List<StationDto> listUnderMaintenanceStations() {
    return stationService.findAllUnderMaintenanceStations();
  }

  @POST
  @Path("unique-code")
  public Response uniqueCode(@Context SecurityContext securityContext) {
    AuthenticatedUserDetails authenticatedUserDetails = (AuthenticatedUserDetails) securityContext.getUserPrincipal();

    stationService.createUniqueCode(authenticatedUserDetails.getEmail());

    return Response.status(Response.Status.OK)
        .entity(new SuccessfulResponse(UNIQUE_CODE_MESSAGE).toString()).build();
  }

  @POST
  @Path("available/{station-code}/bikes/{bike-location}:unlock")
  public Response unlockBike(@Context SecurityContext securityContext,
      @HeaderParam("user-code") String userCode, @PathParam("station-code") String stationCode,
      @PathParam("bike-location") String bikeLocation) {
    stationRequestsValidator.validateUnlockBikeRequest(userCode, stationCode, bikeLocation);
    AuthenticatedUserDetails authenticatedUserDetails = (AuthenticatedUserDetails) securityContext.getUserPrincipal();

    stationService.unlockBike(userCode, stationCode, bikeLocation,
        authenticatedUserDetails.getIdul());

    return Response.status(Response.Status.OK)
        .entity(new SuccessfulResponse(SUCCESSFULLY_UNLOCKED_MSG).toString()).build();
  }

  @POST
  @Path("available/{return-station-code}/bikes/{return-bike-location}:return")
  public Response returnBike(@Context SecurityContext securityContext,
      @PathParam("return-station-code") String returnStationCode,
      @PathParam("return-bike-location") String returnBikeLocation) {

    stationRequestsValidator.validateReturnBikeRequest(returnStationCode, returnBikeLocation);
    AuthenticatedUserDetails authenticatedUserDetails = (AuthenticatedUserDetails) securityContext.getUserPrincipal();

    stationService.returnBike(returnStationCode, returnBikeLocation,
        authenticatedUserDetails.getIdul());

    return Response.status(Response.Status.OK)
        .entity(new SuccessfulResponse(SUCCESSFULLY_RETURNED_MSG).toString()).build();
  }

  @POST
  @Path("under-maintenance/{station-code}:start-maintenance")
  @RolesAllowed({"TECHNICIEN"})
  public Response startMaintenance(@PathParam("station-code") String stationCode) {
    stationRequestsValidator.validateStartMaintenanceRequest(stationCode);

    stationService.startMaintenance(stationCode);

    return Response.status(Response.Status.OK)
        .entity(new SuccessfulResponse(SUCCESSFULLY_START_MAINTENANCE_MSG).toString()).build();
  }

  @POST
  @Path("under-maintenance/{station-code}:end-maintenance")
  @RolesAllowed({"TECHNICIEN"})
  public Response endMaintenance(@PathParam("station-code") String stationCode) {
    stationRequestsValidator.validateResumeServiceRequest(stationCode);

    stationService.endMaintenance(stationCode);

    return Response.status(Response.Status.OK)
        .entity(new SuccessfulResponse(SUCCESSFULLY_RESUME_SERVICE_MSG).toString()).build();
  }

}
