package com.project.physio_backend.Exceptions.Reports;

public class ReportNotFound extends RuntimeException {
  public ReportNotFound(String message) {
    super(message);
  }

  public ReportNotFound(String message, Throwable cause) {
    super(message, cause);
  }
}
