package ca.ulaval.glo4003.sulvlo.auth.jwt;

import java.time.ZonedDateTime;

public final class TokenAuthentificationDetails {

  private final String id;
  private final String name;
  private final String email;
  private final String subject;
  private final ZonedDateTime issuedDate;
  private final ZonedDateTime expirationDate;
  private final String role;

  private TokenAuthentificationDetails(String id, String name, String email, String role,
      String subject,
      ZonedDateTime issuedDate,
      ZonedDateTime expirationDate) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.role = role;
    this.subject = subject;
    this.issuedDate = issuedDate;
    this.expirationDate = expirationDate;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getSubject() {
    return subject;
  }

  public ZonedDateTime getIssuedDate() {
    return issuedDate;
  }

  public ZonedDateTime getExpirationDate() {
    return expirationDate;
  }

  public String getRole() {
    return role;
  }

  public static class Builder {

    private String id;
    private String name;
    private String role;
    private ZonedDateTime issuedDate;
    private ZonedDateTime expirationDate;
    private String email;
    private String subject;

    public Builder withId(String id) {
      this.id = id;
      return this;
    }

    public Builder withName(String username) {
      this.name = username;
      return this;
    }

    public Builder withRole(String role) {
      this.role = role;
      return this;
    }

    public Builder withEmail(String email) {
      this.email = email;
      return this;
    }

    public Builder withSubject(String subject) {
      this.subject = subject;
      return this;
    }

    public Builder withIssuedDate(ZonedDateTime issuedDate) {
      this.issuedDate = issuedDate;
      return this;
    }

    public Builder withExpirationDate(ZonedDateTime expirationDate) {
      this.expirationDate = expirationDate;
      return this;
    }

    public TokenAuthentificationDetails build() {
      return new TokenAuthentificationDetails(id, name, email, role, subject, issuedDate,
          expirationDate);
    }
  }

}
