package com.utn.TPfinal.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());

        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getHttpStatus());
    }

    @ExceptionHandler({FeeException.class})
    public ResponseEntity<Object> feeError(FeeException ex, WebRequest request) {

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "error");

        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getHttpStatus());
    }
    /*@ExceptionHandler({FeeException.class})
    public ResponseEntity<Object> feeError(FeeException ex, WebRequest request) {

        List<String> errors = new ArrayList<>();

        errors.add(ex.getMessage());

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);

        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getHttpStatus());
    }*/
    @ExceptionHandler({MeterException.class})
    public ResponseEntity<Object> meterError(FeeException ex, WebRequest request) {

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());

        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getHttpStatus());
    }

    @ExceptionHandler({MeterWithMeasurings.class})
    public ResponseEntity<Object> meterMeasuringError(MeterWithMeasurings ex, WebRequest request) {

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "error en meter, con measurings");

        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getHttpStatus());
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> BadRequestException(NotFoundException ex, WebRequest request) {

        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());

        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getHttpStatus());
    }

}
