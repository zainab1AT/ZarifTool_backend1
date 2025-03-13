package com.project.physio_backend.Services.Exercise;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.physio_backend.Entities.Excercises.Exercise;
import com.project.physio_backend.Entities.Problems.Problem;
import com.project.physio_backend.Exceptions.Excercises.ExerciseNotFoundException;
import com.project.physio_backend.Repositories.*;
// import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import com.project.physio_backend.Services.ImageService.ImageService;

import io.jsonwebtoken.io.IOException;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private ImageService imageService;

    @Override
    public ResponseEntity<Exercise> addExercise(long problemID, String exerciseDescription,
            int exerciseDuration) {
        Problem problem = problemRepository.findById(problemID)
                .orElseThrow(() -> new RuntimeException("Problem not found"));

        Exercise exercise = new Exercise();
        exercise.setProblem(problem);
        exercise.setExerciseDescription(exerciseDescription);
        exercise.setExerciseDuration(exerciseDuration);
        problem.addExercise(exercise);

        return ResponseEntity.status(HttpStatus.CREATED).body(exerciseRepository.save(exercise));
    }

    public ResponseEntity<Exercise> addExerciseWithImage(long problemID,
            String exerciseDescription, int exerciseDuration, MultipartFile multipartFile) {
        Problem problem = problemRepository.findById(problemID)
                .orElseThrow(() -> new RuntimeException("Problem not found"));

        Exercise exercise = new Exercise();
        exercise.setProblem(problem);
        exercise.setExerciseDescription(exerciseDescription);
        exercise.setExerciseDuration(exerciseDuration);
        problem.addExercise(exercise);
        Exercise exercise1 = exerciseRepository.save(exercise);

        imageService.uploadImageForExercise(multipartFile, exercise1.getExerciseID());

        return ResponseEntity.status(HttpStatus.CREATED).body(exercise1);
    }

    @Override
    public ResponseEntity<?> deleteExercise(long exerciseID) {
        exerciseRepository.deleteById(exerciseID);
        return ResponseEntity.noContent().build();
    }

    // @Override
    // public EntityModel<Exercise> one(long exerciseID) {
    // Exercise exercise = exerciseRepository.findById(exerciseID)
    // .orElseThrow(() -> new ExerciseNotFoundException(exerciseID));
    // return EntityModel.of(exercise,
    // linkTo(methodOn(ExcerciseController.class).one(exerciseID)).withSelfRel(),
    // linkTo(methodOn(ExcerciseController.class).all()).withRel("Posts"));
    // }

    @Override
    public ResponseEntity<Exercise> updateExerciseWithImage(long exerciseID, String exerciseDescription,
            int exerciseDuration, MultipartFile multipartFile) throws IOException {
        Exercise exercise = exerciseRepository.findById(exerciseID)
                .orElseThrow(() -> new ExerciseNotFoundException(exerciseID));
        exercise.setExerciseDescription(exerciseDescription);
        exercise.setExerciseDuration(exerciseDuration);

        // Save the updated exercise
        Exercise updatedExercise = exerciseRepository.save(exercise);

        // Upload the new image
        imageService.uploadImageForExercise(multipartFile, updatedExercise.getExerciseID());

        return ResponseEntity.status(HttpStatus.OK).body(updatedExercise);
    }

    @Override
    public List<Exercise> getAllExercisesforProblem(long problemID) {
        Problem problem = problemRepository.findById(problemID)
                .orElseThrow(() -> new RuntimeException("Problem not found"));

        return problem.getExercises();
    }

    @Override
    public ResponseEntity<Exercise> getExercise(long exerciseID) {
        Exercise exercise = exerciseRepository.findById(exerciseID)
                .orElseThrow(() -> new ExerciseNotFoundException(exerciseID));
        return ResponseEntity.status(HttpStatus.OK).body(exercise);
    }

}
