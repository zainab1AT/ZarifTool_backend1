package com.project.physio_backend.Entities.Problems;

import java.util.ArrayList;

import com.project.physio_backend.Entities.Excercises.Exercise;
import com.project.physio_backend.Entities.Reports.Report;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "problem")
public class Problem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Problem_id", nullable = false)
  private Long problemID;

  @Column(name = "descriptive_image")
  private String descriptiveImage;

  @Column(columnDefinition = "TEXT")
  private String description;

  @ManyToMany
  @JoinTable(name = "problem_exercise", joinColumns = @JoinColumn(name = "problem_id"), inverseJoinColumns = @JoinColumn(name = "exercise_id"))
  private List<Exercise> exercises = new ArrayList<>();

  @ManyToMany(mappedBy = "problems")
  private List<Report> reports = new ArrayList<>();

  public Problem() {
  }

  public Problem(String descriptiveImage, String description, List<Exercise> exercises) {
    this.descriptiveImage = descriptiveImage;
    this.description = description;
    this.exercises = exercises;
  }

  public Problem(String descriptiveImage, String description) {
    this.descriptiveImage = descriptiveImage;
    this.description = description;
  }

}