package com.example.moviequeryservice.Repository;

import com.example.moviequeryservice.Entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MovieRepository extends JpaRepository<Movie, UUID> {
}
