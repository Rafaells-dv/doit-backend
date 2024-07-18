package com.project.doit.infra;


import com.project.doit.exception.EmptyFieldException;
import com.project.doit.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmptyFieldException.class)
    private ResponseEntity<RestErrorMessage> emptyFieldHandler(EmptyFieldException ex) {
        RestErrorMessage threatResponse = new RestErrorMessage(ex.getMessage(), HttpStatus.BAD_REQUEST, "Preencha todos os campos!");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<RestErrorMessage> notFoundHandler(NotFoundException ex) {
        RestErrorMessage threatResponse = new RestErrorMessage(ex.getMessage(), HttpStatus.NOT_FOUND, "");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }
}
