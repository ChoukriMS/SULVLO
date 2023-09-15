package ca.ulaval.glo4003.sulvlo.context;

import ca.ulaval.glo4003.sulvlo.api.stats.StatsResource;
import ca.ulaval.glo4003.sulvlo.application.service.stats.StatsService;
import ca.ulaval.glo4003.sulvlo.domain.station.service.StationDomainService;
import ca.ulaval.glo4003.sulvlo.domain.station.service.TravelerDomainService;
import ca.ulaval.glo4003.sulvlo.domain.stats.ManagerNotifierDomainService;
import ca.ulaval.glo4003.sulvlo.domain.stats.StatsAssembler;
import ca.ulaval.glo4003.sulvlo.domain.util.email.EmailSender;

public class StatsContext implements Context {

  private final ServiceLocator serviceLocator = ServiceLocator.getInstance();

  private StatsAssembler statsAssembler;
  private ManagerNotifierDomainService managerNotifierDomainService;

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
    createPaymentResource();
  }

  private void registerRepositories() {
  }

  private void create() {
    statsAssembler = new StatsAssembler();
  }

  private void createDomainServices() {
    managerNotifierDomainService = new ManagerNotifierDomainService(
        serviceLocator.resolve(EmailSender.class)
    );
  }

  private void createPaymentResource() {
    StatsService statsService = new StatsService(
        statsAssembler,
        serviceLocator.resolve(StationDomainService.class),
        serviceLocator.resolve(TravelerDomainService.class),
        managerNotifierDomainService
    );

    StatsResource statsResource = new StatsResource(statsService);
    serviceLocator.register(StatsResource.class, statsResource);
  }

}
