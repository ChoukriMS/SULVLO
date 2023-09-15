package ca.ulaval.glo4003.sulvlo.domain.travel;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

public record Travel(UUID travelId,
                     String userIdul,
                     String stationStartTravel,
                     LocalDateTime startTravelDate,
                     LocalDateTime endTravelDate,
                     String stationEndTravel,
                     Month month) {

}
