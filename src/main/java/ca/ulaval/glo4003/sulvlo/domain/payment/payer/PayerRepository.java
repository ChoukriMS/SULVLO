package ca.ulaval.glo4003.sulvlo.domain.payment.payer;

import java.util.List;

public interface PayerRepository {

  void save(Payer payer);

  Payer findByIdul(String idul);

  void update(Payer newPayer);

  List<Payer> findAll();

}
