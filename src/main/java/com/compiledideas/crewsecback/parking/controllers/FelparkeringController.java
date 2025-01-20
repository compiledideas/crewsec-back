package com.compiledideas.crewsecback.parking.controllers;

import com.compiledideas.crewsecback.parking.models.Felparkering;
import com.compiledideas.crewsecback.parking.services.FelparkeringService;
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
@RequestMapping("/v1/felparkering")
public class FelparkeringController {

    private final FelparkeringService service;
    private final JwtService jwtService;

    @GetMapping("")
    public ResponseEntity<Object> findFelparkerings(@RequestParam(name = "page") String page, @RequestParam(name = "limit",required = false, defaultValue = "12") String limit) {
        return ResponseHandler.generateResponse(
          "Getting page of Felparkerings",
          HttpStatus.OK,
          service.findAllFelparkerings(Integer.parseInt(page), Integer.parseInt(limit))      
        );
    }


    @GetMapping("/parking/{parkingId}")
    public ResponseEntity<Object> findFelparkeringsByParking(
            @RequestParam(name = "page") String page,
            @RequestParam(name = "limit",required = false, defaultValue = "12") String limit,
            @PathVariable String parkingId) {

        service.createFelparkering(new Felparkering());
        return ResponseHandler.generateResponse(
          "Getting page of Felparkerings",
          HttpStatus.OK,
          service.findAllFelparkeringsByParking(Integer.parseInt(page), Integer.parseInt(limit), Long.parseLong(parkingId))
        );
    }

    @GetMapping("/user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> findVehiclesByAuthenticatedUser(@NonNull HttpServletRequest request, @RequestParam(name = "page") String page, @RequestParam(name = "limit",required = false, defaultValue = "12") String limit) {
        String username = jwtService.extractUsername(request.getHeader("Authorization").substring(7));

        return ResponseHandler.generateResponse(
                String.format("getting felparkerings of parking of user by username '%s'", username),
                HttpStatus.OK,
                service.findAllFelparkeringsByUserEmail(Integer.parseInt(page), Integer.parseInt(limit), username)
        );
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Object> findFelparkeringById(@PathVariable("id") String id) {
        return ResponseHandler.generateResponse(
                String.format("getting Felparkerings by id '%s'", id),
                HttpStatus.OK,
                service.findFelparkeringById(Long.parseLong(id))
        );
    }

    @PostMapping("/resolve")
    public ResponseEntity<Object> resolveFelparkeringById(@RequestParam(name = "id") String id) {
        return ResponseHandler.generateResponse(
                String.format("mark Felparkerings by id '%s' as resolved", id),
                HttpStatus.OK,
                service.resolveFelparkeringById(Long.parseLong(id))
        );
    }
    
    @PostMapping("/")
    public ResponseEntity<Object> createParking(@RequestBody Felparkering Felparkering) {
        return ResponseHandler.generateResponse(
          "Added new Felparkering successfully.",
          HttpStatus.CREATED,
          service.createFelparkering(Felparkering)
        );
    }
    
    @PostMapping("/{FelparkeringId}")
    public ResponseEntity<Object> updateParking(@PathVariable("FelparkeringId") String FelparkeringId, @RequestBody Felparkering Felparkering) {
        return ResponseHandler.generateResponse(
                String.format("Updated Felparkering '%s' successfully.", FelparkeringId),
                HttpStatus.OK,
                service.updateFelparkering(Long.parseLong(FelparkeringId), Felparkering)
        );
    }
    
    @DeleteMapping("/{FelparkeringId}")
    public ResponseEntity<Object> deleteParking(@PathVariable("FelparkeringId") String FelparkeringId) {
        return ResponseHandler.generateResponse(
                String.format("Deleted Felparkering '%s' successfully.", FelparkeringId),
                HttpStatus.OK,
                service.deleteFelparkering(Long.parseLong(FelparkeringId))
        );
    }
}
