package ca.ulaval.glo4003.sulvlo.infrastructure.subscription.type;

import ca.ulaval.glo4003.sulvlo.domain.util.semester.Semester;
import ca.ulaval.glo4003.sulvlo.infrastructure.config.FileSemesterDto;
import ca.ulaval.glo4003.sulvlo.infrastructure.config.JsonFileReader;
import java.time.LocalDate;
import java.util.List;

public class SemesterDevDataFactory {

  private final JsonFileReader jsonFileReader;
  private LocalDate end_date;
  private LocalDate start_date;

  public SemesterDevDataFactory(JsonFileReader jsonFileReader) {
    this.jsonFileReader = jsonFileReader;
  }

  public LocalDate getEndSemesterDate(Semester semester) {
    List<FileSemesterDto> fileSemesterDtoList = jsonFileReader.readSemester(
        "src/main/java/ca/ulaval/glo4003/sulvlo/infrastructure/config/semester-info.json");
    fileSemesterDtoList.forEach(dto -> {
      String season = dto.semester_code().substring(0, 1);
      int year = Integer.parseInt(dto.semester_code().substring(1, 3));
      if (semester.getYear() == year && semester.getSeason().equals(season)) {
        end_date = LocalDate.parse(dto.end_date());
      }
    });
    return end_date;
  }

  public LocalDate getStartSemesterDate(Semester semester) {
    List<FileSemesterDto> fileSemesterDtoList = jsonFileReader.readSemester(
        "src/main/java/ca/ulaval/glo4003/sulvlo/infrastructure/config/semester-info.json");
    fileSemesterDtoList.forEach(dto -> {
      String season = dto.semester_code().substring(0, 1);
      int year = Integer.parseInt(dto.semester_code().substring(1, 3));
      if (semester.getYear() == year && semester.getSeason().equals(season)) {
        start_date = LocalDate.parse(dto.start_date());
      }
    });
    return start_date;
  }


}
