package com.example.commentcomandservice.repository;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieIdRepository extends MongoRepository<String, ObjectId> {
    //boolean existsById(Long commentID);

}

