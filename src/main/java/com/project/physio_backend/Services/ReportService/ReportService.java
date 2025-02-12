package com.project.physio_backend.Services.ReportService;

import java.util.List;

import com.project.physio_backend.Entities.Reports.Report;

public interface ReportService {
  List<Report> getAllReports();

  Report getReportById(Long id);

  Report createReport(Report report);

}