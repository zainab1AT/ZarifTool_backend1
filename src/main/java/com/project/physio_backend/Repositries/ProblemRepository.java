package com.project.physio_backend.Repositries;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.physio_backend.Entities.Problems.Problem;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
}