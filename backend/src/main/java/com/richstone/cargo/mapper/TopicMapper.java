package com.richstone.cargo.mapper;

import com.richstone.cargo.dto.TopicRequestDto;
import com.richstone.cargo.dto.TopicDto;
import com.richstone.cargo.model.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TopicMapper {
    TopicMapper INSTANCE = Mappers.getMapper(TopicMapper.class);

    Topic dtoToTopic(TopicDto topicDto);

    TopicRequestDto toDto(Topic topic);

    TopicDto toResponseDto(Topic topic);

    Topic topicRequestToTopic(TopicRequestDto topicRequestDto);
}
