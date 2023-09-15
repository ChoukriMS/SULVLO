package ca.ulaval.glo4003.sulvlo.application.service.travel.dto;

import java.time.Month;

public record TravelDto(String Id,
                        String user_id,
                        String stationStartTravel,
                        String startTravelDate,
                        String endTravelDate,
                        String stationEndTravel,
                        Month month) {

}