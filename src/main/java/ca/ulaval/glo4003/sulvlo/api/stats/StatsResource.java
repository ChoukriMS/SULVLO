package ca.ulaval.glo4003.sulvlo.api.stats;

import ca.ulaval.glo4003.sulvlo.application.service.stats.StatsService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("api/stats")
@Produces(MediaType.APPLICATION_JSON)
public class StatsResource {

  private StatsService statsService;

  public StatsResource() {
  }

  public StatsResource(StatsService statsService) {
    this.statsService = statsService;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("stations-available-bikes")
  @RolesAllowed({"MANAGER"})
  public Response listAllBikesAvailabilities() {
    statsService.verifyCurrentBikesAvailabilities();

    return Response.status(Response.Status.OK)
        .entity(statsService.findAllBikesAvailabilities()).build();
  }
}
