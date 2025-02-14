package com.compiledideas.crewsecback.parking.repositories;

import com.compiledideas.crewsecback.parking.models.Markulera;
import com.compiledideas.crewsecback.parking.models.Parking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkuleraRepository extends JpaRepository<Markulera, Long> {

    Page<Markulera> findAllByParking(Pageable pageable, Parking parking);
}
