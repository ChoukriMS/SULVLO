package ca.ulaval.glo4003.sulvlo.context.exception;

public class ContractAlreadySavedException extends RuntimeException {

  public <T> ContractAlreadySavedException(Class<T> contract) {
    super("A service implementation was already provided for " + contract.getName());
  }
}