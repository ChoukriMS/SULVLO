package ca.ulaval.glo4003.sulvlo.unit.api.stats;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.sulvlo.api.stats.StatsResource;
import ca.ulaval.glo4003.sulvlo.application.service.stats.StatsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class StatsResourceTest {

  @Mock
  private StatsService statsService;
  private StatsResource statsResource;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    statsResource = new StatsResource(statsService);
  }

  @Test
  void givenStatsService_WhenListAllBikesAvailabilities_ThenShouldFindAllBikesAvailabilitiesBeCalled() {
    statsResource.listAllBikesAvailabilities();

    verify(statsService, times(1)).findAllBikesAvailabilities();
  }

  @Test
  void givenStatsService_WhenListAllBikesAvailabilities2_ThenShouldVerifyCurrentBikesAvailabilitiesBeCalled() {
    statsResource.listAllBikesAvailabilities();

    verify(statsService, times(1)).verifyCurrentBikesAvailabilities();
  }
}