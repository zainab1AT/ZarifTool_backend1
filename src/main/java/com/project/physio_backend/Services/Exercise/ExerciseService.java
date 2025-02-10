package com.project.physio_backend.Services.Exercise;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.project.physio_backend.Entities.Excercises.Exercise;

public interface ExerciseService {
    
    public ResponseEntity<Exercise> addExercise (long problemID, String exerciseImageURI, String exerciseDescription, int exerciseDuration);

    public ResponseEntity<?> deleteExercise (long exerciseID);

    public ResponseEntity<Exercise> getExercise (long exerciseID);

    public ResponseEntity<Exercise> updateExercise (long exerciseID, String exerciseImageURI, String exerciseDescription, int exerciseDuration);

    public List<Exercise> getAllExercisesforProblem (long problemID);
}
