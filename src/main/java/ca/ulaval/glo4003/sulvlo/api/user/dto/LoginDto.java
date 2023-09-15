package ca.ulaval.glo4003.sulvlo.api.user.dto;

public record LoginDto(
    String email,
    String password
) implements EmailDto, PasswordDto {

}
