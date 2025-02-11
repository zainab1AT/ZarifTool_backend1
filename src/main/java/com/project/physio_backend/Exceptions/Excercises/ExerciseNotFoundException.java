package com.project.physio_backend.Exceptions.Excercises;

public class ExerciseNotFoundException extends RuntimeException {
    public ExerciseNotFoundException(Long id) {
        super("Could not find Exercise " + id);
    }
}
