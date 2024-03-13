package com.example.commentcomandservice.exceptions;

import org.bson.types.ObjectId;

public class CommentNotFoundException extends Throwable{

    public CommentNotFoundException(ObjectId commentID) {super("Comment with id" + commentID + "not found");}
}
