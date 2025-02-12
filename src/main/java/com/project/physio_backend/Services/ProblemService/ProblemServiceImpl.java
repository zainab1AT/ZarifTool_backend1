package com.project.physio_backend.Services.ProblemService;

import com.project.physio_backend.Entities.Problems.Problem;
import com.project.physio_backend.Entities.Users.User;
import com.project.physio_backend.Exceptions.Problems.ProblemNotFound;
import com.project.physio_backend.Repositories.ProblemRepository;
import com.project.physio_backend.Repositories.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemServiceImpl implements ProblemService {

  private final ProblemRepository problemRepository;
  private final UserRepository userRepository;

  public ProblemServiceImpl(ProblemRepository problemRepository,

      UserRepository userRepository) {
    this.problemRepository = problemRepository;
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

  @Override
  public Problem updateProblem(Long id, Problem problem) {
    Problem existingProblem = getProblemById(id);
    existingProblem.setName(problem.getName());
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
