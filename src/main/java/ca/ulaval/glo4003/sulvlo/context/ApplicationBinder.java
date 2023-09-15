package ca.ulaval.glo4003.sulvlo.context;

import static ca.ulaval.glo4003.sulvlo.infrastructure.util.email.EmailSenderImpl.LOGGER;

import ca.ulaval.glo4003.sulvlo.api.payment.PaymentResource;
import ca.ulaval.glo4003.sulvlo.api.station.StationResource;
import ca.ulaval.glo4003.sulvlo.api.stats.StatsResource;
import ca.ulaval.glo4003.sulvlo.api.subscription.SubscriptionResource;
import ca.ulaval.glo4003.sulvlo.api.travel.TravelResource;
import ca.ulaval.glo4003.sulvlo.api.truck.TruckResource;
import ca.ulaval.glo4003.sulvlo.api.user.UserResource;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ApplicationBinder extends AbstractBinder {

  public static final ServiceLocator serviceLocator = ServiceLocator.getInstance();

  @Override
  protected void configure() {
    LOGGER.info("Setup resources (API)");

    bind(serviceLocator.resolve(UserResource.class)).to(UserResource.class);
    bind(serviceLocator.resolve(StationResource.class)).to(StationResource.class);
    bind(serviceLocator.resolve(TruckResource.class)).to(TruckResource.class);
    bind(serviceLocator.resolve(TravelResource.class)).to(TravelResource.class);
    bind(serviceLocator.resolve(SubscriptionResource.class)).to(SubscriptionResource.class);
    bind(serviceLocator.resolve(PaymentResource.class)).to(PaymentResource.class);
    bind(serviceLocator.resolve(StatsResource.class)).to(StatsResource.class);
  }

}
