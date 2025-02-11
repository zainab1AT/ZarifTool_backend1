package com.project.physio_backend.Entities.Progress;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import com.project.physio_backend.Entities.Problems.Problem;

@Data
@Entity
@Table(name = "progress")
public class Progress {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "progress_ID", nullable = false)
  private Long progressID;

  private LocalDateTime timestamp;

  private Double percentag;

  @ManyToOne
  @JoinColumn(name = "problem_ID", nullable = false)
  private Problem problem;

  public Progress() {
  }

  public Progress(LocalDateTime timestamp) {
    this.timestamp = timestamp;
    // this.exercises = exercises;
  }

  public Progress(LocalDateTime timestamp, Double percentage) {
    this.timestamp = timestamp;
    this.percentag = percentage;
  }

}