package com.project.physio_backend.Services.ReportService;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.project.physio_backend.Entities.Reports.Report;

public interface ReportService {
  List<Report> getAllReports();

  Report getReportById(Long id);
 
  Report createReport(Long userId, Report report);

  Report createReportWithImage(Long userId, Report report ,MultipartFile multipartFile) throws IOException;

  Report addProblemsToReport(Long reportId, List<String> problemNames);

}