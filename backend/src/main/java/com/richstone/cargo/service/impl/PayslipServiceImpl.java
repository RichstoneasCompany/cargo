package com.richstone.cargo.service.impl;

import com.richstone.cargo.dto.*;
import com.richstone.cargo.mapper.ExpenseMapper;
import com.richstone.cargo.mapper.IncomeMapper;
import com.richstone.cargo.mapper.PayslipMapper;
import com.richstone.cargo.model.*;
import com.richstone.cargo.model.types.ExpenseType;
import com.richstone.cargo.repository.PayslipRepository;
import com.richstone.cargo.service.PayslipService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayslipServiceImpl implements PayslipService {

    private final PayslipRepository payslipRepository;
    private final TripServiceImpl tripService;
    private final IncomeServiceImpl incomeService;
    private final ExpenseServiceImpl expenseService;
    private final DriverServiceImpl driverService;
    private final UserServiceImpl userService;

    @Cacheable(value = "payslipCache", key = "#payslipDto.driverId.toString() + '-' + #payslipDto.periodStart.toString() + '-' + #payslipDto.periodEnd.toString()")
    public PayslipResponseDto generatePayslip(PayslipDto payslipDto) {
        List<Long> tripIds = tripService.findTripIdsByDriverId(payslipDto.getDriverId(), payslipDto.getPeriodStart(), payslipDto.getPeriodEnd());
        List<Income> incomes = incomeService.findIncomesByTripIds(tripIds);
        List<Expense> expenses = expenseService.findFinesByTripIds(tripIds, ExpenseType.FINES);
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
    @Cacheable(value = "payslips", key = "#principal.name + '-' + #yearMonth.toString()")
    public DriverPayslipDto generatePayslipForMonth(Principal principal, YearMonth yearMonth) {
        log.info("Generating payslip for principal: {} and date: {}", principal.getName(), yearMonth);
        User user = userService.getUserByPrincipal(principal);
        Driver driver = driverService.findByUserId(user.getId());
        LocalDate periodStart = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        LocalDate periodEnd = periodStart.withDayOfMonth(periodStart.lengthOfMonth());

        List<Long> tripIds = tripService.findTripIdsByDriverId(driver.getId(), periodStart, periodEnd);
        List<Income> incomes = incomeService.findIncomesByTripIds(tripIds);
        List<Expense> expenses = expenseService.findFinesByTripIds(tripIds, ExpenseType.FINES);

        double totalIncome = calculateTotalIncome(incomes);
        double totalFine = calculateTotalFine(expenses);
        double baseSalary = calculateTripBaseSalary(totalIncome);
        int numberOfTrips = tripIds.size();
        int bonus = calculateBonus(numberOfTrips, incomes);
        double incomeTax = 30000.0;
        double totalPay = baseSalary + bonus - incomeTax - totalFine;
        DriverPayslipDto payslipDto = DriverPayslipDto.builder()
                .incomeTax(incomeTax)
                .baseSalary(baseSalary)
                .bonuses(bonus)
                .totalPay(totalPay)
                .fine(totalFine)
                .build();
        log.info("Generated payslip: {}", payslipDto);
        return payslipDto;
    }

    private Double calculateTotalIncome(List<Income> incomes) {
        double totalIncome = incomes.stream()
                .mapToDouble(Income::getAmount)
                .sum();
        log.info("Calculated total income: {}", totalIncome);
        return totalIncome;
    }

    private Double calculateTotalFine(List<Expense> expenses) {
        double totalFine = expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();
        log.info("Calculated total fine: {}", totalFine);
        return totalFine;
    }

    private double calculateTripBaseSalary(Double totalIncome) {
        double baseSalary = totalIncome * 0.3;
        log.info("Calculated base salary: {}", baseSalary);
        return baseSalary;
    }

    private int calculateBonus(int numberOfTrips, List<Income> incomes) {
        double bonusPercentage = 0.05;
        int bonus = 0;
        if (numberOfTrips >= 4) {
            bonus = (int) (incomes.stream()
                    .mapToDouble(Income::getAmount)
                    .sum() * bonusPercentage);
        }
        log.info("Calculated bonus: {}", bonus);
        return bonus;
    }

    public List<PayslipDetailsDto> payslipDetailsForMonth(Principal principal, YearMonth yearMonth) {
        log.info("Generating payslip for principal: {} and date: {}", principal.getName(), yearMonth);
        User user = userService.getUserByPrincipal(principal);
        Driver driver = driverService.findByUserId(user.getId());
        LocalDate periodStart = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        LocalDate periodEnd = periodStart.withDayOfMonth(periodStart.lengthOfMonth());

        List<Long> tripIds = tripService.findTripIdsByDriverId(driver.getId(), periodStart, periodEnd);
        List<Trip> tripsByDriverId = tripService.findTripsByDriverId(driver.getId(), periodStart, periodEnd);

        List<Income> incomes = incomeService.findIncomesByTripIds(tripIds);
        List<Expense> expenses = expenseService.findFinesByTripIds(tripIds, ExpenseType.FINES);

        return tripsByDriverId.stream()
                .map(trip -> {
                    double incomeSum = incomes.stream()
                            .filter(income -> income.getTrip().getId().equals(trip.getId()))
                            .mapToDouble(Income::getAmount)
                            .sum();

                    double totalFine = expenses.stream()
                            .filter(expense -> expense.getTrip().getId().equals(trip.getId()))
                            .mapToDouble(Expense::getAmount)
                            .sum();

                    double baseSalary = incomeSum * 0.3;
                    double totalPay = baseSalary - totalFine;

                    PayslipDetailsDto dto = new PayslipDetailsDto();
                    dto.setDepartureTime(trip.getDepartureTime());
                    dto.setArrivalTime(trip.getArrivalTime());
                    dto.setBaseSalary(totalPay);

                    return dto;
                })
                .collect(Collectors.toList());
    }

}
