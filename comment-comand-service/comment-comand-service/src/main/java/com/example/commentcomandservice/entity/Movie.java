package com.example.commentcomandservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    private UUID id;
    private String imageSource;
    private String title;
    private String description;
    private String director;
    private int productionYear;
}