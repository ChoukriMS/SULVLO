package ca.ulaval.glo4003.sulvlo.infrastructure.user;

import ca.ulaval.glo4003.sulvlo.domain.user.User;
import ca.ulaval.glo4003.sulvlo.domain.user.UserType;
import ca.ulaval.glo4003.sulvlo.domain.user.information.Gender;
import ca.ulaval.glo4003.sulvlo.domain.user.information.UserId;
import ca.ulaval.glo4003.sulvlo.domain.user.information.UserInformation;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class UserDevDataFactory {

  private static final UserInformation TECHNICIEN_USER_INFO = new UserInformation("Sylvain",
      "technicien@nospam.today", 25, LocalDate.of(1997, 06, 03), Gender.MALE,
      UUID.randomUUID(), "test123".hashCode());

  private static final UserInformation NORMAL_USER_INFO = new UserInformation("Martin",
      "martin@nospam.today", 22, LocalDate.of(2000, 06, 03), Gender.MALE,
      UUID.randomUUID(), "test123".hashCode());

  private static final UserInformation DEV_USER_INFO = new UserInformation("BIG DEVS",
      "BIGDEVS@nospam.today", 22, LocalDate.of(2000, 06, 03), Gender.MALE,
      UUID.randomUUID(), "test123".hashCode());

  private static final UserInformation MANAGER_USER_INFO = new UserInformation("Anabelle",
      "ROTHSCHILD@nospam.today", 22, LocalDate.of(2000, 06, 03), Gender.FEMALE,
      UUID.randomUUID(), "test123".hashCode());

  public List<User> createMockData() {
    User norm = new User(NORMAL_USER_INFO, UserType.NORMAL, new UserId("SYGAU1"));
    User tech = new User(TECHNICIEN_USER_INFO, UserType.TECHNICIEN, new UserId("MAGAU1"));
    User developer = new User(DEV_USER_INFO, UserType.DEVELOPER, new UserId("LOLOL7"));
    User manager = new User(MANAGER_USER_INFO, UserType.MANAGER, new UserId("MNG01"));
    tech.setAccountVerified(true);
    tech.unblockAccount();
    return List.of(norm, tech, developer, manager);
  }
}
