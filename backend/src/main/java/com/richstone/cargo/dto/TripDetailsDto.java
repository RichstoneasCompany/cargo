package com.richstone.cargo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripDetailsDto {
    @Schema(description = "Информация о поездке")
    private TripInformationDto tripInformation;
    @Schema(description = "Информация о грузе")
    private CargoDto cargo;
}
