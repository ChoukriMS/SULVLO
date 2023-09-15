package ca.ulaval.glo4003.sulvlo.api.mapper;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

  @Override
  public Response toResponse(NotFoundException e) {
    return Response.status(Response.Status.NOT_FOUND)
        .entity(new ErrorResponse(e.getResponse().getStatus(), e.getMessage()).toString()).build();
  }
}