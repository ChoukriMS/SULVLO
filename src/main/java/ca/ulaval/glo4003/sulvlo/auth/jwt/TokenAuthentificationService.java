package ca.ulaval.glo4003.sulvlo.auth.jwt;

import ca.ulaval.glo4003.sulvlo.auth.jwt.exceptions.InvalidAuthenticationTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class TokenAuthentificationService {

  private final TokenAuthentificationSettings settings;

  public TokenAuthentificationService() {
    this.settings = new TokenAuthentificationSettings();
  }


  public String createJwtToken(TokenAuthentificationDetails tokenAuthentificationDetails) {
    return Jwts.builder()
        .claim(settings.getUserNameClaimName(), tokenAuthentificationDetails.getName())
        .claim(settings.getEmailClaimName(), tokenAuthentificationDetails.getEmail())
        .claim(settings.getRolesClaimName(), tokenAuthentificationDetails.getRole())
        .setSubject(tokenAuthentificationDetails.getSubject()).setId(generateTokenIdentifier())
        .setIssuer(settings.getIssuer())
        .setIssuedAt(Date.from(tokenAuthentificationDetails.getIssuedDate().toInstant()))
        .setExpiration(Date.from(tokenAuthentificationDetails.getExpirationDate().toInstant()))
        .signWith(SignatureAlgorithm.HS512,
            Base64.getEncoder().encode(settings.getSecret().getBytes())).compact();
  }

  public Claims getClaims(String token) {
    try {
      return Jwts.parser()
          .setSigningKey(Base64.getEncoder().encode(settings.getSecret().getBytes()))
          .parseClaimsJws(token).getBody();
    } catch (Exception e) {
      throw new InvalidAuthenticationTokenException();
    }
  }

  public TokenAuthentificationDetails parseJwtTokenDetails(String id, String name, String email,
      String role, String subject, ZonedDateTime issuedDate, ZonedDateTime expirationDate) {
    return new TokenAuthentificationDetails.Builder().withId(id).withName(name).withEmail(email)
        .withRole(role).withSubject(subject).withIssuedDate(issuedDate)
        .withExpirationDate(expirationDate).build();
  }

  public TokenAuthentificationDetails createJwtTokenDetails(String name, String email, String idul,
      String role) {
    String id = generateTokenIdentifier();
    ZonedDateTime issuedDate = ZonedDateTime.now();
    ZonedDateTime expirationDate = calculateExpirationDate(issuedDate);
    return new TokenAuthentificationDetails.Builder().withId(id).withName(name).withEmail(email)
        .withRole(role).withSubject(idul).withIssuedDate(issuedDate)
        .withExpirationDate(expirationDate).build();
  }

  private String generateTokenIdentifier() {
    return UUID.randomUUID().toString();
  }

  private ZonedDateTime calculateExpirationDate(ZonedDateTime issuedDate) {
    return issuedDate.plusSeconds(settings.getValidFor());
  }

}

