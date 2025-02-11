package com.project.physio_backend.Exceptions.WorkingHours;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class WorkingHoursNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(WorkingHoursNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String workingHourskNotFoundHandler(WorkingHoursNotFoundException ex) {
        return ex.getMessage();
    }
    
}
