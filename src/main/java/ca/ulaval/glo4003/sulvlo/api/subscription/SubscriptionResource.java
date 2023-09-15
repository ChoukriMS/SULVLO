package ca.ulaval.glo4003.sulvlo.api.subscription;

import ca.ulaval.glo4003.sulvlo.api.mapper.SuccessfulResponse;
import ca.ulaval.glo4003.sulvlo.api.subscription.dto.SubscriptionDto;
import ca.ulaval.glo4003.sulvlo.api.validation.Nodes.CreditCard.CCardCcvValidation;
import ca.ulaval.glo4003.sulvlo.api.validation.Nodes.CreditCard.CCardExpirationValidator;
import ca.ulaval.glo4003.sulvlo.api.validation.Nodes.CreditCard.CCardNumberValidator;
import ca.ulaval.glo4003.sulvlo.api.validation.Nodes.IdulValidation;
import ca.ulaval.glo4003.sulvlo.api.validation.Nodes.Subscription.SemesterValidation;
import ca.ulaval.glo4003.sulvlo.api.validation.Nodes.Subscription.SubscriptionTypeValidation;
import ca.ulaval.glo4003.sulvlo.api.validation.ValidationNode;
import ca.ulaval.glo4003.sulvlo.api.validation.exception.InvalidSubscriptionParameterException;
import ca.ulaval.glo4003.sulvlo.application.service.subscription.SubscriptionService;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/subscriptions")
@Produces(MediaType.APPLICATION_JSON)
public class SubscriptionResource {

  public static final String SUCCESSFULLY_SUSBCRIBED = "You have been successfully subscribed";
  private final ValidationNode<SubscriptionDto> subscriptionValidationChain = ValidationNode.link(
      new IdulValidation(),
      new SemesterValidation(),
      new SubscriptionTypeValidation(),
      new CCardNumberValidator(),
      new CCardExpirationValidator(),
      new CCardCcvValidation()
  );
  private SubscriptionService subscriptionService;

  public SubscriptionResource() {
  }

  public SubscriptionResource(SubscriptionService subscriptionService) {
    this.subscriptionService = subscriptionService;
  }

  @POST
  public Response addSubscription(SubscriptionDto subscriptionDto) {
    if (!subscriptionValidationChain.isValid(subscriptionDto)) {
      throw new InvalidSubscriptionParameterException();
    }

    subscriptionService.addSubscription(subscriptionDto);

    return Response.status(Response.Status.OK)
        .entity(new SuccessfulResponse(SUCCESSFULLY_SUSBCRIBED).toString()).build();
  }

  @GET
  @PermitAll
  public Response getSubscriptions() {
    return Response.status(Response.Status.OK).entity(subscriptionService.findAllSubscriptionType())
        .build();
  }

}
