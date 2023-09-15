package ca.ulaval.glo4003.sulvlo.api.user.dto;

public record RequestMaintenanceDto(String stationCode, String email) implements EmailDto {

}
