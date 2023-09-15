package ca.ulaval.glo4003.sulvlo.api.mapper;

import ca.ulaval.glo4003.sulvlo.infrastructure.exception.ConflictException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ConflictExceptionMapper implements ExceptionMapper<ConflictException> {

  @Override
  public Response toResponse(ConflictException e) {
    return Response.status(Response.Status.CONFLICT)
        .entity(new ErrorResponse(e.getResponse().getStatus(), e.getMessage()).toString()).build();
  }
}
