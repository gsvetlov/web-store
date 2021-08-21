package ru.svetlov.webstore.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ApiErrorDto {
    private String message;
    private LocalDateTime dateTime;

    public ApiErrorDto(String message) {
        this.message = message;
        this.dateTime = LocalDateTime.now();
    }
}
