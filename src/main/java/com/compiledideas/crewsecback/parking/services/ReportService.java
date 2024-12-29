package com.compiledideas.crewsecback.parking.services;

import com.compiledideas.crewsecback.parking.models.Parking;
import com.compiledideas.crewsecback.parking.models.Report;
import com.compiledideas.crewsecback.security.models.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;

@Qualifier("report_jpa_service")
public interface ReportService {

    Page<Report> findAllReports(Integer page, Integer limit);
    Parking findReportById(Long id);
    Parking findReportByUser(User user);
    Parking createReport(Report report);
    Parking updateReport(Long id, Report report);
    Parking deleteReport(Long id);
}