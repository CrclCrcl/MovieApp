package com.example.commentcomandservice.repository;

import com.example.commentcomandservice.entity.Comment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;




public interface CommentRepository extends MongoRepository<Comment, ObjectId> {
    //boolean existsById(Long commentID);

}
