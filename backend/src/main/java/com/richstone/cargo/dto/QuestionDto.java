package com.richstone.cargo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    @Schema(description = "Текст вопроса", example = "What is the largest planet?")
    private String question;

    @Schema(description = "Ответ на вопрос", example = "Jupiter")
    private String answer;
}
