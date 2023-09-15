package ca.ulaval.glo4003.sulvlo.infrastructure.payer;

import ca.ulaval.glo4003.sulvlo.domain.payment.payer.Payer;
import ca.ulaval.glo4003.sulvlo.domain.payment.payer.PayerRepository;
import ca.ulaval.glo4003.sulvlo.infrastructure.payer.exception.PayerAlreadyExistsException;
import ca.ulaval.glo4003.sulvlo.infrastructure.payer.exception.PayerNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayerRepositoryInMemory implements PayerRepository {

  Map<String, Payer> payers;

  public PayerRepositoryInMemory() {
    PayerDevDataFactory payerDevDataFactory = new PayerDevDataFactory();
    List<Payer> payersMock = payerDevDataFactory.createMockData();
    payers = new HashMap<>();
    payersMock.stream().forEach(payer -> save(payer));
  }

  @Override
  public void save(Payer payer) {
    if (payers.containsKey(payer.getIdul())) {
      throw new PayerAlreadyExistsException();
    }
    payers.put(payer.getIdul(), payer);
  }

  @Override
  public Payer findByIdul(String idul) {
    if (!payers.containsKey(idul)) {
      throw new PayerNotFoundException();
    }
    return payers.get(idul);
  }

  @Override
  public void update(Payer newPayer) {
    if (!payers.containsKey(newPayer.getIdul())) {
      throw new PayerNotFoundException();
    }
    payers.replace(newPayer.getIdul(), newPayer);
  }

  @Override
  public List<Payer> findAll() {
    return new ArrayList<Payer>(payers.values());
  }

}
