package com.moviecommandservice.service;

import com.moviecommandservice.dto.MovieEvent;
import com.moviecommandservice.entity.Movie;
import com.moviecommandservice.exceptions.MovieNotFoundException;
import com.moviecommandservice.repository.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import lombok.Data;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovieCommandService {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;
    public Movie createMovie(MovieEvent MovieEvent)
    {
        Movie newMovieDO = movieRepository.save(MovieEvent.getMovie());
        MovieEvent event = new MovieEvent("CreateMovie", newMovieDO);
       // kafkaTemplate.send("movie-event-topic",event);
        return newMovieDO;
    }

    @Transactional
    public Movie updateMovie(UUID id, MovieEvent movieDto) throws MovieNotFoundException {
        Optional<Movie> optionalCurrentMovie = movieRepository.findById(id);

        if (optionalCurrentMovie.isPresent()) {
            Movie currentMovie = optionalCurrentMovie.get();
            Movie updatedMovie = movieDto.getMovie();

            currentMovie.setImageSource(updatedMovie.getImageSource() != null ?
                    updatedMovie.getImageSource() : currentMovie.getImageSource());
            currentMovie.setDirector(updatedMovie.getDirector() != null ? updatedMovie.getDirector() : currentMovie.getDirector());
            currentMovie.setTitle(updatedMovie.getTitle() != null ? updatedMovie.getTitle() : currentMovie.getTitle());
            currentMovie.setDescription(updatedMovie.getDescription() != null ? updatedMovie.getDescription() : currentMovie.getDescription());
            currentMovie.setProductionYear(updatedMovie.getProductionYear());

            movieRepository.save(currentMovie);
            MovieEvent event = new MovieEvent("UpdateMovie", currentMovie);
            // kafkaTemplate.send("movie-event-topic",event);
            return currentMovie;
        } else {
            throw new MovieNotFoundException(id);
        }
    }
    @Transactional
    public void deleteMovie(UUID id) throws MovieNotFoundException {
        Optional<Movie> optionalMovie = movieRepository.findById(id);

        if (optionalMovie.isPresent()) {
            movieRepository.deleteById(id);
            MovieEvent event = new MovieEvent("DeleteMovie", optionalMovie.get());
            // kafkaTemplate.send("movie-event-topic",event);
        } else {
            throw new MovieNotFoundException(id);
        }
    }

}