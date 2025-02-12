package com.project.physio_backend.Services.ProblemService;

import com.project.physio_backend.Entities.Problems.Problem;

import com.project.physio_backend.Entities.Users.User;

import java.util.List;

public interface ProblemService {
  List<Problem> getAllProblems();

  Problem getProblemById(Long id);

  Problem createProblem(Problem problem);

  Problem updateProblem(Long id, Problem problem);

  void deleteProblem(Long id);

  User addProblemToUser(Long userId, Long problemId);

  User removeProblemFromUser(Long userId, Long problemId);

  List<Problem> getUserProblems(Long problemId);

}