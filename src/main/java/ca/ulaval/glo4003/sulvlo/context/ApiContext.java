package ca.ulaval.glo4003.sulvlo.context;


public class ApiContext {

  private final UserContext userContext = new UserContext();
  private final TravelContext travelContext = new TravelContext();
  private final StationContext stationContext = new StationContext();
  private final PaymentContext paymentContext = new PaymentContext();
  private final SubscriptionContext subscriptionContext = new SubscriptionContext();
  private final TruckContext truckContext = new TruckContext();
  private final StatsContext statsContext = new StatsContext();

  public void applyContext() {
    userContext.registerContext();
    paymentContext.registerContext();
    travelContext.registerContext();
    stationContext.registerContext();
    subscriptionContext.registerContext();
    truckContext.registerContext();
    statsContext.registerContext();

    userContext.registerDomainServices();
    travelContext.registerDomainServices();
    paymentContext.registerDomainServices();
    stationContext.registerDomainServices();
    subscriptionContext.registerDomainServices();
    truckContext.registerDomainServices();
    statsContext.registerDomainServices();

    userContext.createResource();
    paymentContext.createResource();
    travelContext.createResource();
    stationContext.createResource();
    subscriptionContext.createResource();
    truckContext.createResource();
    statsContext.createResource();
  }

}
