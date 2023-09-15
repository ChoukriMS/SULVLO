package ca.ulaval.glo4003.sulvlo.domain.stats;

import ca.ulaval.glo4003.sulvlo.api.stats.dto.StatsDto;

public class StatsAssembler {

  public StatsDto create(int numberOfAvailableBikes, int numberOfTakenBikes) {
    return new StatsDto(numberOfAvailableBikes, numberOfTakenBikes);
  }
}
