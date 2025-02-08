package com.project.physio_backend.Entities.Progress;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.project.physio_backend.Entities.Excercises.Exercise;

@Data
@Entity
@Table(name = "progress")
public class Progress {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "progress_id", nullable = false)
  private Long progressID;

  private LocalDateTime timestamp;

  @OneToMany(mappedBy = "progress", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Exercise> exercises = new ArrayList<>();

  public Progress() {
  }

  public Progress(LocalDateTime timestamp, List<Exercise> exercises) {
    this.timestamp = timestamp;
    this.exercises = exercises;
  }

}