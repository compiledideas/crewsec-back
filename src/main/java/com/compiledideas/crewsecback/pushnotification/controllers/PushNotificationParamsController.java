package com.compiledideas.crewsecback.pushnotification.controllers;


import com.compiledideas.crewsecback.exceptions.NotificationException;
import com.compiledideas.crewsecback.pushnotification.model.PushNotificationParams;
import com.compiledideas.crewsecback.pushnotification.repository.PushNotificationParamsRepository;
import com.compiledideas.crewsecback.pushnotification.services.PushParamService;
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
        boolean  changed  = false;
        if(params.getAdminId() == null){
            throw new NotificationException("Admin ID is required");
        }
        PushNotificationParams adminParams = params;
        var old = repository.findByAdminId(params.getAdminId());
        if(old.isPresent()){
            adminParams = old.get();
            changed = !adminParams.getAdminToken().equals(params.getAdminToken());
            if(changed){
                adminParams.setAdminToken(params.getAdminToken());
            }
        }


        return ResponseHandler.generateResponse(
                changed ? "Changed Successfully" : "Token just created",
                HttpStatus.OK,
                repository.save(adminParams)
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
