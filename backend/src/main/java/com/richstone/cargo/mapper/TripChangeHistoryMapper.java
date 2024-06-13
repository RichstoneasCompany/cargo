package com.richstone.cargo.mapper;

import com.richstone.cargo.dto.TripChangeHistoryDto;
import com.richstone.cargo.model.TripChangeHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Mapper
public interface TripChangeHistoryMapper {
    TripChangeHistoryMapper INSTANCE = Mappers.getMapper(TripChangeHistoryMapper.class);

    TripChangeHistoryDto toDto(TripChangeHistory tripChangeHistory);

    List<TripChangeHistoryDto> toDtoList(List<TripChangeHistory> tripChangeHistories);
}
