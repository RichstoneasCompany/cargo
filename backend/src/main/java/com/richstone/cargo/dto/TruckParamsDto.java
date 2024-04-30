package com.richstone.cargo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TruckParamsDto {
    private double maxPermMass;
    private double mass;
    private double axleLoad;
    private double height;
    private double width;
    private double length;
    private boolean dangerousCargo;
    private boolean explosiveCargo;
}