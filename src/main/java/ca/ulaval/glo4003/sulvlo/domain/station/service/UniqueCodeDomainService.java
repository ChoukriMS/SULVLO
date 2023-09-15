package ca.ulaval.glo4003.sulvlo.domain.station.service;

import ca.ulaval.glo4003.sulvlo.domain.station.UnlockBikeToken;
import ca.ulaval.glo4003.sulvlo.domain.station.exception.InvalidTokenException;
import ca.ulaval.glo4003.sulvlo.domain.util.email.EmailSender;
import java.time.LocalDateTime;
import java.util.HashMap;
import org.apache.commons.lang3.RandomStringUtils;

public class UniqueCodeDomainService {

  private static final String UNIQUE_CODE_EMAIL_SUBJECT = "Unique bike code for SULVLO";
  private final EmailSender emailSender;
  private final HashMap<String, UnlockBikeToken> unlockBikeTokenList = new HashMap<>();

  public UniqueCodeDomainService(EmailSender emailSender) {
    this.emailSender = emailSender;
  }

  public UnlockBikeToken generateUnlockBikeToken() {
    UnlockBikeToken unlockBikeToken = new UnlockBikeToken(RandomStringUtils.randomNumeric(10),
        LocalDateTime.now().plusMinutes(1));

    unlockBikeTokenList.put(unlockBikeToken.code(), unlockBikeToken);

    return unlockBikeToken;
  }

  public UnlockBikeToken generateStudentUnlockBikeToken() {
    UnlockBikeToken unlockBikeToken = new UnlockBikeToken(RandomStringUtils.randomNumeric(10),
        LocalDateTime.now().plusMinutes(1));

    unlockBikeTokenList.put(unlockBikeToken.code(), unlockBikeToken);

    return unlockBikeToken;
  }

  public void isTokenValid(String code) {
    if (unlockBikeTokenList.containsKey(code)) {
      UnlockBikeToken unlockBikeToken = unlockBikeTokenList.get(code);
      unlockBikeToken.validateToken(LocalDateTime.now());
      return;
    }
    throw new InvalidTokenException();
  }

  public void sendUniqueCode(String email, String randomUniqueCode) {
    emailSender.send(email, formatEmailBodyForUnlockBikeCode(randomUniqueCode),
        UNIQUE_CODE_EMAIL_SUBJECT);
  }

  private String formatEmailBodyForUnlockBikeCode(String code) {
    return String.format("Your unique code to unlock your bike is: %s", code);
  }
}
