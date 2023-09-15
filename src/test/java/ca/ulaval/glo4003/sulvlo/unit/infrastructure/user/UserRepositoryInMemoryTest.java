package ca.ulaval.glo4003.sulvlo.unit.infrastructure.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.sulvlo.domain.user.User;
import ca.ulaval.glo4003.sulvlo.domain.user.UserType;
import ca.ulaval.glo4003.sulvlo.domain.user.information.Gender;
import ca.ulaval.glo4003.sulvlo.domain.user.information.UserId;
import ca.ulaval.glo4003.sulvlo.domain.user.information.UserInformation;
import ca.ulaval.glo4003.sulvlo.infrastructure.user.UserRepositoryInMemory;
import ca.ulaval.glo4003.sulvlo.infrastructure.user.exception.UserNotFoundException;
import jakarta.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserRepositoryInMemoryTest {

  private static final UserType userType = UserType.NORMAL;
  private static final String NAME = "test";
  private static final String EMAIL = "test@hotmail.com";
  private static final String IDUL = "test";
  private static final int AGE = 11;
  private static final Gender GENDER = Gender.MALE;
  private static final UUID ID = UUID.randomUUID();
  private static final int PASSWORD = 123;
  private static final LocalDate birthDate = LocalDate.now();
  private User normalUser;
  private User technicienUser;
  private UserRepositoryInMemory userRepositoryInMemory;

  @BeforeEach
  public void setUp() {
    userRepositoryInMemory = new UserRepositoryInMemory();
    normalUser = createUser();
    technicienUser = createTechnicienUser();
  }

  @Test
  void givenAUser_whenTryingSaveUser_ThenAddUserToPersistance() {
    userRepositoryInMemory.save(normalUser);

    User userReturned = userRepositoryInMemory.getByIdul(IDUL);

    assertEquals(normalUser, userReturned);
  }

  @Test
  void givenAnEmail_whenTryingGetUser_thenReturnUser() {
    userRepositoryInMemory.save(normalUser);

    User userReturned = userRepositoryInMemory.getByEmail(EMAIL);

    assertEquals(normalUser, userReturned);
  }

  @Test
  void givenAnEmail_whenTryingToRegisterWithEmailThatAlreadyExist_thenThrowNotAuthorizedException() {
    assertThrows(UserNotFoundException.class, () -> {
      userRepositoryInMemory.getByEmail(EMAIL);
    });
  }


  @Test
  void givenAUserType_whenGettingAllUserWithTheSameUserType_ThenReturnListOfUser() {
    userRepositoryInMemory.save(technicienUser);

    List<User> technicienList = userRepositoryInMemory.getUsersByType(UserType.TECHNICIEN);

    assertNotNull(technicienList);
  }

  @Test
  void givenAIdul_whenGettingUserByIdul_thenTheUserIsReturn() {
    userRepositoryInMemory.save(normalUser);

    User user = userRepositoryInMemory.getByIdul(IDUL);

    assertEquals(IDUL, user.getIdul());
  }

  @Test
  void givenAnIdul_whenGettingUserThatISNotInRepository_thenNotFoundException() {
    assertThrows(NotFoundException.class, () -> {
      userRepositoryInMemory.getByIdul(IDUL);
    });
  }

  private User createTechnicienUser() {
    return new User(new UserInformation(NAME, EMAIL, AGE, birthDate, GENDER, ID, PASSWORD),
        UserType.TECHNICIEN, new UserId(IDUL));
  }

  private User createUser() {
    return new User(new UserInformation(NAME, EMAIL, AGE, birthDate, GENDER, ID, PASSWORD),
        userType, new UserId(IDUL));
  }

}