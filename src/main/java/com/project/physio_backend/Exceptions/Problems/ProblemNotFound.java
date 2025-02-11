package com.project.physio_backend.Exceptions.Problems;

public class ProblemNotFound extends RuntimeException {
  public ProblemNotFound(String message) {
    super(message);
  }
}
