package com.augustojaml.appposts.config.auth.user.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredentialDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private String email;

  private String password;

  public CredentialDTO(CredentialDTO credent) {
    this.email = credent.getEmail();
    this.password = credent.getPassword();
  }
}
