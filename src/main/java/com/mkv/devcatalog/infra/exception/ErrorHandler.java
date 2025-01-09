package com.mkv.devcatalog.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
