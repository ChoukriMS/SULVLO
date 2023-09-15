package ca.ulaval.glo4003.sulvlo.domain.user;

import java.util.List;

public interface UserRepository {

  void save(User user);

  User getByEmail(String email);

  User getByIdul(String idul);

  List<User> getUsersByType(UserType userType);
}
