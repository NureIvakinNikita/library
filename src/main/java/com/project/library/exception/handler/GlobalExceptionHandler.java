package com.project.library.exception.handler;


import com.project.library.exception.BookArgumentsViolationException;
import com.project.library.exception.BookNotFoundException;
import com.project.library.model.dto.ValidationExceptionResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleBookNotFoundException(BookNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage().toString(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookArgumentsViolationException.class)
    public ResponseEntity<String> handleBookArgumentsViolationException(BookArgumentsViolationException ex) {
        return new ResponseEntity<>(ex.getMessage().toString(), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               @NotNull HttpHeaders headers,
                                                               @NotNull HttpStatusCode status,
                                                               @NotNull WebRequest webRequest) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError -> FieldError.getField() + ": " + FieldError.getDefaultMessage())
                .toList();
        var exceptionBody = new ValidationExceptionResponse("validation_exceptions", errors.toString());
        return handleExceptionInternal(ex, exceptionBody, new HttpHeaders(), status, webRequest);
    }
}
