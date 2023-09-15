package ca.ulaval.glo4003.sulvlo.auth.jwt;

import java.util.ResourceBundle;
import javax.enterprise.context.Dependent;

@Dependent
public class TokenAuthentificationSettings {

  private Long validFor;
  private String secret;
  private String issuer;
  private String rolesClaimName;
  private String userNameClaimName;
  private String emailClaimName;

  public TokenAuthentificationSettings() {
    initTokenSettings();
  }


  public void initTokenSettings() {
    ResourceBundle rb = ResourceBundle.getBundle("application");
    validFor = Long.parseLong(rb.getString("authentication.jwt.validFor"));
    secret = rb.getString("authentication.jwt.secret");
    issuer = rb.getString("authentication.jwt.issuer");
    userNameClaimName = rb.getString("authentication.jwt.claimNames.name");
    emailClaimName = rb.getString("authentication.jwt.claimNames.email");
    rolesClaimName = rb.getString("authentication.jwt.claimNames.roles");
  }

  public Long getValidFor() {
    return validFor;
  }

  public String getSecret() {
    return secret;
  }

  public String getIssuer() {
    return issuer;
  }

  public String getUserNameClaimName() {
    return userNameClaimName;
  }

  public String getEmailClaimName() {
    return emailClaimName;
  }

  public String getRolesClaimName() {
    return rolesClaimName;
  }

}
