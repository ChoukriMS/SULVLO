package ca.ulaval.glo4003.sulvlo.domain.travel;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

public class TravelFactory {

  public Travel create(String idul, String unlockStationCode, String returnStation,
      LocalDateTime initiateTravelTime, LocalDateTime endTravelTime, Month travelMonth) {

    return new Travel(UUID.randomUUID(), idul, unlockStationCode,
        initiateTravelTime, endTravelTime, returnStation,
        travelMonth);
  }
}
