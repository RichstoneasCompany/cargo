package com.richstone.cargo.mapper;

import com.richstone.cargo.dto.RouteDto;
import com.richstone.cargo.model.Route;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RouteMapper {
    RouteMapper INSTANCE = Mappers.getMapper(RouteMapper.class );
    Route toRoute(RouteDto routeDto);
}
