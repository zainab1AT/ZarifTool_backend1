package com.project.physio_backend.Entities.Image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.physio_backend.Entities.Excercises.Exercise;
import com.project.physio_backend.Entities.Physiotherapists.Physiotherapist;
import com.project.physio_backend.Entities.Problems.Problem;
import com.project.physio_backend.Entities.Reports.Report;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "images")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ImageType type;
    private String name;
    private String url;

    @OneToOne(mappedBy = "image")
    @JsonIgnore
    private Problem problem;

    @OneToOne(mappedBy = "image")
    @JsonIgnore
    private Exercise exercise;

    @OneToOne(mappedBy = "image")
    @JsonIgnore
    private Report report;

    @OneToOne(mappedBy = "image")
    @JsonIgnore
    private Physiotherapist physiotherapist;

    public Image(ImageType type, String name) {
        this.type = type;
        this.name = name;
    }

}
