package com.richstone.cargo.dto;

import com.richstone.cargo.model.Truck;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteRequestDto {
    private PointDto[] points;
    private TruckParamsDto  truck;
    private String type;
}
