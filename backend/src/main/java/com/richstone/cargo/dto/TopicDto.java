package com.richstone.cargo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicDto {
    @Schema(description = "ID темы", example = "2")
    private Long id;

    @Schema(description = "Название темы", example = "Geography")
    private String name;

}
