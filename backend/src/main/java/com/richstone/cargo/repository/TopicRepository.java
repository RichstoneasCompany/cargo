package com.richstone.cargo.repository;

import com.richstone.cargo.model.Route;
import com.richstone.cargo.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    Optional<Topic> getTopicByName(String name);
    Optional<Topic> getTopicById(Long id);

    Page<Topic> findAll(Pageable pageable);
}
