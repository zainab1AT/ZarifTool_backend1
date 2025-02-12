package com.project.physio_backend.Entities.Users;

import com.project.physio_backend.Entities.Problems.Problem;
import com.project.physio_backend.Entities.Reports.Report;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_ID", nullable = false)
    private Long userID;

    @NotBlank
    @Size(min = 5, max = 20, message = "The size of username must be between 5 and 20")
    private String username;

    @NotBlank
    @Size(min = 6, max = 40, message = "Password size must be between 6 and 40 characters")
    private String password;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private Profile profile;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Report> reports;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user-problem", joinColumns = @JoinColumn(name = "user_ID"), inverseJoinColumns = @JoinColumn(name = "problem_ID"))
    private List<Problem> problems;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }
}