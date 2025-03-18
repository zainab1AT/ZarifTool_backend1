package com.project.physio_backend.Entities.Excercises;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.physio_backend.Entities.Image.Image;
import com.project.physio_backend.Entities.Problems.Problem;
import com.project.physio_backend.Entities.Progress.Progress;

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

    // @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "problem_ID", nullable = false)
    private Problem problem;

    @JsonIgnore
    @ManyToMany(mappedBy = "exercises") // Link to Progress
    private List<Progress> progress;

    public List<Progress> getProgresses() {
        return progress;
    }

}
