package com.compiledideas.crewsecback.parking.repositories;

import com.compiledideas.crewsecback.parking.models.Parking;
import com.compiledideas.crewsecback.parking.models.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Page<Vehicle> findAllByParking(Parking parking, Pageable pageable);
    Page<Vehicle> findAllByReferenceContainingIgnoreCase(String reference, Pageable pageable);
    Page<Vehicle> findAllByParkingAndReferenceContainingIgnoreCase(Parking parking, String reference, Pageable pageable);

}
