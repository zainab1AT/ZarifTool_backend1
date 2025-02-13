package com.project.physio_backend.Services.ReportService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.project.physio_backend.Entities.Problems.Problem;
import com.project.physio_backend.Entities.Reports.Report;
import com.project.physio_backend.Entities.Users.User;
import com.project.physio_backend.Exceptions.Reports.ReportNotFound;
import com.project.physio_backend.Repositories.ProblemRepository;
import com.project.physio_backend.Repositories.ReportRepository;
import com.project.physio_backend.Repositories.UserRepository;

@Service
public class ReportServiceImpl implements ReportService {

  private final ReportRepository reportRepository;
  private final UserRepository userRepository;
  private final ProblemRepository problemRepository;

  public ReportServiceImpl(ReportRepository reportRepository, UserRepository userRepository,
      ProblemRepository problemRepository) {
    this.reportRepository = reportRepository;
    this.userRepository = userRepository;
    this.problemRepository = problemRepository;

  }

  @Override
  public List<Report> getAllReports() {
    return reportRepository.findAll();
  }

  @Override
  public Report getReportById(Long id) {
    return reportRepository.findById(id)
        .orElseThrow(() -> new ReportNotFound("Report not found with id " + id));
  }

  @Override
  public Report createReport(Long userId, Report report) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

    report.setUser(user);
    return reportRepository.save(report);
  }

  @Override
  public Report addProblemsToReport(Long reportId, List<String> problemNames) {
    Report report = reportRepository.findById(reportId)
        .orElseThrow(() -> new ReportNotFound("Report not found with id " + reportId));

    Set<Problem> uniqueProblems = new HashSet<>(report.getProblems());

    for (String problemName : problemNames) {
      problemRepository.findByname(problemName).ifPresent(uniqueProblems::add);
    }

    report.setProblems(new ArrayList<>(uniqueProblems));

    return reportRepository.save(report);
  }

}