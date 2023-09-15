package ca.ulaval.glo4003.sulvlo.domain.summary;

import ca.ulaval.glo4003.sulvlo.domain.payment.payer.Payer;
import ca.ulaval.glo4003.sulvlo.domain.subscription.type.SubscriptionType;
import ca.ulaval.glo4003.sulvlo.domain.util.email.EmailSender;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesSummary {

  private BigDecimal totalSales;
  private BigDecimal averageFees;
  private final HashMap<String, BigDecimal> subscriptionTypesCount;
  private final EmailSender emailSender;

  public SalesSummary(EmailSender emailSender) {
    this.emailSender = emailSender;
    this.totalSales = BigDecimal.ZERO;
    this.averageFees = BigDecimal.ZERO;
    this.subscriptionTypesCount = new HashMap<String, BigDecimal>();
  }

  public BigDecimal getAverageFees() {
    return this.averageFees;
  }

  public BigDecimal getTotalSales() {
    return this.totalSales;
  }

  public void calculateAverageFee(List<Payer> payers) {
    for (Payer payer : payers) {
      averageFees = averageFees.add(payer.getExtraFeesAmount());
    }
    if (payers.size() != 0) {
      averageFees = averageFees.divide(new BigDecimal(payers.size()), 2, RoundingMode.HALF_EVEN);
    }
  }

  private void addSale(int price) {
    totalSales = totalSales.add(new BigDecimal(price));
  }

  public void addSubscriptionType(SubscriptionType type) {
    if (subscriptionTypesCount.containsKey(type.type())) {
      BigDecimal value = subscriptionTypesCount.get(type.type());

      value = value.add(BigDecimal.ONE);
      subscriptionTypesCount.put(type.type(), value);
    } else {
      subscriptionTypesCount.put(type.type(), BigDecimal.ONE);
    }
    addSale(type.price());
  }

  public String getBetterSubscriptionTypeSale() {
    String type = "";

    for (Map.Entry<String, BigDecimal> subscriptionTypeCount : subscriptionTypesCount.entrySet()) {
      if (type.isBlank()) {
        type = subscriptionTypeCount.getKey();
      }
      if (subscriptionTypeCount.getValue().compareTo(subscriptionTypesCount.get(type)) > 0) {
        type = subscriptionTypeCount.getKey();
      }
    }
    return type;
  }

  public void sendSaleSummary(String email) {
    String body = String.format(
        "Sommaire des ventes. Fin du mois. Nombre total des ventes: %d$CAD.\nLa passe la plus vendue est %s.\nLe nombre moyen de frais supplémentaires par utilisateur par mois est %d$CAD.",
        getTotalSales().intValue(),
        getBetterSubscriptionTypeSale(),
        getAverageFees().intValue());

    emailSender.send(email, body, "Sommaire SULVLO");
  }
}
