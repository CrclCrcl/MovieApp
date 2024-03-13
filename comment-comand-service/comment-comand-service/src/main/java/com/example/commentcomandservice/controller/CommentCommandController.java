package com.example.commentcomandservice.controller;

import com.example.commentcomandservice.dto.CommentEvent;
import com.example.commentcomandservice.entity.Comment;
import com.example.commentcomandservice.exceptions.CommentNotFoundException;
import com.example.commentcomandservice.service.CommentService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
@RequestMapping("/comment")
public class CommentCommandController {
    @Autowired
    private CommentService commentService;
    @PostMapping("")
    public ResponseEntity<String> createComment(@RequestBody CommentEvent commentEvent){
        Comment newComment = commentService.createComment(commentEvent);
        return  new ResponseEntity<String>("Comment Created"+ commentEvent.getComment().getCommentID(),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateComment(@PathVariable ObjectId commentID, @RequestBody CommentEvent updatedComment) {
        try {
            commentService.updateComment(commentID,updatedComment);
            return new ResponseEntity<String>("Comment was updated",HttpStatus.OK);
        } catch (CommentNotFoundException e) {

            return new ResponseEntity<String>("Comment not found",HttpStatus.NOT_FOUND);

        }
    }
    @PostMapping("/{id}/replies")
    public ResponseEntity<String> createReply(@RequestBody CommentEvent createReply){
        Comment newReply = commentService.createComment(createReply);
        return new ResponseEntity<String>("Reply created",HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAllComments(){

        return ResponseEntity.ok(commentService.getAllComments());
    }

    @GetMapping("userid/test")
    public String id(@AuthenticationPrincipal DefaultOAuth2User user){
        return user.getName();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable ObjectId commentID){
        try{
            commentService.deleteComment(commentID);
            return new ResponseEntity<String>("Comment was deleted",HttpStatus.OK);
        }catch(CommentNotFoundException e)
        {
            return new ResponseEntity<String>("Comment not found",HttpStatus.NOT_FOUND);
        }
    }
}
