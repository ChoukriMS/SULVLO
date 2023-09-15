package ca.ulaval.glo4003.sulvlo.infrastructure.user;

import ca.ulaval.glo4003.sulvlo.domain.user.User;
import ca.ulaval.glo4003.sulvlo.domain.user.UserRepository;
import ca.ulaval.glo4003.sulvlo.domain.user.UserType;
import ca.ulaval.glo4003.sulvlo.infrastructure.user.exception.UserNotFoundException;
import jakarta.ws.rs.NotFoundException;
import java.util.HashMap;
import java.util.List;

public class UserRepositoryInMemory implements UserRepository {

  private final HashMap<String, User> listUser = new HashMap<>();

  public UserRepositoryInMemory() {
    UserDevDataFactory userDevDataFactory = new UserDevDataFactory();
    List<User> users = userDevDataFactory.createMockData();
    users.stream().forEach(user -> save(user));
  }

  @Override
  public void save(User user) {
    listUser.put(user.getIdul(), user);
  }

  @Override
  public User getByEmail(String email) {
    for (User user : listUser.values()) {
      if (user.getEmail().equals(email)) {
        return user;
      }
    }
    throw new UserNotFoundException();
  }

  @Override
  public List<User> getUsersByType(UserType userType) {
    return listUser.values().stream().filter(user -> user.getUserType().equals(userType)).toList();
  }

  @Override
  public User getByIdul(String idul) {
    if (listUser.containsKey(idul)) {
      return listUser.get(idul);
    }
    throw new NotFoundException("Idul does not exist");
  }
}
