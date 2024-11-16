package com.demo.codigo_booking_system.exception;

import com.demo.codigo_booking_system.exception.custom.NotEligibleException;
import com.demo.codigo_booking_system.exception.custom.UserNotFoundException;
import com.demo.codigo_booking_system.exception.dto.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<HttpResponse> userNotFoundException(UserNotFoundException userNotFoundException){
        return createInitialHttpResponse(HttpStatus.BAD_REQUEST, "User was not found".toUpperCase());
    }

    @ExceptionHandler(NotEligibleException.class)
    public ResponseEntity<HttpResponse> notEligibleException(NotEligibleException notEligibleException){
        return createInitialHttpResponse(HttpStatus.BAD_REQUEST, "Not eligible to purchase this package".toUpperCase());
    }

    private ResponseEntity<HttpResponse> createInitialHttpResponse(HttpStatus httpStatus, String message){
        HttpResponse httpResponse = new HttpResponse(LocalDateTime.now(), httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase().toUpperCase(), message);
        return new ResponseEntity<>(httpResponse, httpStatus);
    }

}
