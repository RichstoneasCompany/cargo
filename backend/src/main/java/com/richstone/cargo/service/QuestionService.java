package com.richstone.cargo.service;

import com.richstone.cargo.dto.QuestionAddDto;
import com.richstone.cargo.dto.QuestionDto;
import com.richstone.cargo.dto.TopicDto;

import java.util.List;

public interface QuestionService {
     void addQuestionToExistingTopic(QuestionAddDto questionAddDto);
     List<QuestionDto> getAllQuestions(Long id);
}
