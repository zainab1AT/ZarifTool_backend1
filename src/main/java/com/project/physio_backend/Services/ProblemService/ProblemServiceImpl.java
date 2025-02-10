package com.project.physio_backend.Services.ProblemService;

import com.project.physio_backend.Entities.Excercises.Exercise;
import com.project.physio_backend.Entities.Problems.Problem;
import com.project.physio_backend.Entities.Progress.Progress;
import com.project.physio_backend.Entities.Reports.Report;
import com.project.physio_backend.Exceptions.Problems.ProblemNotFound;
import com.project.physio_backend.Repositries.ExerciseRepositry;
import com.project.physio_backend.Repositries.ProblemRepository;
import com.project.physio_backend.Repositries.ProgressRepository;
import com.project.physio_backend.Repositries.ReportRepository;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProblemServiceImpl implements ProblemService {

  private final ProblemRepository problemRepository;
  private final ExerciseRepositry exerciseRepositry;
  private final ReportRepository reportRepository;
  private final ProgressRepository progressRepository;

  public ProblemServiceImpl(ProblemRepository problemRepository,
      ExerciseRepositry exerciseRepositry,
      ReportRepository reportRepository,
      ProgressRepository progressRepository) {
    this.problemRepository = problemRepository;
    this.exerciseRepositry = exerciseRepositry;
    this.reportRepository = reportRepository;
    this.progressRepository = progressRepository;
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

  // @Override
  // public Problem createProblem(Problem problem) {
  // return problemRepository.save(problem);
  // }
  @Override
  public Problem createProblem(String image, String description, List<Exercise> exercises) {
    Problem problem = new Problem();
    problem.setDescriptiveImage(image);
    problem.setDescription(description);
    problem.setExercises(exercises);

    return problemRepository.save(problem);
  }

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
  public List<Report> getProblemReports(Long id) {
    return getProblemById(id).getReports();
  }

  @Override
  public List<Exercise> getProblemExercises(Long id) {
    return getProblemById(id).getExercises();
  }

  @Override
  public List<Progress> getProblemProgress(Long id) {
    return getProblemById(id).getProgresses();
  }

  @Override
  public Problem removeExerciseFromProblem(Long id, Long exerciseId) {
    Problem problem = getProblemById(id);
    problem.getExercises().removeIf(exercise -> exercise.getExerciseID() == exerciseId);
    return problemRepository.save(problem);
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
  public Problem removeReportFromProblem(Long id, Long reportId) {
    Problem problem = getProblemById(id);
    problem.getReports().removeIf(report -> report.getReportID().equals(reportId));
    return problemRepository.save(problem);
  }

  @Override
  public Problem addProgressToProblem(Long id, Long progressId) {
    Problem problem = getProblemById(id);
    Progress progress = progressRepository.findById(progressId)
        .orElseThrow(() -> new RuntimeException("Progress not found"));

    problem.getProgresses().add(progress);
    return problemRepository.save(problem);
  }

  @Override
  public Problem removeProgressFromProblem(Long id, Long progressId) {
    Problem problem = getProblemById(id);
    problem.getProgresses().removeIf(progress -> progress.getProgressID().equals(progressId));
    return problemRepository.save(problem);
  }

  @Override
  public Problem addExerciseToProblem(Long id, Long exerciseId) {
    Problem problem = getProblemById(id);
    // Exercise exercise = ExerciseRepositry.findById(exerciseId)
    // .orElseThrow(() -> new RuntimeException("Exercise not found"));

    // problem.getExercises().add(exercise);
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
  public boolean updateExercises(Long id, List<Exercise> newExercises) {
    Problem problem = getProblemById(id);
    problem.setExercises(newExercises);
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
}
