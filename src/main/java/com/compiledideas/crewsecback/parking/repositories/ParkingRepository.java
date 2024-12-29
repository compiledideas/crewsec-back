package com.compiledideas.crewsecback.parking.repositories;

import com.compiledideas.crewsecback.parking.models.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {
}
