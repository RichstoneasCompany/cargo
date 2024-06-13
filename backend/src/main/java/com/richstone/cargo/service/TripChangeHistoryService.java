package com.richstone.cargo.service;

import com.richstone.cargo.dto.TripChangeHistoryDto;
import com.richstone.cargo.model.TripChangeHistory;

import java.security.Principal;
import java.util.List;

public interface TripChangeHistoryService {
    void saveChangeHistory(TripChangeHistory changeHistory);
    List<TripChangeHistoryDto> getChangeHistoryForDriver(Principal principal);

}
