package ru.svetlov.webstore.api.dto;

import java.time.LocalDateTime;

public class ApiErrorDto {
    private String message;
    private LocalDateTime dateTime;

    public ApiErrorDto(String message) {
        this.message = message;
        this.dateTime = LocalDateTime.now();
    }

    public ApiErrorDto() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
