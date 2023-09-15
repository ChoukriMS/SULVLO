package ca.ulaval.glo4003.sulvlo.infrastructure.config;


import ca.ulaval.glo4003.sulvlo.infrastructure.exception.DataReadException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class JsonFileReader {

  public List<FileStationDto> read(String path) {
    List<FileStationDto> fileStationDtoList = null;
    try {
      ObjectMapper mapper = new ObjectMapper();
      fileStationDtoList =
          Arrays.asList(mapper.readValue(Paths.get(path).toFile(), FileStationDto[].class));
    } catch (Exception ex) {
      throw new DataReadException();
    }
    return fileStationDtoList;
  }

  public List<FileSemesterDto> readSemester(String path) {
    List<FileSemesterDto> fileStationDtoList = null;
    try {
      ObjectMapper mapper = new ObjectMapper();
      fileStationDtoList =
          Arrays.asList(mapper.readValue(Paths.get(path).toFile(), FileSemesterDto[].class));
    } catch (Exception ex) {
      throw new DataReadException();
    }
    return fileStationDtoList;
  }
}
