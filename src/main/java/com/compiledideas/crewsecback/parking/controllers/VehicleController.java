package com.compiledideas.crewsecback.parking.controllers;

import com.compiledideas.crewsecback.parking.models.Vehicle;
import com.compiledideas.crewsecback.parking.services.VehicleService;
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
@RequestMapping("/v1/vehicle")
public class VehicleController {

    private final VehicleService service;
    private final JwtService jwtService;

    @GetMapping("")
    public ResponseEntity<Object> findVehicles(@RequestParam(name = "page") String page, @RequestParam(name = "query", required = false, defaultValue = "") String query, @RequestParam(name = "limit",required = false, defaultValue = "12") String limit) {
        
        return ResponseHandler.generateResponse(
          "Getting page of Vehicles",
          HttpStatus.OK,
          service.searchAllVehicles(Integer.parseInt(page), Integer.parseInt(limit), query)
        );
    }

    @GetMapping("/parking/{parkingId}")
    public ResponseEntity<Object> findVehiclesByParking(@PathVariable String parkingId, @RequestParam(name = "page") String page, @RequestParam(name = "query", required = false, defaultValue = "") String query, @RequestParam(name = "limit",required = false, defaultValue = "12") String limit) {

        return ResponseHandler.generateResponse(
          "Getting page of Vehicles by Parking "  + parkingId,
          HttpStatus.OK,
          service.searchAllVehiclesByParking(Long.parseLong(parkingId), Integer.parseInt(page), Integer.parseInt(limit), query)
        );
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Object> findVehicleById(@PathVariable("id") String id) {
        return ResponseHandler.generateResponse(
                String.format("getting Vehicle by id '%s'", id),
                HttpStatus.OK,
                service.findVehicleById(Long.parseLong(id))
        );
    }

    @GetMapping("/user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> findVehiclesByAuthenticatedUser(@NonNull HttpServletRequest request, @RequestParam(name = "page") String page, @RequestParam(name = "limit",required = false, defaultValue = "12") String limit) {
        String username = jwtService.extractUsername(request.getHeader("Authorization").substring(7));

        return ResponseHandler.generateResponse(
                String.format("getting vehicles of parking of user by username '%s'", username),
                HttpStatus.OK,
                service.findAllVehiclesByUserEmail(username, Integer.parseInt(page), Integer.parseInt(limit))
        );
    }
    
    @PostMapping("/")
    public ResponseEntity<Object> createParking(@RequestBody Vehicle vehicle) {
        return ResponseHandler.generateResponse(
          "Added new Vehicle successfully.",
          HttpStatus.CREATED,
          service.createVehicle(vehicle)
        );
    }
    
    @PostMapping("/{vehicleId}")
    public ResponseEntity<Object> updateParking(@PathVariable("vehicleId") String vehicleId, @RequestBody Vehicle vehicle) {
        return ResponseHandler.generateResponse(
                String.format("Updated Vehicle '%s' successfully.", vehicleId),
                HttpStatus.OK,
                service.updateVehicle(Long.parseLong(vehicleId), vehicle)
        );
    }
    
    @DeleteMapping("/{vehicleId}")
    public ResponseEntity<Object> deleteParking(@PathVariable("vehicleId") String vehicleId) {
        return ResponseHandler.generateResponse(
                String.format("Deleted Vehicle '%s' successfully.", vehicleId),
                HttpStatus.OK,
                service.deleteVehicle(Long.parseLong(vehicleId))
        );
    }
}
