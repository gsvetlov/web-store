package ru.svetlov.webstore.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.svetlov.webstore.api.dto.ApiErrorDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ApiErrorDto> getResourceNotFoundResponse(ResourceNotFoundException e){
        return new ResponseEntity<>(new ApiErrorDto(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorDto> getBadRequestResponse(IllegalArgumentException e) {
        return new ResponseEntity<>(new ApiErrorDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorDto> getBadRequestResponse(BadRequestException e) {
        return new ResponseEntity<>(new ApiErrorDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
