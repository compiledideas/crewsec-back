package com.compiledideas.crewsecback.parking.services.implimentations;

import com.compiledideas.crewsecback.exceptions.ResourceNotFoundException;
import com.compiledideas.crewsecback.parking.models.Felparkering;
import com.compiledideas.crewsecback.parking.repositories.FelparkeringRepository;
import com.compiledideas.crewsecback.parking.services.FelparkeringService;
import com.compiledideas.crewsecback.parking.services.ParkingService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service("Felparkering_jpa_service")
public class FelparkeringServiceImpl implements FelparkeringService {

    private final FelparkeringRepository repository;
    private final ParkingService parkingService;

    @Override
    public Page<Felparkering> findAllFelparkerings(Integer page, Integer limit) {
        return repository.findAll(PageRequest.of(page, limit));
    }

    @Override
    public Page<Felparkering> findAllFelparkeringsByParking(Integer page, Integer limit, Long parkingId) {
        var parking = parkingService.findParkingById(parkingId);
        return repository.findAllByParking(PageRequest.of(page, limit), parking);
    }

    @Override
    public Page<Felparkering> findAllFelparkeringsByUserEmail(Integer page, Integer limit, String email) {
        var parking = parkingService.findParkingByUserEmail(email);
        return repository.findAllByParking(PageRequest.of(page, limit), parking);
    }

    @Override
    public Felparkering findFelparkeringById(Long id) {
        return repository.findById(id).orElseThrow(( ) -> new ResourceNotFoundException("can't find Felparkering with id: " + id));
    }

    @Override
    public Felparkering resolveFelparkeringById(Long id) {
        var felparkering = findFelparkeringById(id);
        felparkering.setResolved(true);
        return repository.save(felparkering);
    }

    @Override
    public Felparkering createFelparkering(Felparkering Felparkering) {
        return repository.save(Felparkering);
    }

    @Override
    public Felparkering updateFelparkering(Long id, Felparkering Felparkering) {
        Felparkering.setId(id);
        return repository.save(Felparkering);
    }

    @Override
    public Felparkering deleteFelparkering(Long id) {
        Felparkering Felparkering = findFelparkeringById(id);
        repository.delete(Felparkering);
        return Felparkering;
    }
}
