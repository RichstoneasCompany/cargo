package com.richstone.cargo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PointDto {
    private String type;
    private double x;
    private double y;
}
