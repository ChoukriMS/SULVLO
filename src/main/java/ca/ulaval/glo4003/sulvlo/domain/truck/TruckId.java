package ca.ulaval.glo4003.sulvlo.domain.truck;

import ca.ulaval.glo4003.sulvlo.domain.truck.exception.InvalidTruckIdException;
import java.util.Objects;
import java.util.UUID;

public class TruckId {

  private final UUID value;

  private TruckId(UUID value) {
    this.value = value;
  }

  public TruckId() {
    this.value = UUID.randomUUID();
  }

  public static TruckId fromString(String truckId) {
    try {
      return new TruckId(UUID.fromString(truckId));
    } catch (Exception exception) {
      throw new InvalidTruckIdException();
    }
  }

  public String getValue() {
    return value.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TruckId truckId = (TruckId) o;
    return Objects.equals(value, truckId.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
