package com.compiledideas.crewsecback.parking.services.implimentations;

import com.compiledideas.crewsecback.parking.models.Parking;
import com.compiledideas.crewsecback.parking.models.Report;
import com.compiledideas.crewsecback.parking.repositories.ReportRepository;
import com.compiledideas.crewsecback.parking.services.ReportService;
import com.compiledideas.crewsecback.security.models.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service("report_jpa_service")
public class ReportServiceImpl implements ReportService {

    private final ReportRepository repository;

    @Override
    public Page<Report> findAllReports(Integer page, Integer limit) {
        return null;
    }

    @Override
    public Parking findReportById(Long id) {
        return null;
    }

    @Override
    public Parking findReportByUser(User user) {
        return null;
    }

    @Override
    public Parking createReport(Report report) {
        return null;
    }

    @Override
    public Parking updateReport(Long id, Report report) {
        return null;
    }

    @Override
    public Parking deleteReport(Long id) {
        return null;
    }
}
