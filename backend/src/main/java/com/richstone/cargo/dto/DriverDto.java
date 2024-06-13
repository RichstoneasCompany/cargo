package com.richstone.cargo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverDto {
    @Schema(description = "Имя водителя")
    private String name;
    @Schema(description = "Фамилия водителя")
    private String surname;
}
