package ca.ulaval.glo4003.sulvlo.api.mapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UnsupportedOperationExceptionMapper
    implements ExceptionMapper<UnsupportedOperationException> {

  @Override
  public Response toResponse(UnsupportedOperationException e) {
    return Response.status(Response.Status.NOT_IMPLEMENTED).entity(
        new ErrorResponse(Response.Status.NOT_IMPLEMENTED.getStatusCode(),
            e.getMessage()).toString()).build();
  }
}
