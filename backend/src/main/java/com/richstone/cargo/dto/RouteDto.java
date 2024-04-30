package com.richstone.cargo.dto;

import com.richstone.cargo.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteDto {
    private Location startLocationId;
    private Location endLocationId;
    private String name;
}
