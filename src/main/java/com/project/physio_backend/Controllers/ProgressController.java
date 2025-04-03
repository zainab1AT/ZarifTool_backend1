package com.project.physio_backend.Controllers;

import com.project.physio_backend.Entities.Excercises.Exercise;
import com.project.physio_backend.Entities.Progress.Progress;
import com.project.physio_backend.Services.ProgressService.ProgressService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/progresses")
@CrossOrigin
public class ProgressController {

  private final ProgressService progressService;

  public ProgressController(ProgressService progressService) {
    this.progressService = progressService;
  }

  @GetMapping
  public List<Progress> getAllProgresses() {
    return progressService.getAllProgresses();
  }

  @GetMapping("/{id}")
  public Progress getProgressById(@PathVariable Long id) {
    return progressService.getProgressById(id);
  }

  @PostMapping("/user/{userID}/problem/{problemID}")
  @ResponseStatus(HttpStatus.CREATED)
  public Progress createProgress(@PathVariable Long userID, @PathVariable Long problemID,
      @RequestBody Progress progress) {
    return progressService.createProgress(userID, problemID, progress);
  }

  @PutMapping("/{id}")
  public Progress updateProgress(@PathVariable Long id, @RequestBody Progress progress) {
    return progressService.updateProgress(id, progress);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteProgress(@PathVariable Long id) {
    progressService.deleteProgress(id);
  }

  @PutMapping("/{id}/percentage")
  public Progress addProgressPercentage(@PathVariable Long id, @RequestBody Double percentage) {
    return progressService.addProgressPercentage(id, percentage);
  }

  @GetMapping("/user/{userID}/problem/{problemID}")
  @ResponseStatus(HttpStatus.CREATED)
  public List<Progress> getAllProgressesForAUserInAProblem(@PathVariable Long userID, @PathVariable Long problemID) {
    return progressService.getAllProgressesForAUserInAProblem(userID, problemID);
  }

  @GetMapping("/user/{userID}")
  @ResponseStatus(HttpStatus.CREATED)
  public List<Progress> getAllProgressesForUser(@PathVariable Long userID) {
    return progressService.getAllProgressesForUser(userID);
  }

  @GetMapping("/{id}/exercises")
  public List<Exercise> getAllExercisesForProgress(@PathVariable Long id) {
    return progressService.getAllExercisesForProgress(id);
  }

  @GetMapping("/{userID}/today")
  public Boolean progressExistTodayForUser(@PathVariable Long userID) {
    return progressService.progressExistTodayForUser(userID);
  }

  @GetMapping("/{userID}/daily")
  public List<Progress> getProgressesForTodayForUser(@PathVariable Long userID) {
    return progressService.getProgressesForTodayForUser(userID);
  }

  @GetMapping("/{userID}/weekly")
  public List<Progress> getWeeklyProgress(@PathVariable Long userID) {
    return progressService.getWeeklyProgress(userID);
  }

  @GetMapping("/{userID}/month")
  public List<String> progressExistThisMonth(@PathVariable Long userID) {
    return progressService.progressExistThisMonth(userID);
  }

}
