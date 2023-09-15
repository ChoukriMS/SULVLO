package ca.ulaval.glo4003.sulvlo.domain.truck;

import java.util.List;

public interface TruckRepository {

  List<Truck> findAll();

  Truck findById(TruckId truckId);

  void save(Truck truck);
}
