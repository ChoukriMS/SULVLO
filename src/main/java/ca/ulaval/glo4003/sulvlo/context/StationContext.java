package ca.ulaval.glo4003.sulvlo.context;

import static ca.ulaval.glo4003.sulvlo.infrastructure.util.email.EmailSenderImpl.LOGGER;

import ca.ulaval.glo4003.sulvlo.api.station.StationResource;
import ca.ulaval.glo4003.sulvlo.api.station.validation.CommonStationRequestsValidators;
import ca.ulaval.glo4003.sulvlo.api.station.validation.StationRequestsValidator;
import ca.ulaval.glo4003.sulvlo.application.service.station.StationService;
import ca.ulaval.glo4003.sulvlo.domain.payment.service.PaymentDomainService;
import ca.ulaval.glo4003.sulvlo.domain.station.StationAssembler;
import ca.ulaval.glo4003.sulvlo.domain.station.StationRepository;
import ca.ulaval.glo4003.sulvlo.domain.station.service.StationDomainService;
import ca.ulaval.glo4003.sulvlo.domain.station.service.TravelerDomainService;
import ca.ulaval.glo4003.sulvlo.domain.station.service.TripDomainService;
import ca.ulaval.glo4003.sulvlo.domain.station.service.UniqueCodeDomainService;
import ca.ulaval.glo4003.sulvlo.domain.station.traveler.TravelerFactory;
import ca.ulaval.glo4003.sulvlo.domain.station.traveler.TravelerRepository;
import ca.ulaval.glo4003.sulvlo.domain.travel.service.TravelDomainService;
import ca.ulaval.glo4003.sulvlo.domain.user.service.UserDomainService;
import ca.ulaval.glo4003.sulvlo.domain.util.email.EmailSender;
import ca.ulaval.glo4003.sulvlo.infrastructure.config.JsonFileReader;
import ca.ulaval.glo4003.sulvlo.infrastructure.station.StationDevDataFactory;
import ca.ulaval.glo4003.sulvlo.infrastructure.station.StationRepositoryInMemory;
import ca.ulaval.glo4003.sulvlo.infrastructure.station.traveler.TravelerRepositoryInMemory;


public class StationContext implements Context {

  private final ServiceLocator serviceLocator = ServiceLocator.getInstance();

  private StationRepository stationRepository;
  private TravelerRepository travelerRepository;

  private StationAssembler stationAssembler;
  private CommonStationRequestsValidators commonStationRequestsValidators;
  private StationRequestsValidator stationRequestsValidator;
  private TravelerFactory travelerFactory;

  private StationDomainService stationDomainService;
  private TripDomainService tripDomainService;
  private UniqueCodeDomainService uniqueCodeDomainService;
  private TravelerDomainService travelerDomainService;

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
    createStationResource();
  }

  private void registerRepositories() {
    stationRepository = new StationRepositoryInMemory(
        new StationDevDataFactory(new JsonFileReader()));
    travelerRepository = new TravelerRepositoryInMemory();
  }

  private void create() {
    stationAssembler = new StationAssembler();
    commonStationRequestsValidators = new CommonStationRequestsValidators();
    stationRequestsValidator = new StationRequestsValidator(commonStationRequestsValidators);
    travelerFactory = new TravelerFactory();
  }

  private void createDomainServices() {
    stationDomainService = new StationDomainService(stationRepository);
    tripDomainService = new TripDomainService(serviceLocator.resolve(TravelDomainService.class));
    uniqueCodeDomainService = new UniqueCodeDomainService(
        serviceLocator.resolve(EmailSender.class));
    travelerDomainService = new TravelerDomainService(
        travelerFactory,
        travelerRepository,
        serviceLocator.resolve(UserDomainService.class),
        serviceLocator.resolve(PaymentDomainService.class)
    );

    serviceLocator.register(StationDomainService.class, stationDomainService);
    serviceLocator.register(TripDomainService.class, tripDomainService);
    serviceLocator.register(UniqueCodeDomainService.class, uniqueCodeDomainService);
    serviceLocator.register(TravelerDomainService.class, travelerDomainService);
  }

  private void createStationResource() {
    LOGGER.info("Setup station resource dependencies (DOMAIN + INFRASTRUCTURE)");

    StationService stationService = new StationService(
        stationRepository,
        stationAssembler,
        tripDomainService,
        serviceLocator.resolve(TravelerDomainService.class),
        uniqueCodeDomainService
    );

    StationResource stationResource = new StationResource(stationService, stationRequestsValidator);

    serviceLocator.register(StationResource.class, stationResource);
  }


}
