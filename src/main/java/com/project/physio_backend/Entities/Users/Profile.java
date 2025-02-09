package com.project.physio_backend.Entities.Users;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name = "profile")
@Data
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_ID", nullable = false)
    private Long profileId;

    private String bio;

    private String profilePictureUri;

    private double height;

    private double weight;

    @NotNull(message = "Date of birth must not be null")
    @Past(message = "Date of birth must be in the past")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Location location;

    @JsonIgnore
    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL)
    @JoinColumn(name = "user_ID")
    private User user;

    public Profile() {}

    public Profile(String bio, String profilePictureUri, double height, double weight, Date dateOfBirth, Gender gender, Location location) {
        this.bio = bio;
        this.profilePictureUri = profilePictureUri;
        this.height = height;
        this.weight = weight;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.location = location;
        // this.user = user;
    }

}
