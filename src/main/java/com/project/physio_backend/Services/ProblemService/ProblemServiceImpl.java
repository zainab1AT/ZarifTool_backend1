package com.project.physio_backend.Services.ProblemService;

import com.project.physio_backend.Entities.Problems.Problem;
import com.project.physio_backend.Entities.Users.User;
import com.project.physio_backend.Exceptions.Problems.ProblemNotFound;
import com.project.physio_backend.Repositories.ProblemRepository;
import com.project.physio_backend.Repositories.UserRepository;
import com.project.physio_backend.Services.ImageService.ImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProblemServiceImpl implements ProblemService {

  @Autowired
  private ProblemRepository problemRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ImageService imageService;

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
  public Problem createProblemWithImage(Problem problem, MultipartFile multipartFile) {

    Problem problem1 = problemRepository.save(problem);
    imageService.uploadImageForProblem(multipartFile, problem1.getProblemID());
    return problem1;
  }

  @Override
  public Problem createProblem(Problem problem) {
    return problemRepository.save(problem);
  }

  @Override
  public Problem updateProblem(Long id, Problem problem, MultipartFile multipartFile) {
    Problem existingProblem = getProblemById(id);

    existingProblem.setName(problem.getName());
    existingProblem.setDescription(problem.getDescription());

    if (multipartFile != null && !multipartFile.isEmpty()) {
      imageService.uploadImageForProblem(multipartFile, existingProblem.getProblemID());
    }

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
