package ca.ulaval.glo4003.sulvlo.api.user.dto;

public record LoginTokenDto(
    String token,
    String expireIn) {

}
