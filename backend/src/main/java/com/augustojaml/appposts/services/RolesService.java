package com.augustojaml.appposts.services;

import java.util.List;
import java.util.stream.Collectors;

import com.augustojaml.appposts.dtos.RoleDTO;
import com.augustojaml.appposts.exceptions.services.ServiceObjectAlreadyExitsException;
import com.augustojaml.appposts.exceptions.services.ServiceObjectNotFoundException;
import com.augustojaml.appposts.models.RoleModel;
import com.augustojaml.appposts.repositories.RolesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolesService {

  @Autowired
  RolesRepository rolesRepository;

  public RoleModel findById(String id) {
    return rolesRepository.findById(id).orElseThrow(() -> new ServiceObjectNotFoundException("Role not found"));
  }

  public List<RoleModel> findAll() {
    List<RoleModel> users = this.rolesRepository.findAll();
    return users;
  }

  public RoleModel findByName(String name) {
    RoleModel role = this.rolesRepository.findByName(name);
    if (role != null) {
      throw new ServiceObjectAlreadyExitsException("Role already exists");
    }
    return role;
  }

  public RoleModel save(RoleModel roleModel) {
    this.findByName(roleModel.getName());
    RoleModel role = new RoleModel(roleModel);
    return this.rolesRepository.save(role);
  }

  public void delete(String id) {
    RoleModel roleModel = this.findById(id);
    this.rolesRepository.delete(roleModel);
  }

  public RoleModel update(String id, RoleModel fromDTO) {
    RoleModel roleById = this.findById(id);
    if (!roleById.getName().equals(fromDTO.getName())) {
      this.findByName(fromDTO.getName());
    }
    roleById.setName(fromDTO.getName());
    return this.rolesRepository.save(roleById);
  }

  public RoleModel fromDTO(RoleDTO roleDTO) {
    return new RoleModel(roleDTO.getId(), roleDTO.getName());
  }

  public List<RoleDTO> toDTO(List<RoleModel> roles) {
    return roles.stream().map(role -> new RoleDTO(role)).collect(Collectors.toList());
  }
}
