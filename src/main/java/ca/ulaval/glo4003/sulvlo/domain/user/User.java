package ca.ulaval.glo4003.sulvlo.domain.user;

import ca.ulaval.glo4003.sulvlo.domain.user.exception.InvalidActivationTokenException;
import ca.ulaval.glo4003.sulvlo.domain.user.exception.UserConnectionException;
import ca.ulaval.glo4003.sulvlo.domain.user.information.Gender;
import ca.ulaval.glo4003.sulvlo.domain.user.information.UserId;
import ca.ulaval.glo4003.sulvlo.domain.user.information.UserInformation;
import java.util.UUID;

public class User {

  private final UserInformation userInformation;
  private final String activationToken;
  private final UserId userId;
  private final UserType userType;
  private boolean accountVerified;
  private boolean isBlocked;

  public User(UserInformation userInformation, UserType userType, UserId userId) {
    this.userInformation = userInformation;
    this.userType = userType;
    this.userId = userId;
    this.activationToken = createActivationToken(userInformation.userId());
    this.accountVerified = false;
    this.isBlocked = true;
  }

  public void checkPassword(int hashedPassword) {
    if (this.userInformation.password() != hashedPassword) {
      throw new UserConnectionException();
    }
  }

  public UUID getId() {
    return this.userInformation.userId();
  }

  public String getIdul() {
    return this.userId.idul();
  }

  public String getName() {
    return this.userInformation.name();
  }

  public String getEmail() {
    return this.userInformation.email();
  }

  public Gender getGender() {
    return this.userInformation.gender();
  }

  public boolean isBlocked() {
    return this.isBlocked || !this.accountVerified;
  }

  public void blockAccount() {
    this.isBlocked = true;
  }

  public void unblockAccount() {
    this.isBlocked = false;
  }

  public void validateActivationToken(String token) {
    if (this.activationToken.equals(token)) {
      this.accountVerified = true;
      return;
    }
    throw new InvalidActivationTokenException();
  }

  public String getActivationToken() {
    return this.activationToken;
  }

  public boolean isAccountVerified() {
    return accountVerified;
  }

  public void setAccountVerified(boolean accountVerified) {
    this.accountVerified = accountVerified;
  }

  public String createActivationToken(UUID userId) {
    return Integer.toString(userId.toString().hashCode());
  }

  public UserType getUserType() {
    return userType;
  }
}