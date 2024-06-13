package com.richstone.cargo.service.impl;

import com.richstone.cargo.dto.CargoDto;
import com.richstone.cargo.dto.TripDto;
import com.richstone.cargo.exception.CargoNotFoundException;
import com.richstone.cargo.mapper.CargoMapper;
import com.richstone.cargo.model.Cargo;
import com.richstone.cargo.model.Route;
import com.richstone.cargo.model.Trip;
import com.richstone.cargo.repository.CargoRepository;
import com.richstone.cargo.service.CargoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j

public class CargoServiceImpl implements CargoService {
    private final CargoRepository cargoRepository;


    public Cargo save(TripDto tripDto, Trip savedTrip) {
        log.info("Saving new cargo: {}", tripDto.getCargoName());
        Cargo cargo = Cargo.builder()
                .name(tripDto.getCargoName())
                .description(tripDto.getCargoDescription())
                .numberOfPallets(tripDto.getNumberOfPallets())
                .weight(tripDto.getCargoWeight())
                .temperature(tripDto.getTemperature())
                .trip(savedTrip)
                .build();
        cargoRepository.save(cargo);
        log.info("Cargo saved successfully: {}", cargo.getName());
        return cargo;
    }

    public CargoDto getCargoByTripId(Long tripId) {
        log.info("Finding cargo by tripId: {}", tripId);
        Cargo cargo = cargoRepository.findByTripId(tripId)
                .orElseThrow(() -> {
                    log.error("Cargo not found with this id: {}", tripId);
                    return new CargoNotFoundException("Cargo not found with this id: " + tripId);
                });
        log.info("Cargo found with id:  {}", tripId);
        return CargoMapper.INSTANCE.cargoToCargoDto(cargo);
    }

    public void updateCargo(Cargo cargo) {
        cargoRepository.save(cargo);
    }

    public Cargo getCargoByTrip(Long tripId) {
        log.info("Finding cargo by tripId: {}", tripId);
        Cargo cargo = cargoRepository.findByTripId(tripId)
                .orElseThrow(() -> {
                    log.error("Cargo not found with this id: {}", tripId);
                    return new CargoNotFoundException("Cargo not found with this id: " + tripId);
                });
        log.info("Cargo found with id:  {}", tripId);
        return cargo;
    }

}
