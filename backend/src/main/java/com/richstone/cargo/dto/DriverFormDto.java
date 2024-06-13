package com.richstone.cargo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverFormDto {
    @Schema(description = "Информация о пользователе, который станет водителем.")
    private UserDto user;
    @Schema(description = "Информация о грузовике водителя.")
    private TruckRequestDto truck;
    @Schema(description = "Номер водительского удостоверения водителя")
    private String licenseNumber;
}
