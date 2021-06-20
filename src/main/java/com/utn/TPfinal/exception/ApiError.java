package com.utn.TPfinal.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor
public class ApiError {

    private HttpStatus httpStatus;
    private String message;
<<<<<<< HEAD
    private List<String> errors;
=======
 //   private List<String> errors;
>>>>>>> cc95102e93cc1f0ad658d842e02e7ac372265144

}
