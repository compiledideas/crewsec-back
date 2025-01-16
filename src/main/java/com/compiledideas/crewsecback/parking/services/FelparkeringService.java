package com.compiledideas.crewsecback.parking.services;

import com.compiledideas.crewsecback.parking.models.Felparkering;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;

@Qualifier("Felparkering_jpa_service")
public interface FelparkeringService {
    Page<Felparkering> findAllFelparkerings(Integer page, Integer limit);
    Page<Felparkering> findAllFelparkeringsByParking(Integer page, Integer limit, Long parkingId);
    Felparkering findFelparkeringById(Long id);
    Felparkering createFelparkering(Felparkering Felparkering);
    Felparkering updateFelparkering(Long id, Felparkering Felparkering);
    Felparkering deleteFelparkering(Long id);
}
