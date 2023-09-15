package ca.ulaval.glo4003.sulvlo.application.service.travel.dto;

public record TravelHistorySummaryDTO(String totalTravelsTime,
                                      String averageTravelTime,
                                      int numberOfTravels,
                                      String favoriteStation) {

}
