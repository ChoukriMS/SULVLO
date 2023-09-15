package ca.ulaval.glo4003.sulvlo.domain.util.semester;

import ca.ulaval.glo4003.sulvlo.api.subscription.dto.SubscriptionDto;


public class SemesterAssembler {

  public Semester create(SubscriptionDto subscriptionDto) {

    String semester = subscriptionDto.semester();
    String season = "";
    int year = 0;

    try {
      season = semester.substring(0, 1);
      year = Integer.parseInt(semester.substring(1, 3));
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(e);
    } catch (StringIndexOutOfBoundsException e) {
      System.out.println("String Index is out of bounds");
    }
    return new Semester(season, year);
  }


}

