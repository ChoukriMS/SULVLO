package ca.ulaval.glo4003.sulvlo.auth.jwt;

import java.security.Principal;

public final class AuthenticatedUserDetails implements Principal {

  private final String name;
  private final String role;
  private final String idul;
  private final String email;

  public AuthenticatedUserDetails(String idul, String name, String email, String role) {
    this.name = name;
    this.role = role;
    this.idul = idul;
    this.email = email;
  }

  public String getIdul() {
    return idul;
  }

  public String getEmail() {
    return email;
  }

  public String getRoles() {
    return role;
  }

  @Override
  public String getName() {
    return name;
  }
}