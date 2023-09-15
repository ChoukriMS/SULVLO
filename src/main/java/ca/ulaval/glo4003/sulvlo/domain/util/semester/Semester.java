package ca.ulaval.glo4003.sulvlo.domain.util.semester;

public class Semester {

  private final String season;
  private final int year;


  public Semester(String season, int year) {
    this.season = season;
    this.year = year;
  }

  public String getSeason() {
    return season;
  }

  public int getYear() {
    return year;
  }


}
