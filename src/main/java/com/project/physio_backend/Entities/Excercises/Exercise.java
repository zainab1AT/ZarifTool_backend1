package com.project.physio_backend.Entities.Excercises;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.physio_backend.Entities.Image.Image;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

    private String exerciseDescription;

    private int exerciseDuration;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "problem_ID", nullable = false)
    private Problem problem;

}
