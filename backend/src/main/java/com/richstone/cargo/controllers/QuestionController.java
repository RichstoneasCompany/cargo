package com.richstone.cargo.controllers;

import com.richstone.cargo.dto.QuestionAddDto;
import com.richstone.cargo.dto.QuestionDto;
import com.richstone.cargo.dto.TopicRequestDto;
import com.richstone.cargo.dto.TopicDto;
import com.richstone.cargo.service.impl.QuestionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/questions")
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "QuestionController", description = "API для управления вопросами по темам")
public class QuestionController {
    private final QuestionServiceImpl questionService;

    @PostMapping
    @PreAuthorize("hasAuthority('dispatcher:create')")
    @Operation(summary = "Добавление вопроса к теме",
            description = "Добавляет новый вопрос к существующей теме")
    public ResponseEntity<String> addQuestion(@RequestBody QuestionAddDto questionDto) {
        questionService.addQuestionToExistingTopic(questionDto);
        return ResponseEntity.ok("Question added successfully");
    }
    @GetMapping
    @Operation(summary = "Получение всех вопросов по теме",
            description = "Возвращает список всех вопросов, связанных с указанной темой")
    public ResponseEntity<List<QuestionDto>> getAllQuestions(@RequestBody TopicDto topicDto){
        List<QuestionDto> allQuestions = questionService.getAllQuestions(topicDto.getId());
        return new ResponseEntity<>(allQuestions, HttpStatus.OK);
    }
}
