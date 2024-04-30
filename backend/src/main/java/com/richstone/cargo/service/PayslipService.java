package com.richstone.cargo.service;

import com.richstone.cargo.dto.PayslipDto;
import com.richstone.cargo.dto.PayslipResponseDto;

public interface PayslipService {
    PayslipResponseDto generatePayslip(PayslipDto payslipDto);
}
