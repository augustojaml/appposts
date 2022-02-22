package com.augustojaml.appposts.config.auth.user;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.augustojaml.appposts.models.RoleModel;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserSecurityDetail implements UserDetails {

  private String id;

  private String email;

  private String password;

  private Collection<? extends GrantedAuthority> authorities;

  public UserSecurityDetail(String id, String email, String password,
      List<RoleModel> roles) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toList());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  public String getId() {
    return this.id;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
