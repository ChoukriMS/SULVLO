package ca.ulaval.glo4003.sulvlo.domain.payment.service.schoolFeesService;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Enrollment {

  public final String idul;
  public final Boolean fullTime;

  public Enrollment(@JsonProperty("idul") String idul,
      @JsonProperty("full-time") Boolean fullTime) {
    this.idul = idul;
    this.fullTime = fullTime;
  }
}
