package com.project.physio_backend.Repositories;

import org.springframework.stereotype.Repository;
import com.project.physio_backend.Entities.Excercises.Exercise;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

}
