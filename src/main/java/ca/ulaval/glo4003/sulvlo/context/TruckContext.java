package ca.ulaval.glo4003.sulvlo.context;

import static ca.ulaval.glo4003.sulvlo.infrastructure.util.email.EmailSenderImpl.LOGGER;

import ca.ulaval.glo4003.sulvlo.api.station.validation.CommonStationRequestsValidators;
import ca.ulaval.glo4003.sulvlo.api.truck.TruckResource;
import ca.ulaval.glo4003.sulvlo.api.truck.validation.CommonTruckRequestsValidators;
import ca.ulaval.glo4003.sulvlo.api.truck.validation.TruckRequestsValidator;
import ca.ulaval.glo4003.sulvlo.application.service.truck.TruckService;
import ca.ulaval.glo4003.sulvlo.domain.station.service.StationDomainService;
import ca.ulaval.glo4003.sulvlo.domain.truck.TruckAssembler;
import ca.ulaval.glo4003.sulvlo.domain.truck.TruckRepository;
import ca.ulaval.glo4003.sulvlo.infrastructure.truck.TruckDevDataFactory;
import ca.ulaval.glo4003.sulvlo.infrastructure.truck.TruckRepositoryInMemory;


public class TruckContext implements Context {

  private final ServiceLocator serviceLocator = ServiceLocator.getInstance();

  private TruckRepository truckRepository;

  private TruckAssembler truckAssembler;
  private CommonStationRequestsValidators commonStationRequestsValidators;
  private CommonTruckRequestsValidators commonTruckRequestsValidators;
  private TruckRequestsValidator truckRequestsValidator;

  @Override
  public void registerContext() {
    registerRepositories();
    create();
  }

  @Override
  public void registerDomainServices() {
  }

  @Override
  public void createResource() {
    createTruckResource();
  }

  private void registerRepositories() {
    truckRepository = new TruckRepositoryInMemory(new TruckDevDataFactory());
  }

  private void create() {
    truckAssembler = new TruckAssembler();
    commonStationRequestsValidators = new CommonStationRequestsValidators();
    commonTruckRequestsValidators = new CommonTruckRequestsValidators(
        commonStationRequestsValidators);
    truckRequestsValidator = new TruckRequestsValidator(
        commonTruckRequestsValidators);
  }

  private void createTruckResource() {
    LOGGER.info("Setup truck resource dependencies (DOMAIN + INFRASTRUCTURE)");

    TruckService truckService = new TruckService(
        serviceLocator.resolve(StationDomainService.class),
        truckRepository,
        truckAssembler
    );

    TruckResource truckResource = new TruckResource(truckService, truckRequestsValidator);

    serviceLocator.register(TruckResource.class, truckResource);
  }
}
