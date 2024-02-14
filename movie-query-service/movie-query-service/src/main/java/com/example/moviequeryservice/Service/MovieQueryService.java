package com.example.moviequeryservice.Service;

import com.example.moviequeryservice.Dto.MovieEvent;
import com.example.moviequeryservice.Entity.Movie;
import com.example.moviequeryservice.Exceptions.MovieNotFoundException;
import com.example.moviequeryservice.Repository.MovieRepository;
import jakarta.transaction.Transactional;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.hibernate.sql.ast.SqlTreeCreationLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hibernate.sql.ast.SqlTreeCreationLogger.*;

@Service
public class MovieQueryService {

    private static final Logger logger = LoggerFactory.getLogger(MovieQueryService.class);
    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovie(UUID id) throws MovieNotFoundException{
        Optional<Movie> optionalMovie = movieRepository.findById(id);

        if (!optionalMovie.isPresent())
            throw new MovieNotFoundException(id);

        return optionalMovie.get();
    }


    @KafkaListener(topics = "movie-event-topic", groupId = "movie-event-group")
    public void processMovieEvents(ConsumerRecord<String, MovieEvent> record) {
        try {
            MovieEvent movieEvent = record.value();
            Movie movie = movieEvent.getMovie();
            if (movieEvent.getEventType().equals("CreateMovie")) {
                movieRepository.save(movie);
            }
            if (movieEvent.getEventType().equals("UpdateMovie")) {
                Movie existingMovie = movieRepository.findById(movie.getId()).get();
                existingMovie.setTitle(movie.getTitle());
                existingMovie.setProductionYear(movie.getProductionYear());
                existingMovie.setDirector(movie.getDirector());
                existingMovie.setImageSource(movie.getImageSource());
                existingMovie.setDescription(movie.getDescription());
                movieRepository.save(existingMovie);
            }
        } catch (Exception e) {

            logger.error("Floppa Boss",e);

        }
    }


}
