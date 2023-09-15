package ca.ulaval.glo4003.sulvlo.api.mapper;

import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotAuthorizedExceptionMapper implements ExceptionMapper<NotAuthorizedException> {

  @Override
  public Response toResponse(NotAuthorizedException e) {
    return Response.status(e.getResponse().getStatus())
        .entity(new ErrorResponse(e.getResponse().getStatus(), e.getMessage()).toString()).build();
  }
}
