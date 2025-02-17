package com.compiledideas.crewsecback.pushnotification.services;

import com.compiledideas.crewsecback.pushnotification.model.PushNotificationParams;
import com.compiledideas.crewsecback.pushnotification.repository.PushNotificationParamsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PushParamService {

    private final PushNotificationParamsRepository repository;

    public List<PushNotificationParams> getAllAdminsParams(){
        return repository.findAll();
    }
}
