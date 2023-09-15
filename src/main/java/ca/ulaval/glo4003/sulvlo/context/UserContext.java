package ca.ulaval.glo4003.sulvlo.context;

import static ca.ulaval.glo4003.sulvlo.infrastructure.util.email.EmailSenderImpl.LOGGER;

import ca.ulaval.glo4003.sulvlo.api.user.UserResource;
import ca.ulaval.glo4003.sulvlo.application.service.user.UserService;
import ca.ulaval.glo4003.sulvlo.auth.jwt.TokenAuthentificationService;
import ca.ulaval.glo4003.sulvlo.domain.station.service.StationDomainService;
import ca.ulaval.glo4003.sulvlo.domain.user.UserFactory;
import ca.ulaval.glo4003.sulvlo.domain.user.UserRepository;
import ca.ulaval.glo4003.sulvlo.domain.user.service.UserDomainService;
import ca.ulaval.glo4003.sulvlo.domain.user.token.LoginTokenAssembler;
import ca.ulaval.glo4003.sulvlo.domain.util.email.EmailSender;
import ca.ulaval.glo4003.sulvlo.infrastructure.user.UserRepositoryInMemory;


public class UserContext implements Context {

  private final ServiceLocator serviceLocator = ServiceLocator.getInstance();

  private UserRepository userRepository;

  private UserFactory userFactory;
  private LoginTokenAssembler loginTokenAssembler;

  private UserDomainService userDomainService;

  @Override
  public void registerContext() {
    registerRepositories();
    create();
  }

  @Override
  public void registerDomainServices() {
    createDomainServices();
  }

  @Override
  public void createResource() {
    createUserResource();
  }

  private void registerRepositories() {
    userRepository = new UserRepositoryInMemory();
  }

  private void create() {
    userFactory = new UserFactory();
    loginTokenAssembler = new LoginTokenAssembler();
  }

  private void createDomainServices() {
    userDomainService = new UserDomainService(userRepository);

    serviceLocator.register(UserDomainService.class, userDomainService);
  }

  private void createUserResource() {
    LOGGER.info("Setup user resource dependencies (DOMAIN + INFRASTRUCTURE)");

    UserService userService = new UserService(
        userRepository,
        userFactory,
        loginTokenAssembler,
        serviceLocator.resolve(TokenAuthentificationService.class),
        serviceLocator.resolve(StationDomainService.class),
        serviceLocator.resolve(EmailSender.class)
    );

    UserResource userResource = new UserResource(userService);

    serviceLocator.register(UserResource.class, userResource);
  }

}
