package com.richstone.cargo.mapper;

import com.richstone.cargo.dto.IncomeDto;
import com.richstone.cargo.model.Income;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IncomeMapper {
    IncomeMapper INSTANCE = Mappers.getMapper(IncomeMapper.class);

    IncomeDto incomeToIncomeDTO(Income income);

    List<IncomeDto> incomesToIncomeDTOs(List<Income> incomes);
}
