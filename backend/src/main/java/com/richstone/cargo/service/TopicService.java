package com.richstone.cargo.service;

import com.richstone.cargo.dto.TopicDto;
import com.richstone.cargo.dto.TopicRequestDto;

import java.util.List;

public interface TopicService {
    void addTopic(TopicRequestDto topicDto);

    TopicRequestDto getTopicById(Long id);
}
