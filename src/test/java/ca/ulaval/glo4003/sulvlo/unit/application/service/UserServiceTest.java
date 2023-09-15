package ca.ulaval.glo4003.sulvlo.unit.application.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.sulvlo.api.user.dto.ActivationDto;
import ca.ulaval.glo4003.sulvlo.api.user.dto.RegisterDto;
import ca.ulaval.glo4003.sulvlo.api.user.dto.RequestMaintenanceDto;
import ca.ulaval.glo4003.sulvlo.application.service.user.UserService;
import ca.ulaval.glo4003.sulvlo.auth.jwt.TokenAuthentificationService;
import ca.ulaval.glo4003.sulvlo.domain.station.Station;
import ca.ulaval.glo4003.sulvlo.domain.station.service.StationDomainService;
import ca.ulaval.glo4003.sulvlo.domain.user.User;
import ca.ulaval.glo4003.sulvlo.domain.user.UserFactory;
import ca.ulaval.glo4003.sulvlo.domain.user.UserRepository;
import ca.ulaval.glo4003.sulvlo.domain.user.UserType;
import ca.ulaval.glo4003.sulvlo.domain.user.token.LoginTokenAssembler;
import ca.ulaval.glo4003.sulvlo.domain.util.email.EmailSender;
import ca.ulaval.glo4003.sulvlo.infrastructure.user.exception.UserAlreadyExistsException;
import jakarta.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  private static final String STATION_CODE = "PLT";
  private static final String NAME = "test";
  private static final String EMAIL = "test@hotmail.com";
  private static final String IDUL = "itsel5";
  private static final int AGE = 11;
  private static final String BIRTH_DATE_STRING = "02/02/2002";
  private static final String GENDER_STRING = "Male";
  private static final String PASSWORD_STRING = "test";
  private static final String ACTIVATION_TOKEN = "test";
  private static RequestMaintenanceDto REQUEST_MAINTENANCE_DTO;
  @Mock
  private UserRepository userRepository;
  @Mock
  private StationDomainService stationDomainService;
  @Mock
  private EmailSender emailSender;
  @Mock
  private Station station;
  @Mock
  private User user;
  @Mock
  private UserFactory userFactory;
  @Mock
  private LoginTokenAssembler loginTokenAssembler;
  @Mock
  private TokenAuthentificationService tokenAuthentificationService;
  private UserService userService;
  private List<User> technicienList;

  @BeforeEach
  public void setUp() {
    userService = new UserService(userRepository, userFactory, loginTokenAssembler,
        tokenAuthentificationService, stationDomainService, emailSender);
  }

  @Test
  void givenARegisterDto_whenCreatingUser_thenEmailIsSendWithActivationToken() {
    RegisterDto registerDto = new RegisterDto(NAME, EMAIL, IDUL, AGE, PASSWORD_STRING,
        BIRTH_DATE_STRING, GENDER_STRING);

    when(userRepository.getByIdul(IDUL)).thenThrow(NotFoundException.class);
    when(userFactory.create(registerDto)).thenReturn(user);
    when(user.getEmail()).thenReturn(EMAIL);
    doNothing().when(userRepository).save(user);

    userService.register(registerDto);

    verify(emailSender, times(1)).send(anyString(), anyString(), anyString());
  }

  @Test
  void givenARegisterDto_whenRegisteringUserThatAlreadyExist_thenUserAlreadyExistsException() {
    when(userRepository.getByIdul(IDUL)).thenReturn(null);

    RegisterDto registerDto = new RegisterDto(NAME, EMAIL, IDUL, AGE, PASSWORD_STRING,
        BIRTH_DATE_STRING, GENDER_STRING);

    assertThrows(UserAlreadyExistsException.class, () -> {
      userService.register(registerDto);
    });
  }

  @Test
  void givenAUserTokenAndActivationDto_whenTryingToActivateUserAccount_thenActiveAccount() {
    when(userRepository.getByEmail(EMAIL)).thenReturn(user);
    ActivationDto activationDto = new ActivationDto(EMAIL);

    userService.activateUser(ACTIVATION_TOKEN, activationDto);

    verify(user, times(1)).validateActivationToken(anyString());
  }

  @Test
  void givenARequestMaintenanceDto_whenSendingEmailToAllTechniciens_ThenShouldSendEmail() {
    generateData();
    when(userRepository.getByEmail(EMAIL)).thenReturn(user);
    when(stationDomainService.findAvailableByCode(STATION_CODE)).thenReturn(station);
    when(userRepository.getUsersByType(UserType.TECHNICIEN)).thenReturn(technicienList);
    when(user.getEmail()).thenReturn(EMAIL);

    userService.sendMaintenanceRequest(REQUEST_MAINTENANCE_DTO);

    verify(emailSender, times(1)).send(anyString(), anyString(), anyString());
  }

  private void generateData() {
    REQUEST_MAINTENANCE_DTO = new RequestMaintenanceDto(STATION_CODE, EMAIL);
    technicienList = new ArrayList<>();
    technicienList.add(user);

  }
}