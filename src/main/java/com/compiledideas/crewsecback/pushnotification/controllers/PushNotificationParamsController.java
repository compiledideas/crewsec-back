package com.compiledideas.crewsecback.pushnotification.controllers;


import com.compiledideas.crewsecback.exceptions.NotificationException;
import com.compiledideas.crewsecback.pushnotification.model.PushNotificationParams;
import com.compiledideas.crewsecback.pushnotification.repository.PushNotificationParamsRepository;
import com.compiledideas.crewsecback.utils.ResponseHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/push-notification")
public class PushNotificationParamsController {

    private final PushNotificationParamsRepository repository;

    @PostMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> updatePushNotificationToken(@RequestBody PushNotificationParams params) {

        if(params.getAdminId() == null){
            throw new NotificationException("Admin ID is required");
        }

        var old = repository.findByAdminId(params.getAdminId()).orElse(repository.save(params));

        var changed = !old.getAdminToken().equals(params.getAdminToken());

        return ResponseHandler.generateResponse(
                changed ? "Changed Successfully" : "Token just created",
                HttpStatus.OK,
                old
        );
    }


    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getAllParams() {


        return ResponseHandler.generateResponse(
                "getting all params",
                HttpStatus.OK,
                repository.findAll()
        );
    }
}
