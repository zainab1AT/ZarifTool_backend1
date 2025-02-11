package com.project.physio_backend.Exceptions.Physiotherapists;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class PhysiotherapistNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(PhysiotherapistNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String physiotherapistFoundHandler(PhysiotherapistNotFoundException ex) {
        return ex.getMessage();
    }

}
