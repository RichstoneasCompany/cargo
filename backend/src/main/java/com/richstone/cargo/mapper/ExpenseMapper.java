package com.richstone.cargo.mapper;


import com.richstone.cargo.dto.ExpenseDto;
import com.richstone.cargo.model.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ExpenseMapper {
    ExpenseMapper INSTANCE = Mappers.getMapper(ExpenseMapper.class);

    ExpenseDto expenseToExpenseDTO(Expense income);

    List<ExpenseDto> expensesToExpensesDTOs(List<Expense> incomes);
}
