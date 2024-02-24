package com.example.ingressbookstore.exception;

import com.example.ingressbookstore.model.constant.ExceptionConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.ingressbookstore.model.enums.ExceptionMessage.UNAUTORIZE_USER;
import static com.example.ingressbookstore.model.enums.ExceptionMessage.UNEXPECTED_ERROR;
import static org.springframework.http.HttpStatus.*;
@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(Exception exception) {
        log.error("ActionLog.handle.error: ", exception);
        return new ErrorResponse(UNEXPECTED_ERROR.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handle(NotFoundException exception) {
        log.error("NotFoundException:", exception);
        return new ErrorResponse(exception.getMessage());
    }


    @ExceptionHandler(PasswordMismatchException.class)
    @ResponseStatus(UNAUTHORIZED)
    public ErrorResponse handle(PasswordMismatchException exception) {
        log.error("PasswordMismatchException:", exception);
        return new ErrorResponse(exception.getMessage());
    }


    @ExceptionHandler(UserUnautorizeException.class)
    @ResponseStatus(UNAUTHORIZED)
    public ErrorResponse handle(UserUnautorizeException exception){
        log.error("UserUnautorizeException:",exception);
        return new ErrorResponse(ExceptionConstants.USER_UNAUTHORIZED_MESSAGE);
    }

    @ExceptionHandler(UserForbiddenException.class)
    @ResponseStatus(FORBIDDEN)
    public ErrorResponse handle(UserForbiddenException exception){
        log.error("UserForbiddenException:",exception);
        return new ErrorResponse(ExceptionConstants.USER_UNAUTHENTICATED_MESSAGE);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }


}
