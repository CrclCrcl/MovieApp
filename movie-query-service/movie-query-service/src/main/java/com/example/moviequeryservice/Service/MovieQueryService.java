package com.example.moviequeryservice.Service;

import com.example.moviequeryservice.Dto.MovieEvent;
import com.example.moviequeryservice.Entity.Movie;
import com.example.moviequeryservice.Exceptions.MovieNotFoundException;
import com.example.moviequeryservice.Repository.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovieQueryService {
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
    @KafkaListener(topics = "movie-event-topic",groupId = "movie-event-group")
    public void processMovieEvents(MovieEvent movieEvent) {
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
    }

}
