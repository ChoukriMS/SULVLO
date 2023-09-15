package ca.ulaval.glo4003.sulvlo.api.mapper;

import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;


@Provider
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {

  @Override
  public Response toResponse(ForbiddenException e) {
    return Response.status(Status.FORBIDDEN)
        .entity(new ErrorResponse(e.getResponse().getStatus(), e.getMessage()).toString()).build();
  }

}
