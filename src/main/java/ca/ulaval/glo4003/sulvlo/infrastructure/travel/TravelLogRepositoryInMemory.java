package ca.ulaval.glo4003.sulvlo.infrastructure.travel;

import ca.ulaval.glo4003.sulvlo.domain.travel.Travel;
import ca.ulaval.glo4003.sulvlo.domain.travel.TravelRepository;
import ca.ulaval.glo4003.sulvlo.domain.travel.Travels;
import ca.ulaval.glo4003.sulvlo.infrastructure.travel.exception.InvalidTravelIdException;
import com.google.common.collect.Lists;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TravelLogRepositoryInMemory implements TravelRepository {

  private final HashMap<UUID, Travel> listTravel = new HashMap<>();

  public TravelLogRepositoryInMemory() {
    TravelDevDataFactory travelDevDataFactory = new TravelDevDataFactory();
    List<Travel> travels = travelDevDataFactory.createMockData();
    travels.forEach(this::save);
  }

  @Override
  public void save(Travel travel) {
    listTravel.put(travel.travelId(), travel);
  }

  @Override
  public Travels getAll(String userId) {
    return new Travels(userId, Lists.newArrayList(listTravel.values()));
  }

  @Override
  public Travel getById(UUID id) {
    if (listTravel.containsKey(id)) {
      return listTravel.get(id);
    }
    throw new InvalidTravelIdException();
  }

  @Override
  public int getListSize() {
    return listTravel.size();
  }
}
