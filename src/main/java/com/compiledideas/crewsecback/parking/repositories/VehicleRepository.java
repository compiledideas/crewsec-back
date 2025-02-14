package com.compiledideas.crewsecback.parking.repositories;

import com.compiledideas.crewsecback.parking.models.Parking;
import com.compiledideas.crewsecback.parking.models.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Page<Vehicle> findAllByParkingAndLeaveDateIsAfter(Parking parking, Date leaveDate, Pageable pageable);
    Page<Vehicle> findAllByParking(Parking parking, Pageable pageable);
    Page<Vehicle> findAllByReferenceContainingIgnoreCase(String reference, Pageable pageable);
    Page<Vehicle> findAllByParkingAndReferenceContainingIgnoreCase(Parking parking, String reference, Pageable pageable);
    Page<Vehicle> findAllByParkingAndReferenceContainingIgnoreCaseAndLeaveDateIsBefore(Parking parking, String reference, Date leaveDate, Pageable pageable);
}
