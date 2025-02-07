package com.project.physio_backend.Entities.Physiotherapists;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "physiotherapist")
@Data
public class Physiotherapist {

    @Column(name = "physiotherapist_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long physiotherapistID;

    private String clicncName;

    private long phonenumber;

    private double price;

    private String address;

    private List<String> workingDays;

    @OneToMany(mappedBy = "physiotherapist", cascade = CascadeType.ALL)
    private List<WorkingHours> workingHours;
}
