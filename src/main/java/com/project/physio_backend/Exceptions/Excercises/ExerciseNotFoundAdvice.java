package com.project.physio_backend.Exceptions.Excercises;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class ExerciseNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ExerciseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String exerciseNotFoundHandler(ExerciseNotFoundException ex) {
        return ex.getMessage();
    }

}
