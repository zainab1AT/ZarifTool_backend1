package com.project.physio_backend.Repositories;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.physio_backend.Entities.Problems.Problem;
import com.project.physio_backend.Entities.Progress.Progress;
import com.project.physio_backend.Entities.Users.User;

public interface ProgressRepository extends JpaRepository<Progress, Long> {
    boolean existsByUserAndProblemAndTimestampBetween(User user, Problem problem, LocalDateTime start,
            LocalDateTime end);

    Optional<Progress> findByUserAndProblemAndTimestampBetween(User user, Problem problem, LocalDateTime start,
            LocalDateTime end);

    List<Progress> findByUserAndProblem(User user, Problem problem);
}
