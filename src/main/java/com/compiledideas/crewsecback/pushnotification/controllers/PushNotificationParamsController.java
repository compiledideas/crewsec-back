package com.compiledideas.crewsecback.pushnotification.controllers;


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
        var old = repository.findAllByAdminId(params.getAdminId()).orElse(repository.save(params));

        var changed = !old.getAdminToken().equals(params.getAdminToken());

        return ResponseHandler.generateResponse(
                changed ? "Changed Successfully" : "Token just created",
                HttpStatus.OK,
                old
        );
    }
}
