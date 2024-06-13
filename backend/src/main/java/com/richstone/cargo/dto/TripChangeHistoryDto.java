package com.richstone.cargo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripChangeHistoryDto {
    @Schema(description = "Заголовок изменения")
    private String changeTitle;
    @Schema(description = "Описание изменения")
    private String changeDescription;
    @Schema(description = "Время изменения")
    private LocalDateTime changeTime;

}
