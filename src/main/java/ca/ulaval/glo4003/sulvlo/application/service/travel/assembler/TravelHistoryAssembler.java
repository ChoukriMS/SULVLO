package ca.ulaval.glo4003.sulvlo.application.service.travel.assembler;

import ca.ulaval.glo4003.sulvlo.application.service.travel.dto.TravelHistorySummaryDTO;
import ca.ulaval.glo4003.sulvlo.domain.travel.history.TravelHistorySummary;

public class TravelHistoryAssembler {

  public TravelHistorySummaryDTO create(TravelHistorySummary travelHistorySummary) {
    return new TravelHistorySummaryDTO(
        travelHistorySummary.totalTravelsTime(),
        travelHistorySummary.averageTravelTime(),
        travelHistorySummary.numberOfTravels(),
        travelHistorySummary.favoriteStation());
  }
}
