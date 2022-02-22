package com.augustojaml.appposts.repositories;

import com.augustojaml.appposts.models.UserModel;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepository extends MongoRepository<UserModel, String> {
  UserModel findByEmail(String email);
}
