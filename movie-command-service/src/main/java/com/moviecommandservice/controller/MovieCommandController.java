package com.moviecommandservice.controller;

import com.moviecommandservice.dto.MovieEvent;
import com.moviecommandservice.entity.Movie;
import com.moviecommandservice.exceptions.MovieNotFoundException;
import com.moviecommandservice.service.MovieCommandService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RestController
@Controller
@RequestMapping("/movies")
public class MovieCommandController {
    @Autowired
    private MovieCommandService commandService;
    @PostMapping
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<String> createMovie(@RequestBody MovieEvent movieEvent)
    {
        Movie newMovie = commandService.createMovie(movieEvent);
        return new ResponseEntity<String>("Movie was created "+movieEvent.getMovie().getId(), HttpStatus.OK);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<String> updateMovie(@PathVariable UUID id,@RequestBody MovieEvent updatedMovie){
        try{
            commandService.updateMovie(id,updatedMovie);
            return new ResponseEntity<String>("Movie was updated",HttpStatus.OK);
        }catch(MovieNotFoundException ex)
        {
            return new ResponseEntity<String>("Movie was not found",HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<String> deleteMovie(@PathVariable UUID id){
        try{
            commandService.deleteMovie(id);
            return new ResponseEntity<String>("Movie was deleted",HttpStatus.OK);
        }catch(MovieNotFoundException ex)
        {
            return new ResponseEntity<String>("Movie not found",HttpStatus.NOT_FOUND);
        }
    }

}
