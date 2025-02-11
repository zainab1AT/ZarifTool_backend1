package com.project.physio_backend.Exceptions.Physiotherapists;

public class PhysiotherapistNotFoundException extends RuntimeException {
    public PhysiotherapistNotFoundException(Long id) {
        super("Could not find Physiotherapist " + id);
    }
}
