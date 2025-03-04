package com.project.physio_backend.Exceptions.Prize;

public class PrizeNotFoundException extends RuntimeException {
    public PrizeNotFoundException(Long id) {
        super("Could not find Prize " + id);
    }
    
}
