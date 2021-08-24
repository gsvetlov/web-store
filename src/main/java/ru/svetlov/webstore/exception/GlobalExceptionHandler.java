package ru.svetlov.webstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.svetlov.webstore.dto.ApiErrorDto;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorDto> getResourceNotFoundResponse(ResourceNotFoundException e){
        return new ResponseEntity<>(new ApiErrorDto(e.getMessage()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({IllegalArgumentException.class, BadRequestException.class})
    public ResponseEntity<ApiErrorDto> getBadRequestResponse(IllegalArgumentException e) {
        return new ResponseEntity<>(new ApiErrorDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
