package com.project.physio_backend.Entities.Problems;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.physio_backend.Entities.Excercises.Exercise;
import com.project.physio_backend.Entities.Image.Image;
import com.project.physio_backend.Entities.Progress.Progress;
import com.project.physio_backend.Entities.Reports.Report;
import com.project.physio_backend.Entities.Users.User;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "problem")
public class Problem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Problem_ID", nullable = false)
  private Long problemID;

  @Column(name = "descriptive_image")
  private String descriptiveImage;

  @Column(columnDefinition = "TEXT")
  private String description;

  @JsonIgnore
  @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
  private List<Exercise> exercises = new ArrayList<>();

  @JsonIgnore
  @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
  private List<Progress> progresses = new ArrayList<>();

  @JsonIgnore
  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(name = "report-problem", joinColumns = @JoinColumn(name = "problem_ID"), inverseJoinColumns = @JoinColumn(name = "report_ID"))
  private List<Report> reports = new ArrayList<>();

  @JsonIgnore
  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(name = "user-problem", joinColumns = @JoinColumn(name = "problem_ID"), inverseJoinColumns = @JoinColumn(name = "user_ID"))
  private List<User> users = new ArrayList<>();

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "image_id", referencedColumnName = "id")
  private Image image;

  public Problem() {
  }

  public Problem(String descriptiveImage, String description) {
    this.descriptiveImage = descriptiveImage;
    this.description = description;
  }

  public void addExercise(Exercise exercise) {
    if (exercises == null) {
      exercises = new ArrayList<>();
    }
    this.exercises.add(exercise);
  }

}