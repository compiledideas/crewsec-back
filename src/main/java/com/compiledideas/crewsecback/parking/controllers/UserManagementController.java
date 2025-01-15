package com.compiledideas.crewsecback.parking.controllers;

import com.compiledideas.crewsecback.security.models.User;
import com.compiledideas.crewsecback.security.service.UserService;
import com.compiledideas.crewsecback.utils.ResponseHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/users")
public class UserManagementController {
    
    private UserService service;


    @GetMapping("")
    public ResponseEntity<Object> findUsers(@RequestParam(name = "page") String page, @RequestParam(name = "limit",required = false, defaultValue = "12") String limit) {

        return ResponseHandler.generateResponse(
                "Getting page of Users",
                HttpStatus.OK,
                service.findAll(Integer.parseInt(page), Integer.parseInt(limit))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findUserById(@PathVariable("id") String id) {
        return ResponseHandler.generateResponse(
                String.format("getting User by id '%s'", id),
                HttpStatus.OK,
                service.findById(Long.parseLong(id))
        );
    }

    @PostMapping("/")
    public ResponseEntity<Object> createParking(@RequestBody User User) {
        return ResponseHandler.generateResponse(
                "Added new User successfully.",
                HttpStatus.CREATED,
                service.addUser(User)
        );
    }

    @PostMapping("/{UserId}")
    public ResponseEntity<Object> updateParking(@PathVariable("UserId") String UserId, @RequestBody User User) {
        return ResponseHandler.generateResponse(
                String.format("Updated User '%s' successfully.", UserId),
                HttpStatus.OK,
                service.updateUser(Long.parseLong(UserId), User)
        );
    }

    @DeleteMapping("/{UserId}")
    public ResponseEntity<Object> deleteParking(@PathVariable("UserId") String userId) {
        return ResponseHandler.generateResponse(
                String.format("Deleted User '%s' successfully.", userId),
                HttpStatus.OK,
                service.deleteUserById(Long.parseLong(userId))
        );
    }
}
