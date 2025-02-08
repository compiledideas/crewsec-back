package com.compiledideas.crewsecback.parking.services.implimentations;

import com.compiledideas.crewsecback.exceptions.ResourceNotFoundException;
import com.compiledideas.crewsecback.parking.models.Report;
import com.compiledideas.crewsecback.parking.repositories.ReportRepository;
import com.compiledideas.crewsecback.parking.services.ParkingService;
import com.compiledideas.crewsecback.parking.services.ReportService;
import com.compiledideas.crewsecback.utils.enums.ReportStatus;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service("report_jpa_service")
public class ReportServiceImpl implements ReportService {

    private final ReportRepository repository;
    private final ParkingService parkingService;

    @Override
    public Page<Report> findAllReports(Integer page, Integer limit) {
        return repository.findAll(PageRequest.of(page, limit));
    }

    @Override
    public Report findReportById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Report", "id", id));
    }

    @Override
    public Page<Report> findReportByParking(Long parkingId, Integer page, Integer limit) {
        var parking = parkingService.findParkingById(parkingId);
        return repository.findByParking(parking, PageRequest.of(page, limit));
    }

    @Override
    public Page<Report> findReportByUsername(String email, Integer page, Integer limit) {
        var parking = parkingService.findParkingByUserEmail(email);
        return repository.findByParking(parking, PageRequest.of(page, limit));
    }


    @Override
    public Report createReport(Report report) {
        return repository.save(report);
    }

    @Override
    public Report updateReport(Long id, Report report) {
        report.setId(id);
        return repository.save(report);
    }

    @Override
    public Report resolveReport(Long id) {
        var report = findReportById(id);
        report.setStatus(ReportStatus.RESOLVED);
        return repository.save(report);
    }

    @Override
    public Report deleteReport(Long id) {
        var report = findReportById(id);
        repository.deleteById(id);
        return report;
    }
}
