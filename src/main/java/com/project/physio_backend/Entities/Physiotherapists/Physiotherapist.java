package com.project.physio_backend.Entities.Physiotherapists;

import java.util.*;

import com.project.physio_backend.Entities.Users.Location;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "physiotherapist")
@Data
@NoArgsConstructor
public class Physiotherapist {

    @Column(name = "physiotherapist_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long physiotherapistID;

    private String clinicName;

    private long phonenumber;

    private double price;

    private String address;

    private String addressLink;

    @Enumerated(EnumType.STRING)
    private Location location;

    @OneToMany(mappedBy = "physiotherapist", cascade = CascadeType.ALL)
    private List<WorkingHours> workingHours;

    public void addWorkDay (WorkingHours workingDays) {
        if (workingHours == null) {
            workingHours = new ArrayList<>();
        }
        this.workingHours.add(workingDays);
    }

    
}
