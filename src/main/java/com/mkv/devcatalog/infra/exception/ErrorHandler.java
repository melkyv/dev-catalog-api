package com.mkv.devcatalog.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> notFound() {
        return ResponseEntity.status(404).body("Register not found");
    }

    @ExceptionHandler(IntegrityError.class)
    public ResponseEntity<String> integrityError( IntegrityError e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(StandardError.class)
    public ResponseEntity<String> standardError( StandardError e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> error500(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getLocalizedMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorData>> validationError(MethodArgumentNotValidException e) {
        var errors = e.getFieldErrors();
        return ResponseEntity.unprocessableEntity().body(errors.stream().map(ValidationErrorData::new).toList());
    }
}
