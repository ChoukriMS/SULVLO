package ca.ulaval.glo4003.sulvlo.api.payment;

import ca.ulaval.glo4003.sulvlo.api.mapper.SuccessfulResponse;
import ca.ulaval.glo4003.sulvlo.api.payment.dto.AutomaticPaymentDto;
import ca.ulaval.glo4003.sulvlo.api.payment.dto.BalanceDto;
import ca.ulaval.glo4003.sulvlo.application.service.payment.PaymentService;
import ca.ulaval.glo4003.sulvlo.auth.jwt.AuthenticatedUserDetails;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("/api/payment")
@Produces(MediaType.APPLICATION_JSON)
public class PaymentResource {

  private static final String CONFIGURE_END_MONTH_SUCCESS = "Automatic payment end month updated.";
  private static final String CONFIGURE_AFTER_TRAVEL_SUCCESS = "Automatic payment after travel updated.";
  private static final String PAY_EXTRA_FESS_SUCCESS = "Extra fees successfully paid.";
  private static final String PAY_DEBT_SUCCESS = "Debt successfully paid.";
  private static final String PAY_SUBSCRIPTION_SUCCESS = "Subscription successfully paid.";

  private PaymentService paymentService;

  public PaymentResource() {
  }

  public PaymentResource(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @POST
  @Path("configure/end-month")
  public Response addAutomaticPaymentEndMonth(@Context SecurityContext secuContext,
      AutomaticPaymentDto automaticBillingDto) {
    AuthenticatedUserDetails userDetail = (AuthenticatedUserDetails) secuContext.getUserPrincipal();

    paymentService.addAutomaticPaymentEndMonth(userDetail.getIdul(), automaticBillingDto);
    return Response.ok(new SuccessfulResponse(CONFIGURE_END_MONTH_SUCCESS).toString()).build();
  }

  @POST
  @Path("configure/after-travel")
  public Response addAutomaticPaymentAfterTravel(@Context SecurityContext secuContext,
      AutomaticPaymentDto automaticBillingDto) {
    AuthenticatedUserDetails userDetail = (AuthenticatedUserDetails) secuContext.getUserPrincipal();

    paymentService.addAutomaticPaymentAfterTravel(userDetail.getIdul(), automaticBillingDto);
    return Response.ok(new SuccessfulResponse(CONFIGURE_AFTER_TRAVEL_SUCCESS).toString()).build();
  }

  @POST
  @Path("pay-extra-fees")
  public Response payExtraFees(@Context SecurityContext secuContext) {
    AuthenticatedUserDetails userDetail = (AuthenticatedUserDetails) secuContext.getUserPrincipal();

    paymentService.payExtraFees(userDetail.getIdul());
    return Response.ok(new SuccessfulResponse(PAY_EXTRA_FESS_SUCCESS).toString()).build();
  }

  @POST
  @Path("pay-debt")
  public Response payDebt(@Context SecurityContext secuContext) {
    AuthenticatedUserDetails userDetail = (AuthenticatedUserDetails) secuContext.getUserPrincipal();

    paymentService.payDebt(userDetail.getIdul());
    return Response.ok(new SuccessfulResponse(PAY_DEBT_SUCCESS).toString()).build();
  }

  @POST
  @Path("pay-subscription")
  public Response paySubscription(@Context SecurityContext secuContext) {
    AuthenticatedUserDetails userDetail = (AuthenticatedUserDetails) secuContext.getUserPrincipal();

    paymentService.paySubscription(userDetail.getIdul());
    return Response.ok(new SuccessfulResponse(PAY_SUBSCRIPTION_SUCCESS).toString()).build();
  }

  @GET
  @Path("balance")
  public Response getBalance(@Context SecurityContext secuContext) {
    AuthenticatedUserDetails userDetail = (AuthenticatedUserDetails) secuContext.getUserPrincipal();

    BalanceDto balanceDto = paymentService.getBalance(userDetail.getIdul());
    return Response.ok(balanceDto).build();
  }
}
