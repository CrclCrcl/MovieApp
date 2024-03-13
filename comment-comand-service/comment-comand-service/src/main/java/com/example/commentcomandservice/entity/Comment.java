package com.example.commentcomandservice.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

import org.hibernate.type.descriptor.jdbc.UUIDJdbcType;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Document(collation = "comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JdbcType(VarcharJdbcType.class)
    private ObjectId commentID;
    private String content;
    private UUID userID;
    private UUID movieID;
    private String username;
    List<Comment> replies;

}
