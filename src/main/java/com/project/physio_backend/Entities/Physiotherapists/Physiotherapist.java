package com.project.physio_backend.Entities.Physiotherapists;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private String physiotherapitsImage;

    @JsonIgnore
    @OneToMany(mappedBy = "physiotherapist", cascade = CascadeType.ALL)
    private List<WorkingHours> workingHours;

    public void addWorkDay(WorkingHours workingDays) {
        if (workingHours == null) {
            workingHours = new ArrayList<>();
        }
        this.workingHours.add(workingDays);
    }

    public Physiotherapist(String clinicName, long phonenumber, double price, String address, String addressLink,
            Location location, String physiotherapitsImage) {
        this.clinicName = clinicName;
        this.phonenumber = phonenumber;
        this.price = price;
        this.address = address;
        this.addressLink = addressLink;
        this.location = location;
        this.physiotherapitsImage = physiotherapitsImage;
        workingHours = new ArrayList<>();
    }

    

}
