package com.augustojaml.appposts.config.auth.user;

import com.augustojaml.appposts.models.UserModel;
import com.augustojaml.appposts.repositories.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
class LoadUserSecurityDetail implements UserDetailsService {

  @Autowired
  UsersRepository usersRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserModel user = usersRepository.findByEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException(email);
    }
    return new UserSecurityDetail(user.getId(), user.getEmail(), user.getPassword(), user.getRoles());
  }
}
