package com.richstone.cargo.dto;

import com.richstone.cargo.model.types.OtpStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpResponseDto {
    @Schema(description = "Статус отправки OTP", example = "SUCCESS")
    private OtpStatus status;
    @Schema(description = "Сообщение, описывающее результат отправки или проверки OTP", example = "OTP sent successfully.")
    private String message;
}
