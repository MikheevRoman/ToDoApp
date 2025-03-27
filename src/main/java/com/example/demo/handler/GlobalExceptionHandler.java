package com.example.demo.handler;

import com.example.demo.dto.ErrorResponse;
import com.example.demo.exception.IllegalSortingOrderException;
import com.example.demo.exception.MappingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalSortingOrderException(IllegalSortingOrderException e) {
        log.warn("incorrect sorting order parameter");
        return new ErrorResponse("incorrect sorting order");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMappingException(MappingException e) {
        log.warn("cannot map dto: {}", e.getMessage());
        return new ErrorResponse("incorrect request body");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEntityNotFoundException(EntityNotFoundException e) {
        log.warn("no such entity: {}", e.getMessage());
        return new ErrorResponse("entity not found");
    }

}
