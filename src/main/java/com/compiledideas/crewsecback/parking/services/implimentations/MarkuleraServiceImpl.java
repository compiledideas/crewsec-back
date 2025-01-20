package com.compiledideas.crewsecback.parking.services.implimentations;

import com.compiledideas.crewsecback.exceptions.ResourceNotFoundException;
import com.compiledideas.crewsecback.parking.models.Markulera;
import com.compiledideas.crewsecback.parking.repositories.MarkuleraRepository;
import com.compiledideas.crewsecback.parking.services.MarkuleraService;
import com.compiledideas.crewsecback.parking.services.ParkingService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service("markulera_jpa_service")
public class MarkuleraServiceImpl implements MarkuleraService {

    private final MarkuleraRepository repository;
    private final ParkingService parkingService;

    @Override
    public Page<Markulera> findAllMarkuleras(Integer page, Integer limit) {

        return repository.findAll(PageRequest.of(page, limit));
    }

    @Override
    public Page<Markulera> findAllMarkulerasByParking(Integer page, Integer limit, Long parkingId) {
        var parking = parkingService.findParkingById(parkingId);
        return repository.findAllByParking(PageRequest.of(page, limit), parking);
    }

    @Override
    public Page<Markulera> findAllMarkulerasByUserEmail(Integer page, Integer limit, String email) {
        var parking = parkingService.findParkingByUserEmail(email);
        return repository.findAllByParking(PageRequest.of(page, limit), parking);
    }

    @Override
    public Markulera findMarkuleraById(Long id) {
        return repository.findById(id).orElseThrow(( ) -> new ResourceNotFoundException("can't find markulera with id: " + id));
    }

    @Override
    public Markulera resolveMarkuleraById(Long id) {
        var markulera = findMarkuleraById(id);
        markulera.setResolved(true);
        return repository.save(markulera);
    }

    @Override
    public Markulera createMarkulera(Markulera markulera) {
        return repository.save(markulera);
    }

    @Override
    public Markulera updateMarkulera(Long id, Markulera markulera) {
        markulera.setId(id);
        return repository.save(markulera);
    }

    @Override
    public Markulera deleteMarkulera(Long id) {
        Markulera markulera = findMarkuleraById(id);
        repository.delete(markulera);
        return markulera;
    }
}
