package com.augustojaml.appposts.config.auth.user;

import com.augustojaml.appposts.exceptions.services.ServiceObjectNotFoundException;

import org.springframework.security.core.context.SecurityContextHolder;

public class UserSecurityDetailAuthenticated {

  public static UserSecurityDetail authenticated() {
    try {
      return (UserSecurityDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    } catch (Exception e) {
      throw new ServiceObjectNotFoundException("Access Denied, check your credentials");
    }
  }

}
