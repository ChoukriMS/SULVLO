package ca.ulaval.glo4003.sulvlo.domain.station;

import ca.ulaval.glo4003.sulvlo.domain.station.exception.TokenExpiredException;
import java.time.LocalDateTime;

public record UnlockBikeToken(
    String code,
    LocalDateTime localDateTime) {

  public void validateToken(LocalDateTime localDateTime) {
    if (this.localDateTime.isBefore(localDateTime)) {
      throw new TokenExpiredException();
    }
  }

  public void validateStudentToken(LocalDateTime localDateTime) {
    if (this.localDateTime.isBefore(localDateTime)) {
      throw new TokenExpiredException();
    }
  }

}
