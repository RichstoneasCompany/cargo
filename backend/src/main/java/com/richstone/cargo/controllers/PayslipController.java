package com.richstone.cargo.controllers;

import com.richstone.cargo.dto.DriverPayslipDto;
import com.richstone.cargo.dto.PayslipDetailsDto;
import com.richstone.cargo.service.impl.PayslipServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.YearMonth;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/payslip")
@CrossOrigin("*")
@RequiredArgsConstructor
@Tag(name = "PayslipController", description = "API для генерации зарплатных ведомостей")
public class PayslipController {
    private final PayslipServiceImpl payslipService;

    @GetMapping()
    @Operation(summary = "Генерация зарплатной ведомости",
            description = "Создает зарплатную ведомость для водителя за указанный период")
    public ResponseEntity<DriverPayslipDto> generatePayslipForMonth(Principal principal,
                                                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth) {
        DriverPayslipDto payslipDto = payslipService.generatePayslipForMonth(principal, yearMonth);
        return ResponseEntity.ok(payslipDto);
    }

    @GetMapping("/details")
    @Operation(summary = "Получение детальной информации зарплатной ведомости",
            description = "Возвращает даты поездок за определенный месяц и заработок за каждую поездку.")
    public ResponseEntity<List<PayslipDetailsDto>> getPayslipDetailsForMonth(Principal principal,
                                                                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth) {
        List<PayslipDetailsDto> payslipDetails = payslipService.payslipDetailsForMonth(principal, yearMonth);
        return ResponseEntity.ok(payslipDetails);
    }
}
