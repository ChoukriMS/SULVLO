package ca.ulaval.glo4003.sulvlo.infrastructure.travel;

import ca.ulaval.glo4003.sulvlo.domain.travel.Travel;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class TravelDevDataFactory {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
      "yyyy-MM-dd HH:mm:ss");

  public List<Travel> createMockData() {
    Travel firstTravel = new Travel(UUID.randomUUID(), "user69", "TEST2",
        LocalDateTime.parse("2022-01-01 05:30:00", formatter),
        LocalDateTime.parse("2022-01-01 06:00:00", formatter),
        "TEST2", Month.OCTOBER);
    Travel secondTravel = new Travel(UUID.randomUUID(), "user69", "TEST777",
        LocalDateTime.parse("2022-01-10 08:00:00", formatter),
        LocalDateTime.parse("2022-01-10 08:40:00", formatter),
        "TEST2", Month.OCTOBER);
    Travel thirdTravel = new Travel(UUID.randomUUID(), "user69", "TEST777",
        LocalDateTime.parse("2022-01-11 08:00:00", formatter),
        LocalDateTime.parse("2022-01-11 08:30:00", formatter),
        "TEST2", Month.OCTOBER);
    Travel fourthTravel = new Travel(UUID.randomUUID(), "user69", "TEST777",
        LocalDateTime.parse("2022-01-10 18:00:00", formatter),
        LocalDateTime.parse("2022-01-10 18:30:00", formatter),
        "TEST2", Month.OCTOBER);
    Travel fifthTravel = new Travel(UUID.randomUUID(), "user69", "TEST777",
        LocalDateTime.parse("2022-01-17 18:00:00", formatter),
        LocalDateTime.parse("2022-01-17 18:30:00", formatter),
        "TEST2", Month.OCTOBER);
    Travel sixthTravel = new Travel(UUID.randomUUID(), "user69", "TEST777",
        LocalDateTime.parse("2022-01-10 18:00:00", formatter),
        LocalDateTime.parse("2022-01-10 18:30:00", formatter),
        "TEST777", Month.OCTOBER);
    Travel seventhTravel = new Travel(UUID.randomUUID(), "user69", "TEST777",
        LocalDateTime.parse("2022-01-10 18:00:00", formatter),
        LocalDateTime.parse("2022-01-10 18:30:00", formatter),
        "TEST777", Month.APRIL);
    Travel eightTravel = new Travel(UUID.randomUUID(), "2", "TEST777",
        LocalDateTime.parse("2022-01-10 18:00:00", formatter),
        LocalDateTime.parse("2022-01-10 18:30:00", formatter),
        "TEST777", Month.DECEMBER);

    return List.of(firstTravel, secondTravel, thirdTravel, fourthTravel, fifthTravel, sixthTravel,
        seventhTravel, eightTravel);
  }
}
