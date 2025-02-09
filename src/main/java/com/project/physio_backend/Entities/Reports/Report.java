package com.project.physio_backend.Entities.Reports;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.project.physio_backend.Entities.Problems.Problem;

@Data
@Entity
@Table(name = "report")
public class Report {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "report_id", nullable = false)
  private Long reportID;

  private LocalDateTime timestamp;

  @Column(name = "user_problem_image")
  private String userProblemImage;

  @ManyToMany
  @JoinTable(name = "report_problems", joinColumns = @JoinColumn(name = "report_id"), inverseJoinColumns = @JoinColumn(name = "problem_id"))
  private List<Problem> problems = new ArrayList<>();

  public Report() {
  }

  public Report(List<Problem> problems, String userProblemImage) {
    this.problems = problems;
    this.userProblemImage = userProblemImage;
    this.timestamp = LocalDateTime.now();
  }

  public void addProblem(Problem problem) {
    problems.add(problem);
  }

  @PrePersist
  protected void onCreate() {
    this.timestamp = LocalDateTime.now();
  }
}
