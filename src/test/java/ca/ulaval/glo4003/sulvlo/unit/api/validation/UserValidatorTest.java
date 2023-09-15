package ca.ulaval.glo4003.sulvlo.unit.api.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.ulaval.glo4003.sulvlo.api.user.dto.LoginDto;
import ca.ulaval.glo4003.sulvlo.api.user.dto.RegisterDto;
import ca.ulaval.glo4003.sulvlo.api.validation.Nodes.EmailValidation;
import ca.ulaval.glo4003.sulvlo.api.validation.Nodes.IdulValidation;
import ca.ulaval.glo4003.sulvlo.api.validation.Nodes.PasswordValidation;
import ca.ulaval.glo4003.sulvlo.api.validation.Nodes.Register.AgeValidation;
import ca.ulaval.glo4003.sulvlo.api.validation.Nodes.Register.BirthDateValidation;
import ca.ulaval.glo4003.sulvlo.api.validation.Nodes.Register.GenderValidation;
import ca.ulaval.glo4003.sulvlo.api.validation.Nodes.Register.UserNameValidation;
import ca.ulaval.glo4003.sulvlo.api.validation.ValidationNode;
import org.junit.jupiter.api.Test;

class UserValidatorTest {

  private final String NAME = "test";
  private final String TOO_LONG_NAME = "testestestestestestest";
  private final String IDUL = "test";
  private final int AGE = 11;
  private final int NEGATIVE_AGE = -11;
  private final int OVER_AGE = 123;
  private final String BIRTH_DATE = "02/02/2002";
  private final String WRONG_BIRTH_DATE = "02/42/2002";
  private final String GENDER = "Male";
  private final String MINUS_GENDER = "female";
  private final String UP_GENDER = "OTHER";
  private final String WRONG_GENDER = "WhalE";
  private final String EMAIL = "test@hotmail.com";
  private final String WRONG_EMAIL = "test@hotmail";
  private final String PASSWORD = "test";
  private final String BLANK = "";

  private final ValidationNode<RegisterDto> registerValidationChain = ValidationNode.link(
      new UserNameValidation(),
      new IdulValidation(),
      new AgeValidation(),
      new EmailValidation(),
      new PasswordValidation(),
      new BirthDateValidation(),
      new GenderValidation()
  );

  private final ValidationNode<LoginDto> loginValidationChain = ValidationNode.link(
      new EmailValidation(),
      new PasswordValidation()
  );

  @Test
  void givenValidRegisterDtoWhenVerifyRegisterDtoThenDoNothing() {
    RegisterDto registerDto = new RegisterDto(NAME, EMAIL, IDUL, AGE, PASSWORD, BIRTH_DATE, GENDER);

    assertTrue(registerValidationChain.isValid(registerDto));
  }

  @Test
  void givenRegisterDtoWithTooLongNameWhenVerifyRegisterDtoThenThrowInvalidUserParameterException() {
    RegisterDto registerDto = new RegisterDto(TOO_LONG_NAME, EMAIL, IDUL, AGE, PASSWORD, BIRTH_DATE,
        GENDER);

    assertFalse(registerValidationChain.isValid(registerDto));
  }

  @Test
  void givenRegisterDtoWithBlankNameWhenVerifyRegisterDtoThenThrowInvalidUserParameterException() {
    RegisterDto registerDto = new RegisterDto(BLANK, EMAIL, IDUL, AGE, PASSWORD, BIRTH_DATE,
        GENDER);

    assertFalse(registerValidationChain.isValid(registerDto));
  }

  @Test
  void givenRegisterDtoWithNullNameWhenVerifyRegisterDtoThenThrowInvalidUserParameterException() {
    RegisterDto registerDto = new RegisterDto(null, EMAIL, IDUL, AGE, PASSWORD, BIRTH_DATE, GENDER);

    assertFalse(registerValidationChain.isValid(registerDto));
  }

  @Test
  void givenRegisterDtoWithBlankIdulWhenVerifyRegisterDtoThenThrowInvalidUserParameterException() {
    RegisterDto registerDto = new RegisterDto(NAME, EMAIL, BLANK, AGE, PASSWORD, BIRTH_DATE,
        GENDER);

    assertFalse(registerValidationChain.isValid(registerDto));
  }

  @Test
  void givenRegisterDtoWithNullIdulWhenVerifyRegisterDtoThenThrowInvalidUserParameterException() {
    RegisterDto registerDto = new RegisterDto(NAME, EMAIL, null, AGE, PASSWORD, BIRTH_DATE, GENDER);

    assertFalse(registerValidationChain.isValid(registerDto));
  }

  @Test
  void givenRegisterDtoWithNegativeAgeWhenVerifyRegisterDtoThenThrowInvalidUserParameterException() {
    RegisterDto registerDto = new RegisterDto(NAME, EMAIL, IDUL, NEGATIVE_AGE, PASSWORD, BIRTH_DATE,
        GENDER);

    assertFalse(registerValidationChain.isValid(registerDto));
  }

  @Test
  void givenRegisterDtoWithOverAgeWhenVerifyRegisterDtoThenThrowInvalidUserParameterException() {
    RegisterDto registerDto = new RegisterDto(NAME, EMAIL, IDUL, OVER_AGE, PASSWORD, BIRTH_DATE,
        GENDER);

    assertFalse(registerValidationChain.isValid(registerDto));
  }

