package com.project.physio_backend.Exceptions.Progress;

public class ProgressNotFound extends RuntimeException {

  public ProgressNotFound(String message) {
    super(message);
  }

  public ProgressNotFound(String message, Throwable cause) {
    super(message, cause);
  }
}
