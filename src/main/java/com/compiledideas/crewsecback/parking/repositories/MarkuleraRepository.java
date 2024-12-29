package com.compiledideas.crewsecback.parking.repositories;

import com.compiledideas.crewsecback.parking.models.Markulera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkuleraRepository extends JpaRepository<Markulera, Long> {
}
