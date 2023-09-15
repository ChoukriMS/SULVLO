package ca.ulaval.glo4003.sulvlo.api.user;

import ca.ulaval.glo4003.sulvlo.api.mapper.SuccessfulResponse;
import ca.ulaval.glo4003.sulvlo.api.user.dto.ActivationDto;
import ca.ulaval.glo4003.sulvlo.api.user.dto.LoginDto;
import ca.ulaval.glo4003.sulvlo.api.user.dto.RegisterDto;
import ca.ulaval.glo4003.sulvlo.api.user.dto.RequestMaintenanceDto;
import ca.ulaval.glo4003.sulvlo.api.validation.Nodes.EmailValidation;
import ca.ulaval.glo4003.sulvlo.api.validation.Nodes.IdulValidation;
import ca.ulaval.glo4003.sulvlo.api.validation.Nodes.PasswordValidation;
import ca.ulaval.glo4003.sulvlo.api.validation.Nodes.Register.AgeValidation;
import ca.ulaval.glo4003.sulvlo.api.validation.Nodes.Register.BirthDateValidation;
import ca.ulaval.glo4003.sulvlo.api.validation.Nodes.Register.GenderValidation;
import ca.ulaval.glo4003.sulvlo.api.validation.Nodes.Register.UserNameValidation;
import ca.ulaval.glo4003.sulvlo.api.validation.Nodes.Station.StationCodeValidation;
import ca.ulaval.glo4003.sulvlo.api.validation.ValidationNode;
import ca.ulaval.glo4003.sulvlo.api.validation.exception.InvalidConnectionArgumentException;
import ca.ulaval.glo4003.sulvlo.application.service.user.UserService;
import ca.ulaval.glo4003.sulvlo.auth.jwt.AuthenticatedUserDetails;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;
import java.util.UUID;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

  private static final String USER_CREATE_SUCCESS = "User successfully created.";
  private static final String USER_ACCOUNT_VERIFICATION = "User is successfully verified.";
  private final ValidationNode<RegisterDto> registerValidationChain = ValidationNode.link(
      new UserNameValidation(),
      new IdulValidation(),
      new AgeValidation(),
      new EmailValidation(),
      new PasswordValidation(),
      new BirthDateValidation(),
      new GenderValidation()
  );
  private final ValidationNode<LoginDto> loginValidationChain = ValidationNode.link(
      new EmailValidation(),
      new PasswordValidation()
  );
  private final ValidationNode<RequestMaintenanceDto> requestMaintenanceValidationChain = ValidationNode.link(
      new EmailValidation(),
      new StationCodeValidation()
  );
  private UserService userService;

  public UserResource() {
  }

  public UserResource(UserService userService) {
    this.userService = userService;
  }

  @POST
  @Path("/register")
  @PermitAll
  public Response register(RegisterDto registerDTO) throws ClientErrorException {
    if (!registerValidationChain.isValid(registerDTO)) {
      throw new InvalidConnectionArgumentException();
    }
    UUID userId = userService.register(registerDTO);
    return Response.status(Status.CREATED)
        .header("activationToken", Integer.toString(userId.toString().hashCode()))
        .entity(new SuccessfulResponse(USER_CREATE_SUCCESS).toString())
        .build();
  }

  @POST
  @Path("/login")
  @PermitAll
  public Response login(LoginDto loginDTO) throws ClientErrorException {
    if (!loginValidationChain.isValid(loginDTO)) {
      throw new InvalidConnectionArgumentException();
    }
    return (Response.status(Status.OK)
        .entity(userService.login(loginDTO))
        .build());
  }

  @POST
  @Path("/activation")
  @PermitAll
  public Response activation(@HeaderParam("activation-token") String validationToken,
      ActivationDto activationDto) {
    userService.activateUser(validationToken, activationDto);
    return Response.status(Response.Status.OK)
        .entity(new SuccessfulResponse(USER_ACCOUNT_VERIFICATION).toString())
        .build();
  }

  @POST
  @Path("/request-maintenance")
  @PermitAll
  public Response requestMaintenance(RequestMaintenanceDto requestMaintenanceDto) {
    requestMaintenanceValidationChain.isValid(requestMaintenanceDto);
    userService.sendMaintenanceRequest(requestMaintenanceDto);
    return (Response.status(Status.OK).build());
  }

  @GET
  @Path("/protected")
  @RolesAllowed({"DEVELOPER"})
  public Response getProtectedRequest(@Context SecurityContext securityContext) {
    AuthenticatedUserDetails principal = (AuthenticatedUserDetails) securityContext.getUserPrincipal();
    return Response.status(Status.ACCEPTED)
        .entity(principal)
        .build();
  }
}
