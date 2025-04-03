package com.project.physio_backend.Repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.physio_backend.Entities.Problems.Problem;
import com.project.physio_backend.Entities.Progress.Progress;
import com.project.physio_backend.Entities.Users.User;

public interface ProgressRepository extends JpaRepository<Progress, Long> {
        boolean existsByUserAndProblemAndTimestampBetween(User user, Problem problem, LocalDateTime start,
                        LocalDateTime end);

        boolean existsByUserAndTimestampBetween(User user, LocalDateTime start,
                        LocalDateTime end);

        Optional<Progress> findByUserAndProblemAndTimestampBetween(User user, Problem problem, LocalDateTime start,
                        LocalDateTime end);

        List<Progress> findByUserAndProblem(User user, Problem problem);

        long countByUserAndProblem(User user, Problem problem);

        List<Progress> findByUserAndTimestampBetween(User user, LocalDateTime start, LocalDateTime end);

        @Query("SELECT DISTINCT DATE(p.timestamp) FROM Progress p WHERE p.user = :user AND p.timestamp BETWEEN :start AND :end")
        List<java.sql.Date> findDistinctDatesByUserAndTimestampBetween(@Param("user") User user,
                        @Param("start") LocalDateTime start,
                        @Param("end") LocalDateTime end);

}
