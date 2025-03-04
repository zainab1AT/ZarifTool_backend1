package com.project.physio_backend.Controllers;

import com.project.physio_backend.Entities.Progress.Progress;
import com.project.physio_backend.Services.ProgressService.ProgressService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

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

  @PostMapping("/{userID}/{problemID}")
  @ResponseStatus(HttpStatus.CREATED)
  public Progress createProgress(@PathVariable Long userID, @PathVariable Long problemID, @RequestBody Progress progress) {
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
}
