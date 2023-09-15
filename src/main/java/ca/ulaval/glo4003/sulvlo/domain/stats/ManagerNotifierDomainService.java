package ca.ulaval.glo4003.sulvlo.domain.stats;

import ca.ulaval.glo4003.sulvlo.domain.util.email.EmailSender;

public class ManagerNotifierDomainService {

  public static final String MANAGER_NOTIFICATION_EMAIL_SUBJECT = "Bike availabilities count for SULVLO";
  public static final String MANAGER_NOTIFICATION_EMAIL_BODY = "The avalaible bike count is currently null.";
  private static final String MANAGER_EMAIL = "ROTHSCHILD@nospam.today";
  private final EmailSender emailSender;

  public ManagerNotifierDomainService(EmailSender emailSender) {
    this.emailSender = emailSender;
  }


  public void sendEmailNotification() {
    emailSender.send(MANAGER_EMAIL, MANAGER_NOTIFICATION_EMAIL_BODY,
        MANAGER_NOTIFICATION_EMAIL_SUBJECT);
  }
}
