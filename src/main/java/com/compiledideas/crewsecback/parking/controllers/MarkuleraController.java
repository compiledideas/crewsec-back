package com.compiledideas.crewsecback.parking.controllers;

import com.compiledideas.crewsecback.parking.models.Markulera;
import com.compiledideas.crewsecback.parking.services.MarkuleraService;
import com.compiledideas.crewsecback.utils.ResponseHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/markulera")
public class MarkuleraController {

    private final MarkuleraService service;
    
    @GetMapping("")
    public ResponseEntity<Object> findMarkuleras(@RequestParam(name = "page") String page, @RequestParam(name = "limit",required = false, defaultValue = "12") String limit) {
        service.createMarkulera(new Markulera());
        return ResponseHandler.generateResponse(
          "Getting page of Markuleras",
          HttpStatus.OK,
          service.findAllMarkuleras(Integer.parseInt(page), Integer.parseInt(limit))      
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
    
    @PostMapping("/")
    public ResponseEntity<Object> createParking(@RequestBody Markulera markulera) {
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
