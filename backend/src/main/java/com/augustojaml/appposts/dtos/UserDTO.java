package com.augustojaml.appposts.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.augustojaml.appposts.models.RoleModel;
import com.augustojaml.appposts.models.UserModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  @EqualsAndHashCode.Include
  private String id;

  @NotEmpty(message = "Name required")
  private String name;

  @NotEmpty(message = "Email required")
  @Email(message = "Email invalid")
  private String email;

  @JsonIgnore
  // @NotEmpty(message = "Password required")
  private String password;

  @JsonIgnore
  private List<RoleModel> roles = new ArrayList<>();

  private String avatar;

  public UserDTO(List<RoleModel> roles) {
    this.roles = roles;
  }

  public UserDTO(UserModel user) {
    this.id = user.getId();
    this.name = user.getName();
    this.email = user.getEmail();
    this.password = user.getPassword();
    this.avatar = user.getAvatar();
    this.roles = user.getRoles();
  }

  public UserDTO(UserDTO user) {
    this.id = user.getId();
    this.name = user.getName();
    this.email = user.getEmail();
    this.password = user.getPassword();
    this.avatar = user.getAvatar();
    this.roles = user.getRoles();
  }

  public Set<String> getAuthorities() {
    Set<String> authorities = new HashSet<>();
    for (RoleModel authority : this.getRoles()) {
      authorities.add(authority.getName());
    }
    return authorities;
  }

}
