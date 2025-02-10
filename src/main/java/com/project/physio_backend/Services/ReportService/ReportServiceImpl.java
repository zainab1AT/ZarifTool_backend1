package com.project.physio_backend.Services.ReportService;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.physio_backend.Entities.Reports.Report;
import com.project.physio_backend.Exceptions.Reports.ReportNotFound;
import com.project.physio_backend.Repositries.ReportRepository;

@Service
public class ReportServiceImpl implements ReportService {

  private final ReportRepository reportRepository;

  public ReportServiceImpl(ReportRepository reportRepository) {
    this.reportRepository = reportRepository;
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
  public Report createReport(Report report) {
    return reportRepository.save(report);
  }

  @Override
  public Report updateReport(Long id, Report report) {
    Report existingReport = getReportById(id);
    existingReport.setUserProblemImage(report.getUserProblemImage());
    existingReport.setTimestamp(report.getTimestamp());
    return reportRepository.save(existingReport);
  }

  @Override
  public void deleteReport(Long id) {
    Report report = getReportById(id);
    reportRepository.delete(report);
  }
}