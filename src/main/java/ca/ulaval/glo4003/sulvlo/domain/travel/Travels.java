package ca.ulaval.glo4003.sulvlo.domain.travel;

import ca.ulaval.glo4003.sulvlo.domain.travel.exception.InvalidUserIdException;
import ca.ulaval.glo4003.sulvlo.domain.travel.history.TravelHistoryFactory;
import ca.ulaval.glo4003.sulvlo.domain.travel.history.TravelHistorySummary;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Travels {

  private final List<Travel> travellist;
  private final TravelHistoryFactory travelHistoryFactory;

  public Travels(@NotNull String userId, @NotNull List<Travel> travellist) {
    this.travellist = travellist.stream().filter(
        travel -> travel.userIdul().equals(userId)).toList();
    this.travelHistoryFactory = new TravelHistoryFactory();
  }

  private static void throwIfTravelIsNotFound(List<Travel> filteredTravels) {
    if (filteredTravels.isEmpty()) {
      throw new InvalidUserIdException();
    }
  }

  public List<Travel> filterByMonth(Month month) {

    return this.travellist.stream().filter(travel ->
        travel.month().equals(month)).toList();
  }

  public TravelHistorySummary getHistorySummary(String userId) {
    List<Travel> filteredTravels = this.travellist.stream().filter
        (travel -> travel.userIdul().equals(userId)).toList();

    throwIfTravelIsNotFound(filteredTravels);

    long totalTime = getTotalTime(filteredTravels);
    String favoriteStation = getFavoriteStation(filteredTravels);
    String totalTimeInMilliSecond = millisecondsToTime(totalTime);
    String averageTimeInMillisecond = millisecondsToTime(totalTime / filteredTravels.size());

    return travelHistoryFactory.create(totalTimeInMilliSecond,
        averageTimeInMillisecond,
        filteredTravels.size(),
        favoriteStation);
  }

  public long getTotalTime(List<Travel> travelList) {
    long total = 0;

    for (Travel travel : travelList) {

      LocalDateTime dateOne = travel.startTravelDate();
      LocalDateTime dateTwo = travel.endTravelDate();
      total += Duration.between(dateOne, dateTwo).toMillis();

    }
    return total;
  }

  public String millisecondsToTime(long milliseconds) {
    long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds);
    long removeMilliseconds = TimeUnit.MINUTES.toMillis(minutes);
    long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds - removeMilliseconds);
    String secondsStr = Long.toString(seconds);
    String secs = (secondsStr.length() >= 2) ? secondsStr.substring(0, 2) : "0" + secondsStr;
    return minutes + ":" + secs;
  }

  public String getFavoriteStation(List<Travel> listOfTravels) {

    List<String> listOfStationsVisited = getAllStationsVisited(listOfTravels);

    return listOfStationsVisited.stream()
        .collect(Collectors.groupingBy(w -> w, Collectors.counting()))
        .entrySet()
        .stream()
        .max(Map.Entry.comparingByValue())
        .get()
        .getKey();
  }

  public List<String> getAllStationsVisited(List<Travel> listOfTravels) {
    List<String> listOfStationsVisited = new ArrayList<>();

    for (Travel travel : listOfTravels) {
      listOfStationsVisited.add(travel.stationStartTravel());
      listOfStationsVisited.add(travel.stationEndTravel());
    }
    return listOfStationsVisited;
  }

  public List<Travel> getAllFromUser() {
    return this.travellist;
  }
}
