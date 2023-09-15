package ca.ulaval.glo4003.sulvlo.api.validation;

import ca.ulaval.glo4003.sulvlo.api.subscription.dto.SubscriptionDto;
import ca.ulaval.glo4003.sulvlo.api.validation.exception.InvalidSubscriptionParameterException;
import jakarta.ws.rs.BadRequestException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class CreditCardValidator {

  private static final Date date = new Date();
  private static final LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault())
      .toLocalDate();
  private static final int CURRENT_MONTH = localDate.getMonthValue();
  private static final int CURRENT_YEAR = localDate.getYear();
  private static final int MAX_MONTH = 12;
  private static final int MIN_MONTH = 1;
  private static final int MIN_CVV = 100;
  private static final int MAX_CVV = 999;
  private static final int CVV_NUMBERS_LENGTH = 16;


  private static void validateCreditCardNumbers(String numbers) throws BadRequestException {
    if (numbers == null || numbers.isBlank() || numbers.length() != CVV_NUMBERS_LENGTH) {
      throw new InvalidSubscriptionParameterException();
    }
  }

  private static void validateCreditCardExpiration(int month, int year) throws BadRequestException {
    if (month > MAX_MONTH || month < MIN_MONTH || year < CURRENT_YEAR) {
      throw new InvalidSubscriptionParameterException();
    }
    if (year == CURRENT_YEAR && month < CURRENT_MONTH) {
      throw new InvalidSubscriptionParameterException();
    }
  }

  private static void validateCreditCardCcv(int ccv) throws BadRequestException {
    if (ccv < MIN_CVV || ccv > MAX_CVV) {
      throw new InvalidSubscriptionParameterException();
    }
  }

  public static void validCreditCardDTO(SubscriptionDto dto) throws BadRequestException {
    validateCreditCardNumbers(dto.creditCardNumber());
    validateCreditCardExpiration(dto.expirationMonth(), dto.expirationYear());
    validateCreditCardCcv(dto.ccv());
  }
}
