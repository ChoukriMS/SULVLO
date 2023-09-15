package ca.ulaval.glo4003.sulvlo.application.service.travel.assembler;

import ca.ulaval.glo4003.sulvlo.application.service.travel.dto.TravelDto;
import ca.ulaval.glo4003.sulvlo.domain.travel.Travel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TravelAssembler {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
      "yyyy-MM-dd HH:mm:ss");

  public TravelDto create(Travel travel) {
    return new TravelDto(travel.travelId().toString(), travel.userIdul(),
        travel.stationStartTravel(), dateFormat(travel.startTravelDate()),
        dateFormat(travel.endTravelDate()), travel.stationEndTravel(), travel.month());
  }

  private String dateFormat(LocalDateTime date) {
    return date.format(formatter);
  }

}
