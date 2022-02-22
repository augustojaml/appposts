package com.augustojaml.appposts.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.augustojaml.appposts.models.RoleModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RoleDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  @EqualsAndHashCode.Include
  private String id;

  @NotEmpty(message = "Name required")
  private String name;

  public RoleDTO(RoleDTO role) {
    this.id = role.getId();
    this.name = role.getName();
  }

  public RoleDTO(RoleModel role) {
    this.id = role.getId();
    this.name = role.getName();
  }
}
