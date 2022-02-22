package com.augustojaml.appposts.repositories;

import com.augustojaml.appposts.models.RoleModel;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RolesRepository extends MongoRepository<RoleModel, String> {
  RoleModel findByName(String name);
}
