package ca.ulaval.glo4003.sulvlo.domain.user.token;

import ca.ulaval.glo4003.sulvlo.api.user.dto.LoginTokenDto;
import java.time.LocalDateTime;

public class LoginTokenAssembler {

  public LoginTokenDto create(String token, LocalDateTime tokenExpirationDate) {
    return new LoginTokenDto(token, tokenExpirationDate.toString());
  }

}
