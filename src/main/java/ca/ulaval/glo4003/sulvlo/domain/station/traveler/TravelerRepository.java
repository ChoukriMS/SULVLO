package ca.ulaval.glo4003.sulvlo.domain.station.traveler;

import java.util.List;

public interface TravelerRepository {

  List<Traveler> findAllTravelers();

  Traveler findByIdul(String idul);

  void save(Traveler traveler);

}
