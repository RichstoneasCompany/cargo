package com.richstone.cargo.controllers;

import com.richstone.cargo.dto.QuestionAddDto;
import com.richstone.cargo.dto.QuestionDto;
import com.richstone.cargo.dto.TopicRequestDto;
import com.richstone.cargo.dto.TripDetailsDto;
import com.richstone.cargo.exception.QuestionNotFoundException;
import com.richstone.cargo.exception.TripNotFoundException;
import com.richstone.cargo.service.impl.QuestionServiceImpl;
import com.richstone.cargo.service.impl.TopicServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/topics")
@RequiredArgsConstructor
@Tag(name = "TopicController", description = "API для управления темами")
public class TopicController {
    private final TopicServiceImpl topicService;
    private final QuestionServiceImpl questionService;

    @PostMapping
    @PreAuthorize("hasAuthority('dispatcher:create')")
    @Operation(summary = "Добавление темы",
            description = "Добавляет новую тему в систему")
    public ResponseEntity<String> addTopic(@RequestBody TopicRequestDto topicDto) {
        topicService.addTopic(topicDto);
        return ResponseEntity.ok("Topic added successfully");
    }

    @GetMapping
    @Operation(summary = "Получение всех тем",
            description = "Возвращает список всех тем")
    public ResponseEntity<List<TopicRequestDto>> getAllTopics() {
        List<TopicRequestDto> topicList = topicService.getAllTopics();
        return new ResponseEntity<>(topicList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение темы по ID",
            description = "Возвращает тему по заданному идентификатору")
    public ResponseEntity<TopicRequestDto> getTopic(@PathVariable Long id) {
        TopicRequestDto topic = topicService.getTopicById(id);
        return new ResponseEntity<>(topic, HttpStatus.OK);
    }

    @GetMapping("/{topicId}/questions")
    @Operation(summary = "Получение всех вопросов для определенной темы",
            description = "Возвращает вопросы для конкретной темы")
    public ResponseEntity<?>  showQuestionsForTopic(@PathVariable Long topicId) {
            questionService.getAllQuestions(topicId);
        try {
            List<QuestionDto> allQuestions = questionService.getAllQuestions(topicId);
            return ResponseEntity.ok(allQuestions);
        }catch (QuestionNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
