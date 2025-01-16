package com.compiledideas.crewsecback.parking.repositories;

import com.compiledideas.crewsecback.parking.models.Felparkering;
import com.compiledideas.crewsecback.parking.models.Parking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FelparkeringRepository extends JpaRepository<Felparkering, Long> {

    Page<Felparkering> findAllByParking(Pageable pageable, Parking parking);
}
