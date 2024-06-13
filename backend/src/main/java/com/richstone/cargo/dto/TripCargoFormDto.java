package com.richstone.cargo.dto;

import com.richstone.cargo.model.Cargo;
import com.richstone.cargo.model.Trip;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripCargoFormDto {
    @Schema(description = "Информация о поездке")
    private Trip trip;
    @Schema(description = "Информация о грузе")
    private Cargo cargo;

    @Override
    public String toString() {
        return "TripCargoFormDto{" +
                "tripId=" + (trip != null ? trip.getId() : null) +
                ", cargoId=" + (cargo != null ? cargo.getId() : null) +
                '}';
    }
}
