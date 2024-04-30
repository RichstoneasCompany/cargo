package com.richstone.cargo.mapper;

import com.richstone.cargo.dto.QuestionAddDto;
import com.richstone.cargo.dto.QuestionDto;
import com.richstone.cargo.model.Question;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface QuestionMapper {
    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    QuestionDto toDto(Question question);

    Question fromDtoToQuestion(QuestionAddDto questionAddDto);
}
