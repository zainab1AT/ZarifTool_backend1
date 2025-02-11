package com.project.physio_backend.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.physio_backend.Entities.Excercises.Exercise;
import com.project.physio_backend.Services.Exercise.ExerciseService;

@RestController
@RequestMapping("/api/exercises")
public class ExcerciseController {
    
    @Autowired
    private ExerciseService exerciseService;

    @PostMapping("/add/{problemID}")
    public ResponseEntity<Exercise> addExercise(@PathVariable long problemID, @RequestBody Exercise exercise) {
        return exerciseService.addExercise(problemID, exercise.getExerciseImageURI(), exercise.getExerciseDescription(), exercise.getExerciseDuration());
    }

    @DeleteMapping("/delete/{exerciseID}")
    public ResponseEntity<?> deleteExercise(@PathVariable long exerciseID) {
        return exerciseService.deleteExercise(exerciseID);
    }

    @PutMapping("/update/{exerciseID}")
    public ResponseEntity<Exercise> updateExercise(@PathVariable long exerciseID, @RequestBody Exercise exercise) {
        return exerciseService.updateExercise(exerciseID, exercise.getExerciseImageURI(), exercise.getExerciseDescription(), exercise.getExerciseDuration());
    }

    @GetMapping("/{exerciseID}")
    public ResponseEntity<Exercise> getExercise(@PathVariable long exerciseID) {
        return exerciseService.getExercise(exerciseID);
    }

    @GetMapping("/problem/{problemID}")
    public List<Exercise> getAllExercisesForProblem(@PathVariable long problemID) {
        return exerciseService.getAllExercisesforProblem(problemID);
    }


}
