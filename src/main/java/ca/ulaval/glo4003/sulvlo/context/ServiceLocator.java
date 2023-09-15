package ca.ulaval.glo4003.sulvlo.context;

import ca.ulaval.glo4003.sulvlo.context.exception.ContractAlreadySavedException;
import ca.ulaval.glo4003.sulvlo.context.exception.UnresolvedContractException;
import java.util.HashMap;
import java.util.Map;


public class ServiceLocator {

  private static ServiceLocator locator;
  private final Map<Class<?>, Object> instances = new HashMap<>();

  public static ServiceLocator createInstance() {
    locator = new ServiceLocator();
    return locator;
  }

  public static ServiceLocator getInstance() {
    if (locator == null) {
      locator = new ServiceLocator();
    }
    return locator;
  }

  public <T> void register(Class<T> contract, T instance) {
    if (instances.containsKey(contract)) {
      throw new ContractAlreadySavedException(contract);
    }
    instances.put(contract, instance);
  }

  public <T> T resolve(Class<T> contract) {
    T instance = (T) instances.get(contract);
    if (instance == null) {
      throw new UnresolvedContractException(contract);
    }
    return instance;
  }

}