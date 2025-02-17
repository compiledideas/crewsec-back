package com.compiledideas.crewsecback.parking.controllers;

import com.compiledideas.crewsecback.exceptions.NotificationException;
import com.compiledideas.crewsecback.parking.models.Markulera;
import com.compiledideas.crewsecback.parking.services.MarkuleraService;
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
@RequestMapping("/v1/markulera")
public class MarkuleraController {

    private final MarkuleraService service;
    private final JwtService jwtService;
    private final FCMService fcmService;
    private final PushParamService pushParamService;

    @GetMapping("")
    public ResponseEntity<Object> findMarkuleras(@RequestParam(name = "page") String page, @RequestParam(name = "limit",required = false, defaultValue = "12") String limit) {
        return ResponseHandler.generateResponse(
          "Getting page of Markuleras",
          HttpStatus.OK,
          service.findAllMarkuleras(Integer.parseInt(page), Integer.parseInt(limit))      
        );
    }

    @GetMapping("/parking/{parkingId}")
    public ResponseEntity<Object> findMarkulerasByParking(
            @RequestParam(name = "page") String page,
            @RequestParam(name = "limit",required = false, defaultValue = "12") String limit,
            @PathVariable String parkingId) {
        return ResponseHandler.generateResponse(
          "Getting page of Markuleras",
          HttpStatus.OK,
          service.findAllMarkulerasByParking(Integer.parseInt(page), Integer.parseInt(limit), Long.parseLong(parkingId))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findMarkuleraById(@PathVariable("id") String id) {
        return ResponseHandler.generateResponse(
                String.format("getting Markuleras by id '%s'", id),
                HttpStatus.OK,
                service.findMarkuleraById(Long.parseLong(id))
        );
    }

    @GetMapping("/user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> findVehiclesByAuthenticatedUser(@NonNull HttpServletRequest request, @RequestParam(name = "page") String page, @RequestParam(name = "limit",required = false, defaultValue = "12") String limit) {
        String username = jwtService.extractUsername(request.getHeader("Authorization").substring(7));

        return ResponseHandler.generateResponse(
                String.format("getting markuleras of parking of user by username '%s'", username),
                HttpStatus.OK,
                service.findAllMarkulerasByUserEmail( Integer.parseInt(page), Integer.parseInt(limit), username)
        );
    }
    
    @PostMapping("/resolve")
    public ResponseEntity<Object> resolveMarkulera(@RequestParam(name = "id") String id) {
        return ResponseHandler.generateResponse(
          "mark markulera with id '" + id + "' as resolved",
          HttpStatus.CREATED,
          service.resolveMarkuleraById(Long.parseLong(id))
        );
    }


    @PostMapping("/")
    public ResponseEntity<Object> createParking(@RequestBody Markulera markulera) {

        var notification = "Parking " + markulera.getParking().getName() + " created a new markulera for car with reference " + markulera.getReference();

        pushParamService.getAllAdminsParams().forEach(item -> {
            try {
                fcmService.sendMessageToToken(new NotificationRequest("New markulera", notification, null, item.getAdminToken()));
            } catch (Exception e) {
                throw new NotificationException("Can't send notification. " + e.getMessage());
            }
        });

        return ResponseHandler.generateResponse(
          "Added new markulera successfully.",
          HttpStatus.CREATED,
          service.createMarkulera(markulera)
        );
    }
    
    @PostMapping("/{MarkuleraId}")
    public ResponseEntity<Object> updateParking(@PathVariable("MarkuleraId") String markuleraId, @RequestBody Markulera markulera) {
        return ResponseHandler.generateResponse(
                String.format("Updated Markulera '%s' successfully.", markuleraId),
                HttpStatus.OK,
                service.updateMarkulera(Long.parseLong(markuleraId), markulera)
        );
    }
    
    @DeleteMapping("/{MarkuleraId}")
    public ResponseEntity<Object> deleteParking(@PathVariable("MarkuleraId") String markuleraId) {
        return ResponseHandler.generateResponse(
                String.format("Deleted Markulera '%s' successfully.", markuleraId),
                HttpStatus.OK,
                service.deleteMarkulera(Long.parseLong(markuleraId))
        );
    }
}
