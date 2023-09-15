package ca.ulaval.glo4003.sulvlo.domain.user.information;

import java.time.LocalDate;
import java.util.UUID;

public record UserInformation(
    String name,
    String email,
    int age,
    LocalDate birthdate,
    Gender gender,
    UUID userId,
    int password
) {

}
