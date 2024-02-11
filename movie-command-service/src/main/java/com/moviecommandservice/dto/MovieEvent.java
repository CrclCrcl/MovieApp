package com.moviecommandservice.dto;

import com.moviecommandservice.entity.Movie;
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
