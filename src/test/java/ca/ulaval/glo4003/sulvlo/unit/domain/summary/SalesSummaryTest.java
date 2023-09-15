package ca.ulaval.glo4003.sulvlo.unit.domain.summary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.sulvlo.domain.payment.PaymentProcessor;
import ca.ulaval.glo4003.sulvlo.domain.payment.payer.Payer;
import ca.ulaval.glo4003.sulvlo.domain.subscription.Subscription;
import ca.ulaval.glo4003.sulvlo.domain.subscription.type.SubscriptionType;
import ca.ulaval.glo4003.sulvlo.domain.summary.SalesSummary;
import ca.ulaval.glo4003.sulvlo.domain.util.email.EmailSender;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SalesSummaryTest {

  private static final String PREMIUM = "Premium";
  private static final int PRICE_PREMIUM = 50;
  private static final int PRICE_BASE = 30;
  private static final String BASE = "Base";
  private final SubscriptionType subscriptionType1 = new SubscriptionType(PREMIUM, PRICE_PREMIUM,
      "", 0);
  private final SubscriptionType subscriptionType2 = new SubscriptionType(BASE, PRICE_BASE, "", 0);
  @Mock
  PaymentProcessor paymentProcessor;
  Subscription subscription1 = new Subscription(subscriptionType1, BASE, null, null, false, false,
      false);
  Subscription subscription2 = new Subscription(subscriptionType2, BASE, null, null, false, false,
      false);
  @Mock
  private EmailSender emailSender;
  private final SalesSummary salesSummary = new SalesSummary(emailSender);
  private final Payer payer1 = new Payer("Payer1");
  private final Payer payer2 = new Payer("Payer2");
  private final Payer payer3 = new Payer("Payer3");
  private final Payer payer4 = new Payer("Payer4");

  @Test
  void givenSubscriptionType_whenAddSubscriptionType_thenGetBetterSubscriptionType() {
    salesSummary.addSubscriptionType(subscriptionType1);
    salesSummary.addSubscriptionType(subscriptionType1);
    salesSummary.addSubscriptionType(subscriptionType1);
    salesSummary.addSubscriptionType(subscriptionType2);
    assertEquals(salesSummary.getBetterSubscriptionTypeSale(), "Premium");
  }

  @Test
  void givenSubscriptionType_whenAddSubscriptionType_thenGetTotalSales() {
    salesSummary.addSubscriptionType(subscriptionType1);
    salesSummary.addSubscriptionType(subscriptionType2);
    assertEquals(salesSummary.getTotalSales(), new BigDecimal(80));
  }

  @Test
  void givenPayers_whenCalculateAverageSales_thenGetTotalSales() {
    List<Payer> payers = new ArrayList<Payer>();

    payer1.addExtraFees(new BigDecimal(PRICE_BASE));
    payer2.addExtraFees(new BigDecimal(PRICE_PREMIUM));
    payer3.addExtraFees(new BigDecimal(PRICE_BASE));
    payer4.addExtraFees(new BigDecimal(PRICE_PREMIUM));
    payers.add(payer1);
    payers.add(payer2);
    payers.add(payer3);
    payers.add(payer4);

    salesSummary.calculateAverageFee(payers);

    assertEquals(salesSummary.getAverageFees().toString(), "40.00");
  }

}