  @Test
  void givenRegisterDtoWithWrongBirthDateWhenVerifyRegisterDtoThenThrowInvalidUserParameterException() {
    RegisterDto registerDto = new RegisterDto(NAME, EMAIL, IDUL, AGE, PASSWORD, WRONG_BIRTH_DATE,
        GENDER);

    assertFalse(registerValidationChain.isValid(registerDto));
  }

  @Test
  void givenRegisterDtoWithBlankBirthDateWhenVerifyRegisterDtoThenThrowInvalidUserParameterException() {
    RegisterDto registerDto = new RegisterDto(NAME, EMAIL, IDUL, AGE, PASSWORD, BLANK, GENDER);

    assertFalse(registerValidationChain.isValid(registerDto));
  }

  @Test
  void givenRegisterDtoWithNullBirthDateWhenVerifyRegisterDtoThenThrowInvalidUserParameterException() {
    RegisterDto registerDto = new RegisterDto(NAME, EMAIL, IDUL, AGE, PASSWORD, null, GENDER);

    assertFalse(registerValidationChain.isValid(registerDto));
  }

  @Test
  void givenValidRegisterDtoWithMinusGenderWhenVerifyRegisterDtoThenDoNothing() {
    RegisterDto registerDto = new RegisterDto(NAME, EMAIL, IDUL, AGE, PASSWORD, BIRTH_DATE,
        MINUS_GENDER);

    assertTrue(registerValidationChain.isValid(registerDto));
  }

  @Test
  void givenValidRegisterDtoWithUpperGenderWhenVerifyRegisterDtoThenDoNothing() {
    RegisterDto registerDto = new RegisterDto(NAME, EMAIL, IDUL, AGE, PASSWORD, BIRTH_DATE,
        UP_GENDER);

    assertTrue(registerValidationChain.isValid(registerDto));
  }

  @Test
  void givenRegisterDtoWithWrongGenderWhenVerifyRegisterDtoThenThrowInvalidUserParameterException() {
    RegisterDto registerDto = new RegisterDto(NAME, EMAIL, IDUL, AGE, PASSWORD, BIRTH_DATE,
        WRONG_GENDER);

    assertFalse(registerValidationChain.isValid(registerDto));
  }

  @Test
  void givenRegisterDtoWithBlankGenderWhenVerifyRegisterDtoThenThrowInvalidUserParameterException() {
    RegisterDto registerDto = new RegisterDto(NAME, EMAIL, IDUL, AGE, PASSWORD, BIRTH_DATE, BLANK);

    assertFalse(registerValidationChain.isValid(registerDto));
  }

  @Test
  void givenRegisterDtoWithNullGenderWhenVerifyRegisterDtoThenThrowInvalidUserParameterException() {
    RegisterDto registerDto = new RegisterDto(NAME, EMAIL, IDUL, AGE, PASSWORD, BIRTH_DATE, null);

    assertFalse(registerValidationChain.isValid(registerDto));
  }

  @Test
  void givenRegisterDtoWithWrongEmailWhenVerifyRegisterDtoThenThrowInvalidUserParameterException() {
    RegisterDto registerDto = new RegisterDto(NAME, WRONG_EMAIL, IDUL, AGE, PASSWORD, BIRTH_DATE,
        GENDER);

    assertFalse(registerValidationChain.isValid(registerDto));
  }

  @Test
  void givenRegisterDtoWithBlankEmailWhenVerifyRegisterDtoThenThrowInvalidUserParameterException() {
    RegisterDto registerDto = new RegisterDto(NAME, BLANK, IDUL, AGE, PASSWORD, BIRTH_DATE, GENDER);

    assertFalse(registerValidationChain.isValid(registerDto));
  }

  @Test
  void givenRegisterDtoWithNullEmailWhenVerifyRegisterDtoThenThrowInvalidUserParameterException() {
    RegisterDto registerDto = new RegisterDto(NAME, null, IDUL, AGE, PASSWORD, BIRTH_DATE, GENDER);

    assertFalse(registerValidationChain.isValid(registerDto));
  }

  @Test
  void givenRegisterDtoWithBlankPasswordWhenVerifyRegisterDtoThenThrowInvalidUserParameterException() {
    RegisterDto registerDto = new RegisterDto(NAME, EMAIL, IDUL, AGE, BLANK, BIRTH_DATE, GENDER);

    assertFalse(registerValidationChain.isValid(registerDto));
  }

  @Test
  void givenRegisterDtoWithNullPasswordWhenVerifyRegisterDtoThenThrowInvalidUserParameterException() {
    RegisterDto registerDto = new RegisterDto(NAME, EMAIL, IDUL, AGE, null, BIRTH_DATE, GENDER);

    assertFalse(registerValidationChain.isValid(registerDto));
  }

  @Test
  void givenValidLoginDtoWhenVerifyLoginDtoThenDoNothing() {
    LoginDto loginDto = new LoginDto(EMAIL, PASSWORD);

    assertTrue(loginValidationChain.isValid(loginDto));
  }
}
