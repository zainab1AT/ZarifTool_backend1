package com.project.physio_backend.Services.ProgressService;

import com.project.physio_backend.Entities.Excercises.Exercise;
import com.project.physio_backend.Entities.Progress.Progress;

import java.util.List;

public interface ProgressService {
  List<Progress> getAllProgresses();

  Progress getProgressById(Long id);

  Progress createProgress(Long userID, Long problemID, Progress progress);

  Progress updateProgress(Long id, Progress progress);

  void deleteProgress(Long id);

  Progress addProgressPercentage(Long id, double percentage);

  List<Progress> getAllProgressesForAUserInAProblem(Long userID, Long problemID);

  List<Exercise> getAllExercisesForProgress(Long id);
}