package com.richstone.cargo.service.impl;

import com.richstone.cargo.dto.ExpenseDto;
import com.richstone.cargo.dto.IncomeDto;
import com.richstone.cargo.dto.PayslipDto;
import com.richstone.cargo.dto.PayslipResponseDto;
import com.richstone.cargo.mapper.ExpenseMapper;
import com.richstone.cargo.mapper.IncomeMapper;
import com.richstone.cargo.mapper.PayslipMapper;
import com.richstone.cargo.model.Expense;
import com.richstone.cargo.model.Income;
import com.richstone.cargo.model.Payslip;
import com.richstone.cargo.repository.PayslipRepository;
import com.richstone.cargo.service.PayslipService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayslipServiceImpl implements PayslipService {

    private final PayslipRepository payslipRepository;
    private final TripServiceImpl tripService;
    private final IncomeServiceImpl incomeService;
    private final ExpenseServiceImpl expenseService;
    @Cacheable(value = "payslipCache", key = "#payslipDto.driverId.toString() + '-' + #payslipDto.periodStart.toString() + '-' + #payslipDto.periodEnd.toString()")
    public PayslipResponseDto generatePayslip(PayslipDto payslipDto) {
        List<Long> tripIds = tripService.findTripIdsByDriverId(payslipDto.getDriverId(), payslipDto.getPeriodStart(), payslipDto.getPeriodEnd());
        List<Income> incomes = incomeService.findIncomesByTripIds(tripIds);
        List<Expense> expenses = expenseService.findExpensesByTripIds(tripIds);
        List<IncomeDto> incomeDtos = IncomeMapper.INSTANCE.incomesToIncomeDTOs(incomes);
        List<ExpenseDto> expenseDtos = ExpenseMapper.INSTANCE.expensesToExpensesDTOs(expenses);

        Payslip payslip = Payslip.builder()
                .driver(payslipDto.getDriverId())
                .incomes(incomes)
                .expenses(expenses)
                .periodStart(payslipDto.getPeriodStart())
                .periodEnd(payslipDto.getPeriodEnd())
                .build();
        payslipRepository.save(payslip);

        PayslipResponseDto dto = PayslipMapper.INSTANCE.toDto(payslip);
        dto.setIncomes(incomeDtos);
        dto.setExpenses(expenseDtos);

        return dto;
    }

}
