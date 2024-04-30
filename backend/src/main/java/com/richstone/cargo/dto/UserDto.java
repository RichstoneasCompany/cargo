package com.richstone.cargo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @Schema(description = "Имя пользователя", example = "user123")
    private String username;

    @Schema(description = "Имя", example = "Aibek")
    private String firstname;

    @Schema(description = "Фамилия", example = "Toktarov")
    private String lastname;

    @Schema(description = "Email", example = "aibek.toktarov@example.com")
    private String email;

    @Schema(description = "Телефон", example = "+77071234567")
    private String phone;

    @Schema(description = "Пароль", example = "strongpassword")
    private String password;
    @Schema(description = "Пол", example = "Male")
    private String gender;
    @Schema(description = "Дата рождения", example = "2024-04-29")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    public String toString() {
        return "Registration info: username: " + this.email + " password: " + this.password;
    }
}