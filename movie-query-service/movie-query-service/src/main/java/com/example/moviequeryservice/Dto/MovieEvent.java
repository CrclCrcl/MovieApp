package com.example.moviequeryservice.Dto;

import com.example.moviequeryservice.Entity.Movie;
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

