package ca.ulaval.glo4003.sulvlo.domain.travel.history;

public class TravelHistoryFactory {

  public TravelHistorySummary create(String totalTime, String averageTime, int numberOfTravel,
      String favoriteStation) {
    return new TravelHistorySummary(totalTime, averageTime, numberOfTravel, favoriteStation);
  }

}
