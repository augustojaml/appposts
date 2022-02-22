package com.augustojaml.appposts.repositories;

import com.augustojaml.appposts.models.PostModel;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends MongoRepository<PostModel, String> {

}
