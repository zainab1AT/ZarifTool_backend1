package com.project.physio_backend.Controllers;

import com.project.physio_backend.Entities.Problems.Problem;
import com.project.physio_backend.Entities.Users.User;
import com.project.physio_backend.Repositories.ExerciseRepository;
import com.project.physio_backend.Repositories.ProblemRepository;
import com.project.physio_backend.Repositories.UserRepository;
import com.project.physio_backend.Services.ProblemService.ProblemService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/problems")
@CrossOrigin(origins = "*")
public class ProblemController {

  private final ProblemService problemService;

  public ProblemController(ProblemService problemService, UserRepository userRepository,
      ExerciseRepository exerciseRepository,
      ProblemRepository problemRepository) {
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

  @PostMapping
  public ResponseEntity<Problem> createProblem(@RequestBody Problem problem) {
    return ResponseEntity.ok(
        problemService.createProblem(problem));
  }

  ///
  @PutMapping("/{id}")
  public ResponseEntity<Problem> updateProblem(@PathVariable Long id, @RequestBody Problem problem) {
    return ResponseEntity.ok(problemService.updateProblem(id, problem));
  }

  ////
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProblem(@PathVariable Long id) {
    problemService.deleteProblem(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{id}/add-report/{reportId}")
  public ResponseEntity<Problem> addReportToProblem(@PathVariable Long id, @PathVariable Long reportId) {
    return ResponseEntity.ok(problemService.addReportToProblem(id, reportId));
  }

  @PutMapping("/{id}/descriptiveImage")
  public ResponseEntity<Boolean> updateDescriptiveImage(@PathVariable Long id,
      @RequestBody Map<String, String> request) {
    String newImage = request.get("newImage");
    boolean updated = problemService.updateDescriptiveImage(id, newImage);
    return new ResponseEntity<>(updated, HttpStatus.OK);
  }

  @PutMapping("/{id}/description")
  public ResponseEntity<Boolean> updateDescription(@PathVariable Long id, @RequestBody Map<String, String> request) {
    String newDescription = request.get("newDescription");
    boolean updated = problemService.updateDescription(id, newDescription);
    return new ResponseEntity<>(updated, HttpStatus.OK);
  }

  @DeleteMapping("/user/{userId}/remove-problem/{problemId}")
  public ResponseEntity<User> removeProblemFromUser(@PathVariable Long userId, @PathVariable Long problemId) {
    return ResponseEntity.ok(problemService.removeProblemFromUser(userId, problemId));
  }

  @GetMapping("/{id}/problems")
  public ResponseEntity<List<Problem>> getUserProblems(@PathVariable Long id) {
    return ResponseEntity.ok(problemService.getUserProblems(id));
  }

  @PostMapping("/user/{userId}/add-problem/{problemId}")
  public ResponseEntity<User> addProblemToUser(@PathVariable Long userId, @PathVariable Long problemId) {
    return ResponseEntity.ok(problemService.addProblemToUser(userId, problemId));
  }

}