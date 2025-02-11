package com.project.physio_backend.Services.ProgressService;

import com.project.physio_backend.Entities.Progress.Progress;

import java.util.List;

public interface ProgressService {
  List<Progress> getAllProgresses();

  Progress getProgressById(Long id);

  Progress createProgress(Progress progress);

  Progress updateProgress(Long id, Progress progress);

  void deleteProgress(Long id);
}