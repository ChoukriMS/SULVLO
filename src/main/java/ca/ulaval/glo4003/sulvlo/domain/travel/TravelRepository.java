package ca.ulaval.glo4003.sulvlo.domain.travel;

import ca.ulaval.glo4003.sulvlo.infrastructure.travel.exception.InvalidTravelIdException;
import java.util.UUID;

public interface TravelRepository {

  void save(Travel travel);

  Travels getAll(String userId);

  Travel getById(UUID ID) throws InvalidTravelIdException;

  int getListSize();

}
