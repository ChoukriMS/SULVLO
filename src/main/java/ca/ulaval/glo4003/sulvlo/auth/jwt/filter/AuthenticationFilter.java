package ca.ulaval.glo4003.sulvlo.auth.jwt.filter;

import ca.ulaval.glo4003.sulvlo.auth.jwt.AuthenticatedUserDetails;
import ca.ulaval.glo4003.sulvlo.auth.jwt.TokenAuthentificationDetails;
import ca.ulaval.glo4003.sulvlo.auth.jwt.TokenAuthentificationService;
import ca.ulaval.glo4003.sulvlo.auth.jwt.TokenBasedSecurityContext;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javax.enterprise.context.Dependent;


@Provider
@Dependent
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

  private TokenAuthentificationService tokenAuthentificationService;


  public AuthenticationFilter(TokenAuthentificationService tokenAuthentificationService) {
    this.tokenAuthentificationService = tokenAuthentificationService;
  }

  public AuthenticationFilter() {

  }

  @Override
  public void filter(ContainerRequestContext requestContext) {
    String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      String authenticationToken = authorizationHeader.substring(7);
      handleTokenBasedAuthentication(authenticationToken, requestContext);
    }
  }

  private void handleTokenBasedAuthentication(String authenticationToken,
      ContainerRequestContext requestContext) {

    Claims claims = tokenAuthentificationService.getClaims(authenticationToken);
    String role = claims.get("role").toString();
    String name = claims.get("name").toString();
    String email = claims.get("email").toString();
    String idul = claims.getSubject();

    AuthenticatedUserDetails authenticatedUserDetails = new AuthenticatedUserDetails(idul, name,
        email, role);

    TokenAuthentificationDetails tokenAuthentificationDetails = tokenAuthentificationService
        .parseJwtTokenDetails(claims.getId(), name, email, role, claims.getSubject(),
            ZonedDateTime.ofInstant(claims.getIssuedAt().toInstant(), ZoneId.systemDefault()),
            ZonedDateTime.ofInstant(claims.getExpiration().toInstant(), ZoneId.systemDefault()));

    boolean isSecure = requestContext.getSecurityContext().isSecure();
    SecurityContext securityContext = new TokenBasedSecurityContext(authenticatedUserDetails,
        tokenAuthentificationDetails, isSecure);
    requestContext.setSecurityContext(securityContext);
  }

}