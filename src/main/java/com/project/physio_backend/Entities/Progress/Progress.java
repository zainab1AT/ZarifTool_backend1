package com.project.physio_backend.Entities.Progress;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.physio_backend.Entities.Excercises.Exercise;
import com.project.physio_backend.Entities.Problems.Problem;
import com.project.physio_backend.Entities.Users.User;

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

  // @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "problem_ID", nullable = false)
  private Problem problem;

  @ManyToOne
  @JoinColumn(name = "user_ID", nullable = false)
  private User user;

  @JsonIgnore
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "progress-exercises", joinColumns = @JoinColumn(name = "progress_ID"), inverseJoinColumns = @JoinColumn(name = "exercise_ID"))
  private List<Exercise> exercises;

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