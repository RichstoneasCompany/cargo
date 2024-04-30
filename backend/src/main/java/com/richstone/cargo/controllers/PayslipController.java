package com.richstone.cargo.controllers;

import com.richstone.cargo.dto.PayslipDto;
import com.richstone.cargo.dto.PayslipResponseDto;
import com.richstone.cargo.model.Payslip;
import com.richstone.cargo.service.impl.PayslipServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public PayslipResponseDto generatePayslip(@RequestBody PayslipDto payslipDto) {
        return payslipService.generatePayslip(payslipDto);
    }
}
