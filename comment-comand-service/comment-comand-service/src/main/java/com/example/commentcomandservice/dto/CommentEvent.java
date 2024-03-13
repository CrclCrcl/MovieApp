package com.example.commentcomandservice.dto;

import com.example.commentcomandservice.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentEvent {
    private String eventType;
    private Comment comment;
    private String reply;

    public CommentEvent(String eventType,Comment comment){
        this.eventType = eventType;
        this.comment = comment;
    }

}


