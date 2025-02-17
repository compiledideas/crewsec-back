package com.compiledideas.crewsecback.pushnotification.repository;

import com.compiledideas.crewsecback.pushnotification.model.PushNotificationParams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PushNotificationParamsRepository extends JpaRepository<PushNotificationParams, Long> {

    Optional<PushNotificationParams> findByUsername(String username);
}
