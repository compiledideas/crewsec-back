package com.compiledideas.crewsecback.parking.controllers;

import com.compiledideas.crewsecback.parking.models.Parking;
import com.compiledideas.crewsecback.parking.models.Report;
import com.compiledideas.crewsecback.parking.services.ParkingService;
import com.compiledideas.crewsecback.parking.services.ReportService;
import com.compiledideas.crewsecback.utils.ResponseHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/report")
public class ReportController {

    private final ReportService service;
    
    @GetMapping("")
    public ResponseEntity<Object> findReports(@RequestParam(name = "page") String page, @RequestParam(name = "limit",required = false, defaultValue = "12") String limit) {

        return ResponseHandler.generateResponse(
          "Getting page of reports",
          HttpStatus.OK,
          service.findAllReports(Integer.parseInt(page), Integer.parseInt(limit))      
        );
    }
    
    @GetMapping("/parking/{parkingId}")
    public ResponseEntity<Object> findReportByParking(@PathVariable("parkingId") String parkingId) {
        return ResponseHandler.generateResponse(
                String.format("getting reports of parking '%s'", parkingId),
                HttpStatus.OK,
                service.findReportByParking(Long.parseLong(parkingId))
        );
    }
    
    @PostMapping("/")
    public ResponseEntity<Object> createParking(@RequestBody Report report) {
        return ResponseHandler.generateResponse(
          "Added new parking successfully.",
          HttpStatus.CREATED,
          service.createReport(report)      
        );
    }
    
    @PostMapping("/{reportId}")
    public ResponseEntity<Object> updateParking(@PathVariable("reportId") String reportId, @RequestBody Report report) {
        return ResponseHandler.generateResponse(
                String.format("Updated report '%s' successfully.", reportId),
                HttpStatus.OK,
                service.updateReport(Long.parseLong(reportId), report)
        );
    }
    
    @DeleteMapping("/{reportId}")
    public ResponseEntity<Object> deleteParking(@PathVariable("reportId") String reportId) {
        return ResponseHandler.generateResponse(
                String.format("Deleted report '%s' successfully.", reportId),
                HttpStatus.OK,
                service.deleteReport(Long.parseLong(reportId))
        );
    }
}
