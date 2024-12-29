package com.compiledideas.crewsecback.parking.repositories;

import com.compiledideas.crewsecback.parking.models.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}
