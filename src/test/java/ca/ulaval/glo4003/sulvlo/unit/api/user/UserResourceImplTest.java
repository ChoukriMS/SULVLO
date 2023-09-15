package ca.ulaval.glo4003.sulvlo.unit.api.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.sulvlo.api.user.UserResource;
import ca.ulaval.glo4003.sulvlo.api.user.dto.RegisterDto;
import ca.ulaval.glo4003.sulvlo.application.service.user.UserService;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserResourceImplTest {

  private static final String NAME = "test";
  private static final String EMAIL = "test@hotmail.com";
  private static final String IDUL = "test";
  private static final int AGE = 11;
  private static final String BIRTH_DATE_STRING = "02/02/2002";
  private static final String GENDER_STRING = "Male";
  private static final String PASSWORD_STRING = "test";
  private static final UUID UUId = UUID.randomUUID();

  @Mock
  private UserService userService;
  private UserResource userResource;

  @BeforeEach
  public void setUp() {
    userResource = new UserResource(userService);
  }

  @Test
  void giveARegisterDto_whenTryingToRegisterUser_thenReturnsExpectedUniqueId() {
    RegisterDto registerDto = new RegisterDto(NAME, EMAIL, IDUL, AGE, PASSWORD_STRING,
        BIRTH_DATE_STRING, GENDER_STRING);
    when(userService.register(registerDto)).thenReturn(UUId);
    String hashedUserId = userResource.register(registerDto).getHeaderString("activationToken");
    String expectedHashedUSerId = Integer.toString(UUId.toString().hashCode());

    assertEquals(expectedHashedUSerId, hashedUserId);
  }

}