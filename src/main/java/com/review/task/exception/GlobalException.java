package com.review.task.exception;

import com.review.task.enums.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getAllErrors().forEach(error -> {
            errorMap.put(((FieldError) error).getField(), error.getDefaultMessage());
        });
        return errorMap;
    }

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleRecordNotFountException(RecordNotFoundException ex) {
        return new ErrorResponse(ErrorDetails.RECORD_NOT_FOUND.getMessage(), ErrorDetails.RECORD_NOT_FOUND.getCode().toString());
    }
}
