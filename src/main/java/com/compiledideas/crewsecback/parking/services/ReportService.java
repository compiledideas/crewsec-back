package com.compiledideas.crewsecback.parking.services;

import com.compiledideas.crewsecback.parking.models.Report;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;

@Qualifier("report_jpa_service")
public interface ReportService {

    Page<Report> findAllReports(Integer page, Integer limit);
    Report findReportById(Long id);
    Page<Report> findReportByParking(Long parkingId, Integer page, Integer limit);
    Page<Report> findReportByUsername(String email, Integer page, Integer limit);
    Report createReport(Report report);
    Report updateReport(Long id, Report report);
    Report deleteReport(Long id);
}