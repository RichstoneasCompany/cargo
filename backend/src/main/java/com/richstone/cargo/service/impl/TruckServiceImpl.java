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
import com.richstone.cargo.model.User;
import com.richstone.cargo.repository.TruckRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;


@Service
@Slf4j
@RequiredArgsConstructor
public class TruckServiceImpl {
    private final TruckRepository truckRepository;
    private final UserServiceImpl userService;
    private final DriverServiceImpl driverService;

    public Truck addTruck(TruckRequestDto truckDto) {
        Truck truck = TruckMapper.INSTANCE.dtoToTruck(truckDto);
        Truck savedTruck = truckRepository.save(truck);
        log.info("New truck added successfully: {}", savedTruck.getId());
        return savedTruck;
    }

    public Truck getTruckByDriver(Driver driver) {
        Truck truck = truckRepository.findTruckByDriverId(driver.getId())
                .orElseThrow(() -> {
                    log.error("Truck not found with this driver id: {}", driver.getId());
                    return new TruckNotFoundException("Truck id is not valid");
                });

        log.info("Truck found with driver id: {}", driver.getId());

        return truck;
    }
    public void save(Truck truck){
        truckRepository.save(truck);
        log.info("Truck saved successfully: {}", truck.getId());
    }
    public TruckDto getTruckByPrincipal(Principal principal){
        log.info("Fetching truck by principal: {}", principal.getName());
        User user = userService.getUserByPrincipal(principal);
        Driver driver = driverService.findByUserId(user.getId());
        Truck truck = getTruckByDriver(driver);
        TruckDto truckDto = TruckMapper.INSTANCE.truckToDto(truck);
        log.info("Successfully fetched truck by principal: {}", principal.getName());
        return truckDto;
    }




}
