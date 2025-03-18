package com.project.physio_backend.Services.ProblemService;

import com.project.physio_backend.Entities.Problems.Problem;

import com.project.physio_backend.Entities.Users.User;

import io.jsonwebtoken.io.IOException;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ProblemService {
  List<Problem> getAllProblems();

  Problem getProblemById(Long id);

  Problem createProblemWithImage(Problem problem, MultipartFile multipartFile) throws IOException;

  Problem createProblem(Problem problem) throws IOException;

  Problem updateProblem(Long id, Problem problem, MultipartFile multipartFile);

  void deleteProblem(Long id);

  User addProblemToUser(Long userId, Long problemId);

  User removeProblemFromUser(Long userId, Long problemId);

  List<Problem> getUserProblems(Long problemId);

  List<String> getProblemNames ();

  Problem getProblemByName (String name);

}