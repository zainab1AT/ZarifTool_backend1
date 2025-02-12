package com.project.physio_backend.Controllers;

import com.project.physio_backend.Entities.Reports.Report;
import com.project.physio_backend.Services.ReportService.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

  private final ReportService reportService;

  public ReportController(ReportService reportService) {
    this.reportService = reportService;
  }

  @GetMapping
  public List<Report> getAllReports() {
    return reportService.getAllReports();
  }

  @GetMapping("/{id}")
  public Report getReportById(@PathVariable Long id) {
    return reportService.getReportById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Report createReport(@RequestBody Report report) {
    return reportService.createReport(report);
  }

}