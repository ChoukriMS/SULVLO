package ca.ulaval.glo4003.sulvlo.api.user.dto;

public record RegisterDto(
    String name,
    String email,
    String idul,
    int age,
    String password,
    String birthDate,
    String gender
) implements EmailDto, IdulDto, PasswordDto {

}
