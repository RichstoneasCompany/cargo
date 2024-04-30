package com.richstone.cargo.service.impl;

import com.richstone.cargo.dto.TopicRequestDto;
import com.richstone.cargo.dto.TruckDto;
import com.richstone.cargo.dto.TruckRequestDto;
import com.richstone.cargo.exception.TopicNotFoundException;
import com.richstone.cargo.exception.TruckNotFoundException;
import com.richstone.cargo.mapper.TopicMapper;
import com.richstone.cargo.mapper.TruckMapper;
import com.richstone.cargo.model.Driver;
import com.richstone.cargo.model.Topic;
import com.richstone.cargo.model.Truck;
import com.richstone.cargo.repository.TruckRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class TruckServiceImpl {
    private final TruckRepository truckRepository;

    public Truck addTruck(TruckRequestDto truckDto) {
        Truck truck = TruckMapper.INSTANCE.dtoToTruck(truckDto);
        Truck savedTruck = truckRepository.save(truck);
        log.info("New truck added successfully: {}", savedTruck.getId());
        return savedTruck;
    }

    public TruckDto getTruckByDriver(Driver driver) {
        Truck truck = truckRepository.findTruckByDriverId(driver.getId())
                .orElseThrow(() -> {
                    log.error("Truck not found with this driver id: {}", driver.getId());
                    return new TruckNotFoundException("Truck id is not valid");
                });

        log.info("Truck found with driver id: {}", driver.getId());

        return TruckMapper.INSTANCE.truckToDto(truck);
    }
    public void save(Truck truck){
        truckRepository.save(truck);
        log.info("Truck saved successfully: {}", truck.getId());
    }



}
