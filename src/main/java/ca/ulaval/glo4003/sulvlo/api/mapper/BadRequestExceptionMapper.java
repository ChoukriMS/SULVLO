package ca.ulaval.glo4003.sulvlo.api.mapper;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {

  @Override
  public Response toResponse(BadRequestException e) {
    return Response.status(Response.Status.BAD_REQUEST)
        .entity(new ErrorResponse(e.getResponse().getStatus(), e.getMessage()).toString()).build();
  }
}
