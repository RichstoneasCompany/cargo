package com.richstone.cargo.repository;

import com.richstone.cargo.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<List<Question>> findQuestionByTopicId(Long topicId);

    Optional<Question> getQuestionById(Long id);
}