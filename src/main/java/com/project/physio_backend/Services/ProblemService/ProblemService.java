package com.project.physio_backend.Services.ProblemService;

import com.project.physio_backend.Entities.Excercises.Exercise;
import com.project.physio_backend.Entities.Problems.Problem;
import com.project.physio_backend.Entities.Progress.Progress;
import com.project.physio_backend.Entities.Reports.Report;

import java.util.List;

public interface ProblemService {
  List<Problem> getAllProblems();

  Problem getProblemById(Long id);

  // Problem createProblem(Problem problem);
  Problem createProblem(String image, String description, List<Exercise> exercises);

  Problem updateProblem(Long id, Problem problem);

  void deleteProblem(Long id);

  List<Report> getProblemReports(Long id);

  List<Exercise> getProblemExercises(Long id);

  List<Progress> getProblemProgress(Long id);

  Problem addExerciseToProblem(Long problemId, Long exerciseId);

  Problem addReportToProblem(Long problemId, Long reportId);

  Problem addProgressToProblem(Long problemId, Long progressId);

  Problem removeExerciseFromProblem(Long problemId, Long exerciseId);

  Problem removeReportFromProblem(Long problemId, Long reportId);

  Problem removeProgressFromProblem(Long problemId, Long progressId);

  boolean updateDescription(Long id, String newDescription);

  boolean updateExercises(Long id, List<Exercise> newExercises);

  boolean updateDescriptiveImage(Long id, String newImage);
}