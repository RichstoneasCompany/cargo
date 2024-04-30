package com.richstone.cargo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto {
    @Schema(description = "Имя пользователя", example = "ivanov")
    private String username;

    @Schema(description = "Имя", example = "Иван")
    private String firstname;

    @Schema(description = "Фамилия", example = "Иванов")
    private String lastname;

    @Schema(description = "Электронная почта", example = "ivanov@example.com")
    private String email;

    @Schema(description = "Телефон", example = "+79991234567")
    private String phone;
    @Schema(description = "Пол", example = "Male")
    private String gender;
    @Schema(description = "Дата рождения", example = "01.01.2001")
    private LocalDate birthDate;
}
