package com.project.physio_backend.Services.Exercise;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import com.project.physio_backend.Entities.Excercises.Exercise;
import com.project.physio_backend.Entities.Problems.Problem;
import com.project.physio_backend.Exceptions.Excercises.ExerciseNotFoundException;
import com.project.physio_backend.Repositries.*;
// import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    @Autowired
    private ExerciseRepositry exerciseRepository;

    @Autowired
    private ProblemRepositry problemRepository;


    @Override
    public ResponseEntity<Exercise> addExercise(long problemID, String exerciseImageURI, String exerciseDescription, int exerciseDuration) {
        Problem problem = problemRepository.findById(problemID)
                .orElseThrow(() -> new RuntimeException("Problem not found"));

        Exercise exercise = new Exercise();
        exercise.setProblem(problem);
        exercise.setExerciseImageURI(exerciseImageURI);
        exercise.setExerciseDescription(exerciseDescription);
        exercise.setExerciseDuration(exerciseDuration);
        problem.addExercise(exercise);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(exerciseRepository.save(exercise));
    }

    @Override
    public ResponseEntity<?> deleteExercise(long exerciseID) {
        exerciseRepository.deleteById(exerciseID);
        return ResponseEntity.noContent().build();
    }

    // @Override
    // public EntityModel<Exercise> one(long exerciseID) {
    //     Exercise exercise = exerciseRepository.findById(exerciseID)
    //             .orElseThrow(() -> new ExerciseNotFoundException(exerciseID));
    //     return EntityModel.of(exercise,
    //             linkTo(methodOn(ExcerciseController.class).one(exerciseID)).withSelfRel(),
    //             linkTo(methodOn(ExcerciseController.class).all()).withRel("Posts"));
    // }


    @Override
    public ResponseEntity<Exercise> updateExercise(long exerciseID, String exerciseImageURI, String exerciseDescription, int exerciseDuration) {
        Exercise exercise = exerciseRepository.findById(exerciseID)
                .orElseThrow(() -> new ExerciseNotFoundException(exerciseID));
        exercise.setExerciseDescription(exerciseDescription);
        exercise.setExerciseDuration(exerciseDuration);
        exercise.setExerciseImageURI(exerciseImageURI);

        return ResponseEntity.status(HttpStatus.OK).body(exerciseRepository.save(exercise));
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
