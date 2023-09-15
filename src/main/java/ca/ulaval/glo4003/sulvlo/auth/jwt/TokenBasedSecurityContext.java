package ca.ulaval.glo4003.sulvlo.auth.jwt;

import jakarta.ws.rs.core.SecurityContext;
import java.security.Principal;


public class TokenBasedSecurityContext implements SecurityContext {

  private final AuthenticatedUserDetails authenticatedUserDetails;
  private final TokenAuthentificationDetails tokenAuthentificationDetails;
  private final boolean secure;

  public TokenBasedSecurityContext(AuthenticatedUserDetails authenticatedUserDetails,
      TokenAuthentificationDetails authenticationTokenDetails, boolean secure) {
    this.authenticatedUserDetails = authenticatedUserDetails;
    this.tokenAuthentificationDetails = authenticationTokenDetails;
    this.secure = secure;
  }

  @Override
  public Principal getUserPrincipal() {
    return authenticatedUserDetails;
  }

  @Override
  public boolean isUserInRole(String role) {
    return authenticatedUserDetails.getRoles().contains(role);
  }

  @Override
  public boolean isSecure() {
    return secure;
  }

  @Override
  public String getAuthenticationScheme() {
    return "Bearer";
  }

  public TokenAuthentificationDetails getTokenAuthentificationDetails() {
    return tokenAuthentificationDetails;
  }

}