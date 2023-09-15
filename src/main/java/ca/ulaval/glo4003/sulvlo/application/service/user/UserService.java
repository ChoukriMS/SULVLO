package ca.ulaval.glo4003.sulvlo.application.service.user;

import ca.ulaval.glo4003.sulvlo.api.user.dto.ActivationDto;
import ca.ulaval.glo4003.sulvlo.api.user.dto.LoginDto;
import ca.ulaval.glo4003.sulvlo.api.user.dto.LoginTokenDto;
import ca.ulaval.glo4003.sulvlo.api.user.dto.RegisterDto;
import ca.ulaval.glo4003.sulvlo.api.user.dto.RequestMaintenanceDto;
import ca.ulaval.glo4003.sulvlo.auth.jwt.TokenAuthentificationDetails;
import ca.ulaval.glo4003.sulvlo.auth.jwt.TokenAuthentificationService;
import ca.ulaval.glo4003.sulvlo.domain.station.service.StationDomainService;
import ca.ulaval.glo4003.sulvlo.domain.user.User;
import ca.ulaval.glo4003.sulvlo.domain.user.UserFactory;
import ca.ulaval.glo4003.sulvlo.domain.user.UserRepository;
import ca.ulaval.glo4003.sulvlo.domain.user.UserType;
import ca.ulaval.glo4003.sulvlo.domain.user.token.LoginTokenAssembler;
import ca.ulaval.glo4003.sulvlo.domain.util.email.EmailSender;
import ca.ulaval.glo4003.sulvlo.infrastructure.user.exception.UserAlreadyExistsException;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
  private static final String ACTIVATION_TOKEN_EMAIL_SUBJECT = "Activation account token for SULVLO";
  private static final String REQUEST_MAINTENANCE_EMAIL_SUBJECT = "Request maintenance for SULVLO";
  private final UserRepository userRepository;
  private final UserFactory userFactory;
  private final LoginTokenAssembler loginTokenAssembler;
  private final StationDomainService stationDomainService;
  private final EmailSender emailSender;
  private final TokenAuthentificationService tokenAuthentificationService;

  public UserService(UserRepository userRepository,
      UserFactory userFactory,
      LoginTokenAssembler loginTokenAssembler,
      TokenAuthentificationService tokenAuthentificationService,
      StationDomainService stationDomainService,
      EmailSender emailSender) {
    this.emailSender = emailSender;
    this.userRepository = userRepository;
    this.stationDomainService = stationDomainService;
    this.userFactory = userFactory;
    this.loginTokenAssembler = loginTokenAssembler;
    this.tokenAuthentificationService = tokenAuthentificationService;
  }

  public UUID register(RegisterDto info) throws UserAlreadyExistsException {
    LOGGER.info("Register user {}", info);

    User user = initiateUser(info);
    userRepository.save(user);
    String emailBody = formatEmailBodyForActivationCode(user.getActivationToken());

    emailSender.send(user.getEmail(), emailBody, ACTIVATION_TOKEN_EMAIL_SUBJECT);
    return user.getId();
  }

  private String formatEmailBodyForActivationCode(String token) {
    return String.format(
        "Your activation token is: %s, please enter this token to validate your account!", token);
  }

  private User initiateUser(RegisterDto registerDto) {
    try {
      userRepository.getByIdul(registerDto.idul());
      throw new UserAlreadyExistsException();

    } catch (NotFoundException e) {
      return userFactory.create(registerDto);
    }
  }

  public LoginTokenDto login(LoginDto info) throws NotAuthorizedException {
    LOGGER.info("Login user {}", info);
    User user = userRepository.getByEmail(info.email());
    user.checkPassword(info.password().hashCode());

    TokenAuthentificationDetails tokenAuthentificationDetails = tokenAuthentificationService
        .createJwtTokenDetails(user.getName(), user.getEmail(), user.getIdul(),
            getRoleOfCurrentUser(user.getEmail()));
    String token = tokenAuthentificationService.createJwtToken(tokenAuthentificationDetails);

    LocalDateTime expirationDate = convertDateToLocalDateTime(
        tokenAuthentificationService.getClaims(token).getExpiration());
    return loginTokenAssembler.create(token, expirationDate);
  }

  public void activateUser(String userToken, ActivationDto activationDto) {
    LOGGER.info("Activate user account {}", activationDto);
    String email = activationDto.email();

    User user = userRepository.getByEmail(email);
    user.validateActivationToken(userToken);
  }

  private String getRoleOfCurrentUser(String email) {
    User user = userRepository.getByEmail(email);

    return user.getUserType().toString();
  }

  private String formatEmailBodyForRequestMaintenance(String code) {
    return String.format("Station '%s' needs G.", code);
  }

  public void sendMaintenanceRequest(RequestMaintenanceDto requestMaintenanceDto) {
    LOGGER.info("Sending maintenance requests {}", requestMaintenanceDto);
    userRepository.getByEmail(requestMaintenanceDto.email());

    stationDomainService.findAvailableByCode(requestMaintenanceDto.stationCode());

    List<User> listTechnicien = userRepository.getUsersByType(UserType.TECHNICIEN);
    listTechnicien.forEach(users -> emailSender.send(users.getEmail(),
        formatEmailBodyForRequestMaintenance(requestMaintenanceDto.stationCode()),
        REQUEST_MAINTENANCE_EMAIL_SUBJECT));
  }

  private LocalDateTime convertDateToLocalDateTime(Date dateToConvert) {
    return dateToConvert.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime();
  }
}
