package com.project.physio_backend.Services.ProblemService;

import com.project.physio_backend.Entities.Excercises.Exercise;
import com.project.physio_backend.Entities.Problems.Problem;
import com.project.physio_backend.Entities.Progress.Progress;
import com.project.physio_backend.Entities.Reports.Report;
import com.project.physio_backend.Entities.Users.User;
import com.project.physio_backend.Exceptions.Problems.ProblemNotFound;
import com.project.physio_backend.Repositories.ExerciseRepository;
import com.project.physio_backend.Repositories.ProblemRepository;
import com.project.physio_backend.Repositories.ProgressRepository;
import com.project.physio_backend.Repositories.ReportRepository;
import com.project.physio_backend.Repositories.UserRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProblemServiceImpl implements ProblemService {

  private final ProblemRepository problemRepository;
  private final ExerciseRepository exerciseRepositry;
  private final ReportRepository reportRepository;
  private final ProgressRepository progressRepository;
  private final UserRepository userRepository;

  public ProblemServiceImpl(ProblemRepository problemRepository,
      ExerciseRepository exerciseRepositry,
      ReportRepository reportRepository,
      ProgressRepository progressRepository,
      UserRepository userRepository) {
    this.problemRepository = problemRepository;
    this.exerciseRepositry = exerciseRepositry;
    this.reportRepository = reportRepository;
    this.progressRepository = progressRepository;
    this.userRepository = userRepository;
  }

  @Override
  public List<Problem> getAllProblems() {
    return problemRepository.findAll();
  }

  @Override
  public Problem getProblemById(Long id) {
    return problemRepository.findById(id)
        .orElseThrow(() -> new ProblemNotFound("Problem not found with id " + id));
  }

  @Override
  public Problem createProblem(Problem problem) {
    return problemRepository.save(problem);
  }

  ///
  @Override
  public Problem updateProblem(Long id, Problem problem) {
    Problem existingProblem = getProblemById(id);
    existingProblem.setDescription(problem.getDescription());
    existingProblem.setDescriptiveImage(problem.getDescriptiveImage());
    return problemRepository.save(existingProblem);
  }

  @Override
  public void deleteProblem(Long id) {
    Problem problem = getProblemById(id);
    problemRepository.delete(problem);
  }

  @Override
  public Problem addReportToProblem(Long id, Long reportId) {
    Problem problem = getProblemById(id);
    Report report = reportRepository.findById(reportId)
        .orElseThrow(() -> new RuntimeException("Report not found"));

    problem.getReports().add(report);
    return problemRepository.save(problem);
  }

  @Override
  public boolean updateDescription(Long id, String newDescription) {
    Problem problem = getProblemById(id);
    problem.setDescription(newDescription);
    problemRepository.save(problem);
    return true;
  }

  @Override
  public boolean updateDescriptiveImage(Long id, String newImage) {
    Problem problem = getProblemById(id);
    problem.setDescriptiveImage(newImage);
    problemRepository.save(problem);
    return true;
  }

  @Override
  public User addProblemToUser(Long userId, Long problemId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

    Problem problem = problemRepository.findById(problemId)
        .orElseThrow(() -> new RuntimeException("Problem not found"));

    if (user.getProblems().contains(problem)) {
      throw new RuntimeException("User already has this problem assigned");
    }

    user.getProblems().add(problem);
    return userRepository.save(user);
  }

  @Override
  public User removeProblemFromUser(Long userId, Long problemId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

    user.getProblems().removeIf(problem -> problem.getProblemID().equals(problemId));

    return userRepository.save(user);
  }

  @Override
  public List<Problem> getUserProblems(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found with id " + userId));
    return user.getProblems();
  }

}
