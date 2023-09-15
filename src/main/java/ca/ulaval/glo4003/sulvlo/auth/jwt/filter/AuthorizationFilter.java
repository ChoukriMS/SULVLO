package ca.ulaval.glo4003.sulvlo.auth.jwt.filter;


import ca.ulaval.glo4003.sulvlo.auth.jwt.exceptions.PermissionDeniedException;
import ca.ulaval.glo4003.sulvlo.auth.jwt.exceptions.UnauthentificatedUserException;
import jakarta.annotation.Priority;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.ext.Provider;
import java.lang.reflect.Method;
import javax.enterprise.context.Dependent;


@Provider
@Dependent
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {

  @Context
  private ResourceInfo resourceInfo;


  @Override
  public void filter(final ContainerRequestContext requestContext) {
    Method method = resourceInfo.getResourceMethod();

    if (method.isAnnotationPresent(DenyAll.class)) {
      throw new PermissionDeniedException();
    }

    RolesAllowed rolesAllowed = method.getAnnotation(RolesAllowed.class);
    if (rolesAllowed != null) {
      performAuthorization(rolesAllowed.value(), requestContext);
      return;
    }

    if (method.isAnnotationPresent(PermitAll.class)) {
      return;
    }

    rolesAllowed = resourceInfo.getResourceClass().getAnnotation(RolesAllowed.class);
    if (rolesAllowed != null) {
      performAuthorization(rolesAllowed.value(), requestContext);
    }

    if (resourceInfo.getResourceClass().isAnnotationPresent(PermitAll.class)) {
      return;
    }

    if (!isAuthenticated(requestContext)) {
      throw new UnauthentificatedUserException().throwException();
    }

  }


  private void performAuthorization(String[] rolesAllowed, ContainerRequestContext requestContext) {

    if (rolesAllowed.length > 0 && !isAuthenticated(requestContext)) {
      throw new PermissionDeniedException();
    }

    for (final String role : rolesAllowed) {
      if (requestContext.getSecurityContext().isUserInRole(role)) {
        return;
      }
    }

    throw new PermissionDeniedException();
  }

  private boolean isAuthenticated(final ContainerRequestContext requestContext) {
    return requestContext.getSecurityContext().getUserPrincipal() != null;
  }

}