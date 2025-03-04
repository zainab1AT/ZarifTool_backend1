package com.project.physio_backend.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.project.physio_backend.Entities.Excercises.Exercise;
import com.project.physio_backend.Services.Exercise.ExerciseService;

@RestController
@RequestMapping("/api/exercises")
@CrossOrigin
public class ExcerciseController {

    @Autowired
    private ExerciseService exerciseService;

    @PostMapping("/add/{problemID}")
    public ResponseEntity<Exercise> addExercise(@PathVariable long problemID, @RequestBody Exercise exercise) {
        return exerciseService.addExercise(problemID, exercise.getExerciseDescription(),
                exercise.getExerciseDuration());
    }

    @PostMapping(value = "/add/{problemID}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Exercise> addExerciseWithImage(
            @PathVariable long problemID,
            @RequestPart("exercise") Exercise exercise,
            @RequestPart("image") MultipartFile file) {

        return exerciseService.addExerciseWithImage(problemID, exercise.getExerciseDescription(),
                exercise.getExerciseDuration(), file);
    }

    @DeleteMapping("/delete/{exerciseID}")
    public ResponseEntity<?> deleteExercise(@PathVariable long exerciseID) {
        return exerciseService.deleteExercise(exerciseID);
    }

    @PutMapping("/update/{exerciseID}")
    public ResponseEntity<Exercise> updateExercise(@PathVariable long exerciseID, @RequestBody Exercise exercise) {
        return exerciseService.updateExercise(exerciseID,
                exercise.getExerciseDescription(), exercise.getExerciseDuration());
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
