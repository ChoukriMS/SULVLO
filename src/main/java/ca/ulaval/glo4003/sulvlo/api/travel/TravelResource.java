package ca.ulaval.glo4003.sulvlo.api.travel;

import ca.ulaval.glo4003.sulvlo.application.service.travel.TravelService;
import ca.ulaval.glo4003.sulvlo.auth.jwt.AuthenticatedUserDetails;
import ca.ulaval.glo4003.sulvlo.infrastructure.travel.exception.InvalidTravelIdException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("/api/travel")
@Produces(MediaType.APPLICATION_JSON)
public class TravelResource {

  private TravelService travelService;

  public TravelResource() {
  }

  public TravelResource(TravelService travelService) {
    this.travelService = travelService;
  }

  @GET
  public Response getAllByMonth(@HeaderParam("month") String month,
      @Context SecurityContext securityContext) {

    AuthenticatedUserDetails authenticatedUserDetails =
        (AuthenticatedUserDetails) securityContext.getUserPrincipal();

    return Response.status(Response.Status.OK)
        .entity(travelService.getAllFilteredByMonth(month, authenticatedUserDetails.getIdul()))
        .build();
  }

  @GET
  @Path("{id}")
  public Response getById(@PathParam("id") String id) throws InvalidTravelIdException {
    return Response.status(Response.Status.OK).entity(travelService.getById(id)).build();
  }

  @GET
  @Path("/summary")
  public Response getHistorySummary(@Context SecurityContext securityContext) {

    AuthenticatedUserDetails authenticatedUserDetails =
        (AuthenticatedUserDetails) securityContext.getUserPrincipal();

    return Response.status(Response.Status.OK).entity(travelService.getHistorySummary(
            authenticatedUserDetails.getIdul()))
        .build();
  }
}

