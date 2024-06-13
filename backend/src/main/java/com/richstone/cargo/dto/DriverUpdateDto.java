package com.richstone.cargo.dto;

import com.richstone.cargo.model.Driver;
import com.richstone.cargo.model.Image;
import com.richstone.cargo.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriverUpdateDto {
    @Schema(description = "Пользователь, связанный с водителем")
    private User user;
    @Schema(description = "Водитель, информацию о котором необходимо обновить.")
    private Driver driver;
    @Schema(description = "Информация о грузовике в виде DTO которую необходимо обновить.")
    private TruckDto truck;
    @Schema(description = "Фото водителя которую необходимо обновить.")
    private Image image;
}
