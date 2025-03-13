package com.project.physio_backend.Services.Exercise;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.project.physio_backend.Entities.Excercises.Exercise;

import io.jsonwebtoken.io.IOException;

public interface ExerciseService {

    public ResponseEntity<Exercise> addExercise(long problemID, String exerciseDescription, int exerciseDuration);

    public ResponseEntity<Exercise> addExerciseWithImage(long problemID, String exerciseDescription,
            int exerciseDuration, MultipartFile multipartFile) throws IOException;

    public ResponseEntity<?> deleteExercise(long exerciseID);

    public ResponseEntity<Exercise> getExercise(long exerciseID);

    public ResponseEntity<Exercise> updateExerciseWithImage(long exerciseID, String exerciseDescription,
            int exerciseDuration, MultipartFile multipartFile) throws IOException;

    public List<Exercise> getAllExercisesforProblem(long problemID);
}
