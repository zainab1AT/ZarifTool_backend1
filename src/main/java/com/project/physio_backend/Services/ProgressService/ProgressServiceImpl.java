package com.project.physio_backend.Services.ProgressService;

import com.project.physio_backend.Entities.Problems.Problem;
import com.project.physio_backend.Entities.Progress.Progress;
import com.project.physio_backend.Exceptions.Progress.ProgressNotFound;
import com.project.physio_backend.Repositories.ProblemRepository;
import com.project.physio_backend.Repositories.ProgressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgressServiceImpl implements ProgressService {
  private final ProblemRepository problemRepository;

  private final ProgressRepository progressRepository;

  public ProgressServiceImpl(ProgressRepository progressRepository, ProblemRepository problemRepository) {
    this.progressRepository = progressRepository;
    this.problemRepository = problemRepository;

  }

  @Override
  public List<Progress> getAllProgresses() {
    return progressRepository.findAll();
  }

  @Override
  public Progress getProgressById(Long id) {
    return progressRepository.findById(id)
        .orElseThrow(() -> new ProgressNotFound("Progress not found with id " + id));
  }

  @Override
  public Progress createProgress(Progress progress) {
    if (progress.getProblem() == null || progress.getProblem().getProblemID() == null) {
      throw new IllegalArgumentException("Problem ID cannot be null");
    }

    Problem problem = problemRepository.findById(progress.getProblem().getProblemID())
        .orElseThrow(() -> new ProgressNotFound("Problem not found with id " + progress.getProblem().getProblemID()));

    progress.setProblem(problem);
    return progressRepository.save(progress);
  }

  @Override
  public Progress updateProgress(Long id, Progress progress) {
    Progress existingProgress = getProgressById(id);
    existingProgress.setTimestamp(progress.getTimestamp());
    existingProgress.setPercentag(progress.getPercentag());
    return progressRepository.save(existingProgress);
  }

  @Override
  public void deleteProgress(Long id) {
    Progress progress = getProgressById(id);
    progressRepository.delete(progress);
  }
}