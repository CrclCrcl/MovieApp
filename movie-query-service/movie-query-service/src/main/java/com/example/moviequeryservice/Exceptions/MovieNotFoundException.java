package com.example.moviequeryservice.Exceptions;

import java.util.UUID;

public class MovieNotFoundException extends Throwable {
    public MovieNotFoundException(final UUID id) {
        super("Movie with id" + id + " not found");
    }
}
