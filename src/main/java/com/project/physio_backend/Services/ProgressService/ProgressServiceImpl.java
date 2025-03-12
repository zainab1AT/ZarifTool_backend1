package com.project.physio_backend.Services.ProgressService;

import com.project.physio_backend.Entities.Excercises.Exercise;
import com.project.physio_backend.Entities.Problems.Problem;
import com.project.physio_backend.Entities.Progress.Progress;
import com.project.physio_backend.Entities.Users.User;
import com.project.physio_backend.Exceptions.Problems.ProblemNotFound;
import com.project.physio_backend.Exceptions.Progress.ProgressNotFound;
import com.project.physio_backend.Exceptions.Users.UserNotFoundException;
import com.project.physio_backend.Repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProgressServiceImpl implements ProgressService {

  @Autowired
  private ProblemRepository problemRepository;

  @Autowired
  private ProgressRepository progressRepository;

  @Autowired
  private UserRepository userRepository;

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
  public Progress createProgress(Long userID, Long problemID, Progress progress) {

    Problem problem = problemRepository.findById(problemID)
        .orElseThrow(() -> new ProblemNotFound("Problem not found with id " + problemID));

    User user = userRepository.findById(userID)
        .orElseThrow(() -> new UserNotFoundException(userID));

    progress.setTimestamp(LocalDateTime.now());

    progress.setProblem(problem);
    progress.setUser(user);
    problem.addProgress(progress);
    user.addProgress(progress);
    List<Exercise> problemExercises = problem.getExercises();
    Collections.shuffle(problemExercises);
    List<Exercise> randomExercises = problemExercises.stream()
        .limit(5)
        .collect(Collectors.toList());
    progress.setExercises(randomExercises);
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

  @Override
  public Progress addProgressPercentage(Long id, double percentage) {
    Progress progress = getProgressById(id);
    progress.setPercentag(progress.getPercentag() + percentage);
    return progressRepository.save(progress);
  }

}