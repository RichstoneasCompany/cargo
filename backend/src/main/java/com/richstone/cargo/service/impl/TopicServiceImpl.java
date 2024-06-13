package com.richstone.cargo.service.impl;

import com.richstone.cargo.dto.TopicDto;
import com.richstone.cargo.dto.TopicRequestDto;
import com.richstone.cargo.exception.TopicNotFoundException;
import com.richstone.cargo.mapper.TopicMapper;
import com.richstone.cargo.model.Route;
import com.richstone.cargo.model.Topic;
import com.richstone.cargo.repository.TopicRepository;
import com.richstone.cargo.service.TopicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    @Override
    public void addTopic(TopicRequestDto topicDto) {
        Topic topic = TopicMapper.INSTANCE.topicRequestToTopic(topicDto);
        topicRepository.save(topic);
    }


    public List<TopicRequestDto> getAllTopics() {
        List<Topic> topics = topicRepository.findAll();
        log.info("Retrieved {} topics", topics.size());
        return topics.stream()
                .map(TopicMapper.INSTANCE::toDto)
                .toList();
    }

    @Override
    public TopicRequestDto getTopicById(Long id) {
        Topic topic = topicRepository.getTopicById(id).orElseThrow(() -> {
            log.error("Topic not found with this id: {}", id);
            return new TopicNotFoundException("Topic id is not valid", new TopicNotFoundException("Topic id is not valid"));
        });
        log.info("Topic found with id: {}", id);
        return TopicMapper.INSTANCE.toDto(topic);
    }

    public Topic getTopic(Long id) {
        Topic topic = topicRepository.getTopicById(id).orElseThrow(() -> {
            log.error("Topic not found with this id: {}", id);
            return new TopicNotFoundException("Topic id is not valid", new TopicNotFoundException());
        });
        log.info("Topic found with id: {}", id);
        return topic;
    }


    public void delete(Long id) {
        log.info("Deleting topic: {}", id);
        Topic topic = getTopic(id);
        topicRepository.delete(topic);
        log.info("Topic deleted successfully: {}", id);
    }

    public Page<TopicRequestDto> getAllTopics(int pageNo, int pageSize) {
        log.info("Fetching topics for page number: {} with page size: {}", pageNo, pageSize);
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Topic> topics = topicRepository.findAll(pageable);
        log.info("Retrieved {} topics", topics.getNumberOfElements());
        return topics.map(TopicMapper.INSTANCE::toDto);
    }

}
