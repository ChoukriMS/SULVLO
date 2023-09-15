package ca.ulaval.glo4003.sulvlo.api.validation;

public abstract class ValidationNode<T> {

  private ValidationNode next;

  public static ValidationNode link(ValidationNode first, ValidationNode... chain) {
    ValidationNode head = first;
    for (ValidationNode nextInChain : chain) {
      head.next = nextInChain;
      head = nextInChain;
    }
    return first;
  }

  public abstract boolean isValid(T model);

  protected boolean validNext(T model) {
    if (next == null) {
      return true;
    }
    return next.isValid(model);
  }

}
