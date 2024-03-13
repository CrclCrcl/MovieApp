package com.example.commentcomandservice.service;

import com.example.commentcomandservice.dto.CommentEvent;
import com.example.commentcomandservice.dto.MovieEvent;
import com.example.commentcomandservice.entity.Comment;
import com.example.commentcomandservice.entity.Movie;
import com.example.commentcomandservice.exceptions.CommentNotFoundException;
import com.example.commentcomandservice.repository.CommentRepository;
import com.example.commentcomandservice.repository.MovieIdRepository;
import jakarta.transaction.Transactional;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentService {

    private CommentRepository commentRepository;

    private MovieIdRepository movieIdRepository;
    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;
   @Autowired
    private ReactiveMongoTemplate mongoTemplate;
    public Comment createComment(CommentEvent CommentEvent) {
        Comment comment = commentRepository.save(CommentEvent.getComment());
        CommentEvent event = new CommentEvent("CreateComment", comment);
        kafkaTemplate.send("comment-event-topic",event);
        return comment;

    }

    @Transactional
    public void deleteComment(ObjectId commentID) throws CommentNotFoundException {
        Optional<Comment> optionalComment = commentRepository.findById(commentID);

        if (optionalComment.isPresent()) {
            commentRepository.deleteById(commentID);
            CommentEvent event = new CommentEvent("DeleteComment", optionalComment.get());
            kafkaTemplate.send("comment-event-topic",event);
        } else {
            throw new CommentNotFoundException(commentID);
        }
    }

    @Transactional
    public Comment updateComment(ObjectId commentID, CommentEvent commentDto) throws CommentNotFoundException {
        Optional<Comment> optionalComment = commentRepository.findById(commentID);

        if (optionalComment.isPresent()){
            Comment currentComment = optionalComment.get();
            Comment updatedComment = commentDto.getComment();

            currentComment.setContent(updatedComment.getContent() != null ? updatedComment.getContent() : currentComment.getContent());


            commentRepository.save(currentComment);
            CommentEvent event = new CommentEvent("Updated Comment",currentComment);
            kafkaTemplate.send("comment-event-topic",event);
            return currentComment;
        }else {
            throw new CommentNotFoundException(commentID);
        }
    }

    public Object getAllComments() {
        return null;
    }

    @KafkaListener(topics = "movie-event-topic", groupId = "movie-event-group")
    public void processMovieEvents(ConsumerRecord<String, MovieEvent> record) {
        try {
            MovieEvent movieEvent = record.value();
            Movie movie = movieEvent.getMovie();
            if (movieEvent.getEventType().equals("CreateMovie")) {
                String newMovieId = movie.getId().toString();
            }
        } catch (Exception e) {
        }
    }
}



