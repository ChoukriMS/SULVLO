package ca.ulaval.glo4003.sulvlo.unit.domain.station.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.sulvlo.domain.station.UnlockBikeToken;
import ca.ulaval.glo4003.sulvlo.domain.station.exception.InvalidTokenException;
import ca.ulaval.glo4003.sulvlo.domain.station.service.UniqueCodeDomainService;
import ca.ulaval.glo4003.sulvlo.domain.util.email.EmailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UniqueCodeDomainServiceTest {

  private static final String INVALID_CODE = "THI_IS_A_TEST";
  @Mock
  private EmailSender emailSender;
  private UniqueCodeDomainService uniqueCodeDomainService;

  @BeforeEach
  void setUp() {
    uniqueCodeDomainService = new UniqueCodeDomainService(emailSender);
  }

  @Test
  void whenCreatingNewUnlockBikeToken_thenUnlockBikeTokenIsReturned() {
    UnlockBikeToken unlockBikeToken = uniqueCodeDomainService.generateUnlockBikeToken();

    assertNotNull(unlockBikeToken);
  }

  @Test
  void givenAInvalidCode_whenValidatingCode_thenInvalidTokenException() {
    assertThrows(InvalidTokenException.class, () -> {
      uniqueCodeDomainService.isTokenValid(INVALID_CODE);
    });
  }

  @Test
  void givenAUniqueCode_whenValidationCode_thenNoExceptionIsThrown() {
    UnlockBikeToken unlockBikeToken = uniqueCodeDomainService.generateUnlockBikeToken();

    assertDoesNotThrow(() -> uniqueCodeDomainService.isTokenValid(unlockBikeToken.code()));

  }
}
