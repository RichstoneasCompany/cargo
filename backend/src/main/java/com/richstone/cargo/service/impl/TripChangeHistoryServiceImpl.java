package com.richstone.cargo.service.impl;

import com.richstone.cargo.dto.TripChangeHistoryDto;
import com.richstone.cargo.mapper.TripChangeHistoryMapper;
import com.richstone.cargo.model.Driver;
import com.richstone.cargo.model.TripChangeHistory;
import com.richstone.cargo.model.User;
import com.richstone.cargo.repository.TripChangeHistoryRepository;
import com.richstone.cargo.service.TripChangeHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TripChangeHistoryServiceImpl implements TripChangeHistoryService {
    private final TripChangeHistoryRepository tripChangeHistoryRepository;
    private final TripServiceImpl tripService;
    private final DriverServiceImpl driverService;
    private final UserServiceImpl userService;

    @Override
    public void saveChangeHistory(TripChangeHistory changeHistory) {
        log.info("Saving change history: {}", changeHistory);
        tripChangeHistoryRepository.save(changeHistory);
        log.debug("Change history saved: {}", changeHistory);
    }

    @Override
    public List<TripChangeHistoryDto> getChangeHistoryForDriver(Principal principal) {
        log.info("Getting change history for driver with principal: {}", principal.getName());
        User user = userService.getUserByPrincipal(principal);
        Driver driver = driverService.findByUserId(user.getId());
        List<Long> tripIds = tripService.getAllTripIdsByDriver(driver.getId());
        List<TripChangeHistory> changeHistories = tripChangeHistoryRepository.findByTripIdInOrderByChangeTimeDesc(tripIds);
        List<TripChangeHistoryDto> changeHistoryDtos = TripChangeHistoryMapper.INSTANCE.toDtoList(changeHistories);
        log.info("Change history DTOs for principal {}: {}", principal.getName(), changeHistoryDtos);
        return changeHistoryDtos;
    }
}
