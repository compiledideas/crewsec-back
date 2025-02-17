package com.compiledideas.crewsecback.parking.controllers;

import com.compiledideas.crewsecback.exceptions.NotificationException;
import com.compiledideas.crewsecback.parking.models.Report;
import com.compiledideas.crewsecback.parking.services.ReportService;
import com.compiledideas.crewsecback.pushnotification.model.NotificationRequest;
import com.compiledideas.crewsecback.pushnotification.services.FCMService;
import com.compiledideas.crewsecback.pushnotification.services.PushParamService;
import com.compiledideas.crewsecback.security.service.JwtService;
import com.compiledideas.crewsecback.utils.ResponseHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/report")
public class ReportController {

    private final ReportService service;
    private final JwtService jwtService;
    private final FCMService fcmService;
    private final PushParamService pushParamService;

    @GetMapping("")
    public ResponseEntity<Object> findReports(@RequestParam(name = "page") String page, @RequestParam(name = "limit",required = false, defaultValue = "12") String limit) {

        return ResponseHandler.generateResponse(
          "Getting page of reports",
          HttpStatus.OK,
          service.findAllReports(Integer.parseInt(page), Integer.parseInt(limit))      
        );
    }
    
    @GetMapping("/parking/{parkingId}")
    public ResponseEntity<Object> findReportByParking(@PathVariable("parkingId") String parkingId, @RequestParam(name = "page") String page, @RequestParam(name = "limit",required = false, defaultValue = "12") String limit) {
        return ResponseHandler.generateResponse(
                String.format("getting reports of parking '%s'", parkingId),
                HttpStatus.OK,
                service.findReportByParking(Long.parseLong(parkingId), Integer.parseInt(page), Integer.parseInt(limit))
        );
    }

    @GetMapping("/user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> findReportByAuthenticatedUser(@NonNull HttpServletRequest request, @RequestParam(name = "page") String page, @RequestParam(name = "limit",required = false, defaultValue = "12") String limit) {
        String username = jwtService.extractUsername(request.getHeader("Authorization").substring(7));

        return ResponseHandler.generateResponse(
                String.format("getting reports of parking of user by username '%s'", username),
                HttpStatus.OK,
                service.findReportByUsername(username, Integer.parseInt(page), Integer.parseInt(limit))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findReportById(@PathVariable("id") String id) {
        return ResponseHandler.generateResponse(
                String.format("getting report by id '%s'", id),
                HttpStatus.OK,
                service.findReportById(Long.parseLong(id))
        );
    }
    
    @PostMapping("/")
    public ResponseEntity<Object> createReport(@RequestBody Report report) {

        var notification = "Parking " + report.getParking().getName() + " created a new report for disturbing for client " + report.getName() + " was disturbed by user " + report.getDisturbingName();

        pushParamService.getAllAdminsParams().forEach(item -> {
            try {
                fcmService.sendMessageToToken(new NotificationRequest("New markulera", notification, null, item.getAdminToken()));
            } catch (Exception e) {
                throw new NotificationException("Can't send notification. " + e.getMessage());
            }
        });

        return ResponseHandler.generateResponse(
          "Added new parking successfully.",
          HttpStatus.CREATED,
          service.createReport(report)      
        );
    }
    
    @PostMapping("/{reportId}")
    public ResponseEntity<Object> updateReport(@PathVariable("reportId") String reportId, @RequestBody Report report) {
        return ResponseHandler.generateResponse(
                String.format("Updated report '%s' successfully.", reportId),
                HttpStatus.OK,
                service.updateReport(Long.parseLong(reportId), report)
        );
    }

    @PostMapping("/resolve")
    public ResponseEntity<Object> resolveReport(@RequestParam(name = "id") String id) {
        return ResponseHandler.generateResponse(
                String.format("resolved report '%s' successfully.", id),
                HttpStatus.OK,
                service.resolveReport(Long.parseLong(id))
        );
    }
    
    @DeleteMapping("/{reportId}")
    public ResponseEntity<Object> deleteReport(@PathVariable("reportId") String reportId) {
        return ResponseHandler.generateResponse(
                String.format("Deleted report '%s' successfully.", reportId),
                HttpStatus.OK,
                service.deleteReport(Long.parseLong(reportId))
        );
    }
}
