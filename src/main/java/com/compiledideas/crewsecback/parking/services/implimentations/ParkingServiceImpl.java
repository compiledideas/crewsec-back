package com.compiledideas.crewsecback.parking.services.implimentations;

import com.compiledideas.crewsecback.exceptions.ResourceNotFoundException;
import com.compiledideas.crewsecback.parking.models.Parking;
import com.compiledideas.crewsecback.parking.repositories.ParkingRepository;
import com.compiledideas.crewsecback.parking.services.ParkingService;
import com.compiledideas.crewsecback.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service("parking_jpa_service")
public class ParkingServiceImpl implements ParkingService {

    private final ParkingRepository repository;
    private final UserRepository userRepository;

    @Override
    public Page<Parking> findAllParkings(Integer page, Integer limit) {
        return repository.findAll(PageRequest.of(page, limit));
    }

    @Override
    public Parking findParkingById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("can't find parking with id: " + id));
    }

    @Override
    public Parking findParkingByUser(Long userId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("can't find user with id: " + userId));
        return repository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("can't find parking with user_id: " + userId));
    }

    @Override
    public Parking findParkingByUserEmail(String email) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("can't find user with email: " + email));
        return repository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("can't find parking with email: " + email));
    }

    @Override
    public Parking createParking(Parking parking) {
        return repository.save(parking);
    }

    @Override
    public Parking updateParking(Long id, Parking parking) {
        parking.setId(id);
        return repository.save(parking);
    }

    @Override
    public Parking deleteParking(Long id) {
        var parking = findParkingById(id);
        repository.delete(parking);
        return parking;
    }
}
