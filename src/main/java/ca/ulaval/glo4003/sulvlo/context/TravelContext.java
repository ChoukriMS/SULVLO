package ca.ulaval.glo4003.sulvlo.context;

import static ca.ulaval.glo4003.sulvlo.infrastructure.util.email.EmailSenderImpl.LOGGER;

import ca.ulaval.glo4003.sulvlo.api.travel.TravelResource;
import ca.ulaval.glo4003.sulvlo.application.service.travel.TravelService;
import ca.ulaval.glo4003.sulvlo.application.service.travel.assembler.TravelAssembler;
import ca.ulaval.glo4003.sulvlo.application.service.travel.assembler.TravelHistoryAssembler;
import ca.ulaval.glo4003.sulvlo.domain.travel.TravelFactory;
import ca.ulaval.glo4003.sulvlo.domain.travel.TravelRepository;
import ca.ulaval.glo4003.sulvlo.domain.travel.service.TravelDomainService;
import ca.ulaval.glo4003.sulvlo.infrastructure.travel.TravelLogRepositoryInMemory;


public class TravelContext implements Context {

  private final ServiceLocator serviceLocator = ServiceLocator.getInstance();

  private TravelRepository travelRepository;

  private TravelAssembler travelAssembler;
  private TravelHistoryAssembler travelHistoryAssembler;
  private TravelFactory travelFactory;

  private TravelDomainService travelDomainService;

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
    createTravelResource();
  }

  private void registerRepositories() {
    travelRepository = new TravelLogRepositoryInMemory();
  }

  private void create() {
    travelAssembler = new TravelAssembler();
    travelHistoryAssembler = new TravelHistoryAssembler();
    travelFactory = new TravelFactory();
  }

  private void createDomainServices() {
    travelDomainService = new TravelDomainService(travelFactory, travelRepository);

    serviceLocator.register(TravelDomainService.class, travelDomainService);
  }

  private void createTravelResource() {
    LOGGER.info("Setup travel resource dependencies (DOMAIN + INFRASTRUCTURE)");

    TravelService travelService = new TravelService(travelRepository, travelAssembler,
        travelHistoryAssembler);

    TravelResource travelResource = new TravelResource(travelService);

    serviceLocator.register(TravelResource.class, travelResource);
  }

}
