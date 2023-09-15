package ca.ulaval.glo4003.sulvlo.domain.station;

import java.util.List;

public interface StationRepository {

  List<Station> findAllAvailable();

  List<Station> findAllUnderMaintenance();

  List<Station> findAllStations();

  Station findAvailableByCode(String code);

  Station findUnderMaintenanceByCode(String code);

  void saveAvailable(Station station);

  void saveUnderMaintenance(Station station);

  void deleteAvailable(String code);

  void deleteUnderMaintenance(String code);

}
