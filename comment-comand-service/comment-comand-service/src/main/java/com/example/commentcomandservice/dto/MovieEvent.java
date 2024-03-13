package com.example.commentcomandservice.dto;

import com.example.commentcomandservice.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieEvent {
    private String eventType;
    private Movie movie;
}

