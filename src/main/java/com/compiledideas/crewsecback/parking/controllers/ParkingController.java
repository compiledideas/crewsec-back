package com.compiledideas.crewsecback.parking.controllers;

import com.compiledideas.crewsecback.parking.models.Parking;
import com.compiledideas.crewsecback.parking.services.ParkingService;
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
@RequestMapping("/v1/parking")
public class ParkingController {

    private final ParkingService service;
    private final JwtService jwtService;

    @GetMapping("")
    public ResponseEntity<Object> getParking(@RequestParam(name = "page") String page, @RequestParam(name = "limit",required = false, defaultValue = "12") String limit) {

        return ResponseHandler.generateResponse(
          "Getting page of parking",
          HttpStatus.OK,
          service.findAllParkings(Integer.parseInt(page), Integer.parseInt(limit))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getParkingById(@PathVariable String id) {

        return ResponseHandler.generateResponse(
          "Getting page of parking",
          HttpStatus.OK,
          service.findParkingById(Long.parseLong(id))
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Object> getParkingByUserId(@PathVariable("userId") String userId) {
        return ResponseHandler.generateResponse(
                String.format("getting parkings of user '%s'", userId),
                HttpStatus.OK,
                service.findParkingByUser(Long.parseLong(userId))
        );
    }

    @GetMapping("/user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> getParkingByUserEmail(@NonNull HttpServletRequest request) {
        String username = jwtService.extractUsername(request.getHeader("Authorization").substring(7));

        return ResponseHandler.generateResponse(
                String.format("getting parking of user '%s'", username),
                HttpStatus.OK,
                service.findParkingByUserEmail(username)
        );
    }

    @PostMapping("/")
    public ResponseEntity<Object> createParking(@RequestBody Parking parking) {
        return ResponseHandler.generateResponse(
          "Added new parking successfully.",
          HttpStatus.CREATED,
          service.createParking(parking)
        );
    }

    @PostMapping("/{parkingId}")
    public ResponseEntity<Object> updateParking(@PathVariable("parkingId") String parkingId, @RequestBody Parking parking) {
        return ResponseHandler.generateResponse(
                String.format("Updated parking '%s' successfully.", parkingId),
                HttpStatus.OK,
                service.updateParking(Long.parseLong(parkingId), parking)
        );
    }

    @DeleteMapping("/{parkingId}")
    public ResponseEntity<Object> deleteParking(@PathVariable("parkingId") String parkingId) {
        return ResponseHandler.generateResponse(
                String.format("Deleted parking '%s' successfully.", parkingId),
                HttpStatus.OK,
                service.deleteParking(Long.parseLong(parkingId))
        );
    }
}
