package com.project.physio_backend.Entities.Reports;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

import com.project.physio_backend.Entities.Users.User;

@Data
@Entity
@Table(name = "report")
public class Report {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "report_ID", nullable = false)
  private Long reportID;

  private LocalDateTime timestamp;

  @Column(name = "user_problem_image")
  private String userProblemImage;

  @ManyToOne
  @JoinColumn(name = "user_ID", nullable = false)
  private User user;

  public Report() {
  }

  public Report(String userProblemImage) {
    this.userProblemImage = userProblemImage;
    this.timestamp = LocalDateTime.now();
  }

  @PrePersist
  protected void onCreate() {
    this.timestamp = LocalDateTime.now();
  }
}
