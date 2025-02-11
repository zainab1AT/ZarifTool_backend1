package com.project.physio_backend.Controllers;

import com.project.physio_backend.Entities.Problems.Problem;
import com.project.physio_backend.Entities.Progress.Progress;
import com.project.physio_backend.Entities.Reports.Report;
import com.project.physio_backend.Entities.Users.User;
import com.project.physio_backend.Services.ProblemService.ProblemService;
import com.project.physio_backend.Entities.Excercises.Exercise;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/problems")
@CrossOrigin(origins = "*")
public class ProblemController {

  private final ProblemService problemService;

  public ProblemController(ProblemService problemService) {
    this.problemService = problemService;
  }

  @GetMapping
  public List<Problem> getAllProblems() {
    return problemService.getAllProblems();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Problem> getProblemById(@PathVariable Long id) {
    return ResponseEntity.ok(problemService.getProblemById(id));
  }

  @GetMapping("/{id}/reports")
  public ResponseEntity<List<Report>> getProblemReports(@PathVariable Long id) {
    return ResponseEntity.ok(problemService.getProblemReports(id));
  }

  @GetMapping("/{id}/exercises")
  public ResponseEntity<List<Exercise>> getProblemExercises(@PathVariable Long id) {
    return ResponseEntity.ok(problemService.getProblemExercises(id));
  }

  @GetMapping("/{id}/progress")
  public ResponseEntity<List<Progress>> getProblemProgress(@PathVariable Long id) {
    return ResponseEntity.ok(problemService.getProblemProgress(id));
  }

  @PostMapping
  public ResponseEntity<Problem> createProblem(@RequestBody Problem problem) {
    return ResponseEntity.ok(
        problemService.createProblem(problem));
  }

  @PostMapping("/user/{userId}")
  public ResponseEntity<Problem> createProblemForUser(@PathVariable Long user_ID, @RequestBody Problem problem) {
    return ResponseEntity.ok(problemService.createProblemForUser(user_ID, problem));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Problem> updateProblem(@PathVariable Long id, @RequestBody Problem problem) {
    return ResponseEntity.ok(problemService.updateProblem(id, problem));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProblem(@PathVariable Long id) {
    problemService.deleteProblem(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{id}/add-exercise/{exerciseId}")
  public ResponseEntity<Problem> addExerciseToProblem(@PathVariable Long id, @PathVariable Long exerciseId) {
    return ResponseEntity.ok(problemService.addExerciseToProblem(id, exerciseId));
  }

  @PostMapping("/{id}/add-report/{reportId}")
  public ResponseEntity<Problem> addReportToProblem(@PathVariable Long id, @PathVariable Long reportId) {
    return ResponseEntity.ok(problemService.addReportToProblem(id, reportId));
  }

  @PostMapping("/{id}/add-progress/{progressId}")
  public ResponseEntity<Problem> addProgressToProblem(@PathVariable Long id, @PathVariable Long progressId) {
    return ResponseEntity.ok(problemService.addProgressToProblem(id, progressId));
  }

  @DeleteMapping("/{id}/remove-exercise/{exerciseId}")
  public ResponseEntity<Problem> removeExerciseFromProblem(@PathVariable Long id, @PathVariable Long exerciseId) {
    return ResponseEntity.ok(problemService.removeExerciseFromProblem(id, exerciseId));
  }

  @DeleteMapping("/{id}/remove-report/{reportId}")
  public ResponseEntity<Problem> removeReportFromProblem(@PathVariable Long id, @PathVariable Long reportId) {
    return ResponseEntity.ok(problemService.removeReportFromProblem(id, reportId));
  }

  @DeleteMapping("/{id}/remove-progress/{progressId}")
  public ResponseEntity<Problem> removeProgressFromProblem(@PathVariable Long id, @PathVariable Long progressId) {
    return ResponseEntity.ok(problemService.removeProgressFromProblem(id, progressId));
  }

  @PutMapping("/{id}/exercises")
  public ResponseEntity<Boolean> updateExercises(@PathVariable Long id, @RequestBody List<Exercise> newExercises) {
    boolean updated = problemService.updateExercises(id, newExercises);
    return new ResponseEntity<>(updated, HttpStatus.OK);
  }

  @PutMapping("/{id}/descriptiveImage")
  public ResponseEntity<Boolean> updateDescriptiveImage(@PathVariable Long id, @RequestParam String newImage) {
    boolean updated = problemService.updateDescriptiveImage(id, newImage);
    return new ResponseEntity<>(updated, HttpStatus.OK);
  }

  @PutMapping("/{id}/description")
  public ResponseEntity<Boolean> updateDescription(@PathVariable Long id, @RequestParam String newDescription) {
    boolean updated = problemService.updateDescription(id, newDescription);
    return new ResponseEntity<>(updated, HttpStatus.OK);
  }

  @PostMapping("/{id}/add-user/{userId}")
  public ResponseEntity<Problem> addUserToProblem(@PathVariable Long id, @PathVariable Long userId) {
    return ResponseEntity.ok(problemService.addUserToProblem(id, userId));
  }

  @DeleteMapping("/{id}/remove-user/{userId}")
  public ResponseEntity<Problem> removeUserFromProblem(@PathVariable Long id, @PathVariable Long userId) {
    return ResponseEntity.ok(problemService.removeUserFromProblem(id, userId));
  }

  @GetMapping("/{id}/users")
  public ResponseEntity<List<User>> getProblemUsers(@PathVariable Long id) {
    return ResponseEntity.ok(problemService.getProblemUsers(id));
  }

}