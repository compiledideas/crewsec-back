package com.compiledideas.crewsecback.parking.services;

import com.compiledideas.crewsecback.parking.models.Parking;
import com.compiledideas.crewsecback.security.models.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;

@Qualifier("parking_jpa_service")
public interface ParkingService {

    Page<Parking> findAllParkings(Integer page, Integer limit);
    Parking findParkingById(Long id);
    Parking findParkingByUser(Long userId);
    Parking createParking(Parking parking);
    Parking updateParking(Long id, Parking parking);
    Parking deleteParking(Long id);
}
