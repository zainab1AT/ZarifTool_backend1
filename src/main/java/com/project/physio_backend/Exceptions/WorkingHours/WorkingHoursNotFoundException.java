package com.project.physio_backend.Exceptions.WorkingHours;

public class WorkingHoursNotFoundException extends RuntimeException {
    public WorkingHoursNotFoundException(Long id) {
        super("Could not find Working Hours with id " + id);
    }

}
