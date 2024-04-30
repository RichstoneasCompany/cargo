package com.richstone.cargo.mapper;

import com.richstone.cargo.dto.PayslipResponseDto;
import com.richstone.cargo.model.Payslip;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PayslipMapper {
    PayslipMapper INSTANCE = Mappers.getMapper(PayslipMapper.class );
    PayslipResponseDto toDto(Payslip payslip);
}
