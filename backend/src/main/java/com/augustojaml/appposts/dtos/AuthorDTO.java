package com.augustojaml.appposts.dtos;

import java.io.Serializable;

import com.augustojaml.appposts.models.UserModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AuthorDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  @EqualsAndHashCode.Include
  private String id;

  private String name;

  private String email;

  private String avatar;

  public AuthorDTO(AuthorDTO user) {
    this.id = user.getId();
    this.name = user.getName();
    this.email = user.getEmail();
    this.avatar = user.getAvatar();
  }

  public AuthorDTO(UserModel user) {
    this.id = user.getId();
    this.name = user.getName();
    this.email = user.getEmail();
    this.avatar = user.getAvatar();
  }

}
