package com.compiledideas.crewsecback.parking.services;

import com.compiledideas.crewsecback.parking.models.Vehicle;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;

@Qualifier("vehicle_jpa_service")
public interface VehicleService {
    Page<Vehicle> findAllVehicles(Integer page, Integer limit);
    Page<Vehicle> searchAllVehicles(Integer page, Integer limit, String query);
    Page<Vehicle> findAllVehiclesByParking(Long parkingId, Integer page, Integer limit);
    Page<Vehicle> searchAllVehiclesByParking(Long parkingId, Integer page, Integer limit, String query);
    Page<Vehicle> findAllVehiclesByUserEmail(String email, Integer page, Integer limit);
    Vehicle findVehicleById(Long id);
    Vehicle createVehicle(Vehicle Vehicle);
    Vehicle updateVehicle(Long id, Vehicle Vehicle);
    Vehicle deleteVehicle(Long id);
}
