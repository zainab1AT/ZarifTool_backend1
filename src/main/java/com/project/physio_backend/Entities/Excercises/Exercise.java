package com.project.physio_backend.Entities.Excercises;

import com.project.physio_backend.Entities.Problems.Problem;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "exercise")
@Data
public class Exercise {

    @Column(name = "exercise_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long exerciseID;

    private String exerciseImageURI;

    private String exerciseDescription;

    private int exerciseDuration;

    @ManyToOne
    @JoinColumn(name = "problem_ID", nullable = false)
    private Problem problem;

}
