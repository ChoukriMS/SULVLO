package ca.ulaval.glo4003.sulvlo.domain.util.email;

public interface EmailSender {

  void send(String receiverEmail, String body, String subject);
}
