package ca.ulaval.glo4003.sulvlo.application.service.travel;

import ca.ulaval.glo4003.sulvlo.application.service.travel.assembler.TravelAssembler;
import ca.ulaval.glo4003.sulvlo.application.service.travel.assembler.TravelHistoryAssembler;
import ca.ulaval.glo4003.sulvlo.application.service.travel.dto.TravelDto;
import ca.ulaval.glo4003.sulvlo.application.service.travel.dto.TravelHistorySummaryDTO;
import ca.ulaval.glo4003.sulvlo.domain.travel.Travel;
import ca.ulaval.glo4003.sulvlo.domain.travel.TravelRepository;
import ca.ulaval.glo4003.sulvlo.domain.travel.Travels;
import ca.ulaval.glo4003.sulvlo.domain.travel.history.TravelHistorySummary;
import ca.ulaval.glo4003.sulvlo.infrastructure.travel.exception.InvalidTravelIdException;
import jakarta.ws.rs.BadRequestException;
import java.time.Month;
import java.util.List;
import java.util.UUID;

public class TravelService {

  private final String ERROR_MESSAGE = "You have entered one or more invalid field, please try again!";
  private final TravelRepository travelRepository;
  private final TravelAssembler travelAssembler;
  private final TravelHistoryAssembler travelHistoryAssembler;


  public TravelService(TravelRepository travelRepository,
      TravelAssembler travelAssembler,
      TravelHistoryAssembler travelHistoryAssembler) {
    this.travelAssembler = travelAssembler;
    this.travelRepository = travelRepository;
    this.travelHistoryAssembler = travelHistoryAssembler;
  }

  public List<TravelDto> getAllFilteredByMonth(String actualMonth, String idul) {
    convertStringToMonth(actualMonth.toUpperCase());

    Month transferedMonth = Month.valueOf(actualMonth.toUpperCase());
    Travels travels = travelRepository.getAll(idul);

    return travels.filterByMonth(transferedMonth).stream()
        .map(travelAssembler::create).toList();
  }

  private void convertStringToMonth(String month) {
    try {
      Month.valueOf(month);
    } catch (Exception e) {
      throw new BadRequestException(ERROR_MESSAGE);
    }
  }

  public TravelDto getById(String travelID) throws InvalidTravelIdException {
    UUID travelUUID = validateStringToUUID(travelID);

    Travel travel = travelRepository.getById(travelUUID);
    return travelAssembler.create(travel);
  }

  public TravelHistorySummaryDTO getHistorySummary(String userId) {
    Travels travels = travelRepository.getAll(userId);
    TravelHistorySummary travelHistorySummary = travels.getHistorySummary(userId);

    return travelHistoryAssembler.create(travelHistorySummary);
  }

  private UUID validateStringToUUID(String id) throws InvalidTravelIdException {
    UUID uuid;
    try {
      uuid = UUID.fromString(id);
    } catch (IllegalArgumentException illegalArgumentException) {
      throw new InvalidTravelIdException();
    }
    return uuid;
  }
}
