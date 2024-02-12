package com.example.moviequeryservice.Controller;

import com.example.moviequeryservice.Entity.Movie;
import com.example.moviequeryservice.Exceptions.MovieNotFoundException;
import com.example.moviequeryservice.Service.MovieQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/movies")
public class MovieQueryController {
    @Autowired
    private MovieQueryService queryService;

    @GetMapping
    public ResponseEntity fetchAllMovies(){
        var list = queryService.getMovies();
        if(list.size()<1) return new ResponseEntity<String>("There is no movies", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<List<Movie>>(list,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getMovie(@PathVariable UUID id){
        try {
            var movie = queryService.getMovie(id);
            return new  ResponseEntity<Movie>(movie,HttpStatus.OK);
        }catch (MovieNotFoundException ex){
            return new ResponseEntity<String>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

}
