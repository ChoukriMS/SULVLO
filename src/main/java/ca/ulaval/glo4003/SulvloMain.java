package ca.ulaval.glo4003;

import ca.ulaval.glo4003.sulvlo.auth.jwt.TokenAuthentificationService;
import ca.ulaval.glo4003.sulvlo.auth.jwt.filter.AuthenticationFilter;
import ca.ulaval.glo4003.sulvlo.auth.jwt.filter.AuthorizationFilter;
import ca.ulaval.glo4003.sulvlo.context.ApiContext;
import ca.ulaval.glo4003.sulvlo.context.ApplicationBinder;
import ca.ulaval.glo4003.sulvlo.context.ServiceLocator;
import ca.ulaval.glo4003.sulvlo.domain.util.email.EmailSender;
import ca.ulaval.glo4003.sulvlo.infrastructure.util.email.EmailSenderImpl;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.glassfish.jersey.servlet.ServletContainer;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RESTApi setup without using DI or spring
 */
@SuppressWarnings("all")
public class SulvloMain {

  private static final int PORT = 8080;
  private static final Logger LOGGER = LoggerFactory.getLogger(SulvloMain.class);
  private final ServiceLocator serviceLocator = ServiceLocator.createInstance();

  private EmailSender emailSender = new EmailSenderImpl();
  private TokenAuthentificationService tokenAuthentificationService = new TokenAuthentificationService();

  private Server server;

  public static void main(String[] args) throws SchedulerException {
    new SulvloMain().run();
  }

  public void run() throws SchedulerException {
    serviceLocator.register(EmailSender.class, emailSender);
    serviceLocator.register(TokenAuthentificationService.class, tokenAuthentificationService);

    new ApiContext().applyContext();

    server = new Server(PORT);

    ServletContextHandler contextHandler = new ServletContextHandler(server, "/");
    final ResourceConfig config = new ResourceConfig();

    config.register(new ApplicationBinder());
    config.register(new AuthenticationFilter(tokenAuthentificationService));
    config.register(AuthorizationFilter.class);
    config.register(RolesAllowedDynamicFeature.class);
    config.packages("ca.ulaval.glo4003.sulvlo.api");

    ServletContainer container = new ServletContainer(config);
    ServletHolder servletHolder = new ServletHolder(container);
    contextHandler.addServlet(servletHolder, "/*");

    startServer();
  }

  public void startServer() {
    try {
      LOGGER.info("Setup http server");
      server.start();
      LOGGER.info("Application started.%nStop the application using CTRL+C");
    } catch (Exception e) {
      stopServer();
    }
  }

  public void stopServer() {
    try {
      LOGGER.info("Shutting down the application...");
      server.stop();
      LOGGER.info("Done, exit.");
    } catch (Exception e) {
      LOGGER.error("Error shutting down the server", e);
      server.destroy();
    }
  }

}
