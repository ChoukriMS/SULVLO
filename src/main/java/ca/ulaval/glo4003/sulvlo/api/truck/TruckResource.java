package ca.ulaval.glo4003.sulvlo.api.truck;

import ca.ulaval.glo4003.sulvlo.api.mapper.SuccessfulResponse;
import ca.ulaval.glo4003.sulvlo.api.truck.dto.LoadBikesDto;
import ca.ulaval.glo4003.sulvlo.api.truck.dto.TruckDto;
import ca.ulaval.glo4003.sulvlo.api.truck.dto.UnloadBikesDto;
import ca.ulaval.glo4003.sulvlo.api.truck.validation.TruckRequestsValidator;
import ca.ulaval.glo4003.sulvlo.application.service.truck.TruckService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("api/trucks")
@Produces(MediaType.APPLICATION_JSON)
public class TruckResource {

  private static final String SUCCESSFULLY_LOAD_BIKES_MSG = "The bikes were successfully loaded.";
  private static final String SUCCESSFULLY_UNLOAD_BIKES_MSG = "The bikes were successfully unloaded.";

  private TruckService truckService;
  private TruckRequestsValidator truckRequestsValidator;

  public TruckResource() {
  }

  public TruckResource(TruckService truckService, TruckRequestsValidator truckRequestsValidator) {
    this.truckService = truckService;
    this.truckRequestsValidator = truckRequestsValidator;
  }

  @GET
  @RolesAllowed({"TECHNICIEN"})
  public List<TruckDto> listTrucks() {
    return truckService.findAllTrucks();
  }

  @POST
  @Path("{truck-id}:load")
  @RolesAllowed({"TECHNICIEN"})
  public Response loadBikes(@PathParam("truck-id") String truckId, LoadBikesDto loadBikesDto) {
    truckRequestsValidator.validateLoadBikesRequest(truckId, loadBikesDto);

    truckService.loadBikes(truckId, loadBikesDto.fromStationCode(), loadBikesDto.bikesLocations());

    return Response.status(Response.Status.OK)
        .entity(new SuccessfulResponse(SUCCESSFULLY_LOAD_BIKES_MSG).toString()).build();
  }

  @POST
  @Path("{truck-id}:unload")
  @RolesAllowed({"TECHNICIEN"})
  public Response unloadBikes(@PathParam("truck-id") String truckId,
      UnloadBikesDto unloadBikesDto) {
    truckRequestsValidator.validateUnloadBikesRequest(truckId, unloadBikesDto);

    truckService.unload(truckId, unloadBikesDto.unloadBikeDataList());

    return Response.status(Response.Status.OK)
        .entity(new SuccessfulResponse(SUCCESSFULLY_UNLOAD_BIKES_MSG).toString()).build();
  }

}
