package com.richstone.cargo.service.impl;

import com.richstone.cargo.dto.QuestionAddDto;
import com.richstone.cargo.dto.QuestionDto;
import com.richstone.cargo.exception.QuestionNotFoundException;
import com.richstone.cargo.mapper.QuestionMapper;
import com.richstone.cargo.model.Question;
import com.richstone.cargo.repository.QuestionRepository;
import com.richstone.cargo.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    @Override
    public void addQuestionToExistingTopic(QuestionAddDto questionAddDto) {
        Question question = QuestionMapper.INSTANCE.fromDtoToQuestion(questionAddDto);
        questionRepository.save(question);
        log.info("Question added to topic with ID {}: {}", question.getTopicId(), question.getQuestion());
    }

    @Override
    public List<QuestionDto> getAllQuestions(Long id) {
        List<Question> questions = questionRepository.findQuestionByTopicId(id).orElseThrow(() -> {
            log.error("Question not found with this topicId: {}", id);
            return new QuestionNotFoundException("Topic id is not valid", new QuestionNotFoundException());
        });
        log.info("Retrieved {} questions for topic with ID {}", questions.size(), id);
        return questions.stream()
                .map(QuestionMapper.INSTANCE::toDto)
                .toList();
    }

    public Question getQuestion(Long id) {
        Question question = questionRepository.getQuestionById(id).orElseThrow(() -> {
            log.error("Question not found with this id: {}", id);
            return new QuestionNotFoundException("Question id is not valid", new QuestionNotFoundException());
        });
        log.info("Question found with id: {}", id);
        return question;
    }

    public void delete(Long id) {
        log.info("Deleting question: {}", id);
        Question question = getQuestion(id);
        questionRepository.delete(question);
        log.info("Question deleted successfully: {}", id);
    }

}
