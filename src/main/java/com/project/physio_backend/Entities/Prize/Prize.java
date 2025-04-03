package com.project.physio_backend.Entities.Prize;

import com.project.physio_backend.Entities.Users.User;

import java.time.LocalDate;

import com.project.physio_backend.Entities.Problems.Problem;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Prize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Prize_ID", nullable = false)
    private Long prizeID;

    private String prizeDescriprion;

    private LocalDate day;
    private LocalDate month;

    @Enumerated(EnumType.STRING)
    private PrizeType prizeType;

    @ManyToOne
    @JoinColumn(name = "user_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "problem_ID", nullable = false)
    private Problem problem;
}
