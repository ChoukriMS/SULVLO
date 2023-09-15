package ca.ulaval.glo4003.sulvlo.infrastructure.util.email;

import ca.ulaval.glo4003.sulvlo.domain.util.email.EmailSender;
import jakarta.validation.constraints.NotNull;
import java.text.MessageFormat;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailSenderImpl implements EmailSender {

  public static final String USERNAME = "choukriulaval@gmail.com";
  public static final String PASSWORD = "swcqbkdofefbjsff";
  public static final Logger LOGGER = LoggerFactory.getLogger(EmailSenderImpl.class);

  @Override
  public void send(String receiverEmail, String body, String subject) {
    sendEmail(receiverEmail, body, subject);
  }

  private void sendEmail(@NotNull String email, String body, String subject) {

    Properties props = new Properties();
    props.put("mail.smtp.auth", true);
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");
    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");

    Session session = Session.getInstance(props,
        new javax.mail.Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(USERNAME, PASSWORD);
          }
        });
    try {
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(USERNAME));
      message.setRecipients(Message.RecipientType.TO,
          InternetAddress.parse(email));
      message.setSubject(subject);
      message.setText(body);
      Transport.send(message);
      LOGGER.info(MessageFormat.format("Sending email to {0}", email));

    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
    LOGGER.info(MessageFormat.format("email is sent to {0}", email));
  }
}
