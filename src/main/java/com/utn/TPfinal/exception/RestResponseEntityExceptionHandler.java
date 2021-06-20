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

        List<String> errors = new ArrayList<>();

        for (ConstraintViolation violation : ex.getConstraintViolations()) {
<<<<<<< HEAD
            errors.add(violation.getRootBeanClass().getName() + " " + violation.getMessage());
        }
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),errors);
=======
        errors.add(violation.getRootBeanClass().getName() + " " + violation.getMessage());
        }
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
>>>>>>> cc95102e93cc1f0ad658d842e02e7ac372265144

        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getHttpStatus());
        }


    @ExceptionHandler({FeeException.class})
    public ResponseEntity<Object> feeError(FeeException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();


<<<<<<< HEAD
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "error", errors);
=======
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
>>>>>>> cc95102e93cc1f0ad658d842e02e7ac372265144

        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getHttpStatus());
    }

    @ExceptionHandler({MeterException.class})
    public ResponseEntity<Object> meterError(FeeException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();


        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);

        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getHttpStatus());
    }

<<<<<<< HEAD
    @ExceptionHandler({MeterWithMeasurings.class})
    public ResponseEntity<Object> meterMeasuringError(MeterWithMeasurings ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
=======
    @ExceptionHandler({MeterWithMeasuringsException.class})
    public ResponseEntity<Object> meterMeasuringError(MeterWithMeasuringsException ex, WebRequest request) {
>>>>>>> cc95102e93cc1f0ad658d842e02e7ac372265144

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "error en meter, con measurings", errors);

        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getHttpStatus());
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> BadRequestException(NotFoundException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();

        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), errors);

        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getHttpStatus());
    }

}
