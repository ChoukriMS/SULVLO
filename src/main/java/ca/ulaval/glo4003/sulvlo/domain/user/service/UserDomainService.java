package ca.ulaval.glo4003.sulvlo.domain.user.service;

import ca.ulaval.glo4003.sulvlo.domain.user.User;
import ca.ulaval.glo4003.sulvlo.domain.user.UserRepository;

public class UserDomainService {

  private final UserRepository userRepository;

  public UserDomainService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User findUserByIdul(String idul) {
    return userRepository.getByIdul(idul);
  }

  public User findByEmail(String email) {
    return userRepository.getByEmail(email);
  }

  public void blockAccount(String idul) {
    User user = userRepository.getByIdul(idul);

    user.blockAccount();
  }


  public void unblockAccount(String idul) {
    User user = userRepository.getByIdul(idul);

    user.unblockAccount();
  }

  public boolean isUserAccountVerified(String idul) {
    User user = userRepository.getByIdul(idul);

    return user.isAccountVerified();
  }

  public String getEmailAddressFromIdul(String idul) {
    return userRepository.getByIdul(idul).getEmail();
  }
}
