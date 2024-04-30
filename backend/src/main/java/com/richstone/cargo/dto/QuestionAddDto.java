package com.richstone.cargo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAddDto {
    @Schema(description = "ID темы, к которой добавляется вопрос", example = "1")
    private Long topicId;

    @Schema(description = "Текст вопроса", example = "What is the capital of France?")
    private String question;

    @Schema(description = "Ответ на вопрос", example = "Paris")
    private String answer;

}
