package com.compiledideas.crewsecback.parking.repositories;

import com.compiledideas.crewsecback.parking.models.Parking;
import com.compiledideas.crewsecback.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {
    Optional<Parking> findByUser(User user);
}
