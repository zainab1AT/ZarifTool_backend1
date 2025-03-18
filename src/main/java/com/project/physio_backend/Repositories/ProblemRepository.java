package com.project.physio_backend.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.physio_backend.Entities.Problems.Problem;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
  Optional<Problem> findByName(String name);

}
