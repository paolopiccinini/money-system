package com.example.moneysystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    private final Logger logger = LoggerFactory.getLogger(CalculatorController.class);
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .toList();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HandlerMethodValidationException.class)
    public List<String> handleValidationExceptions(HandlerMethodValidationException ex) {
        return ex.getAllValidationResults()
                .stream()
                .flatMap(vr -> vr.getResolvableErrors().stream())
                .map(MessageSourceResolvable::getDefaultMessage)
                .toList();
    }

    // Not able to reproduce spring standard error response
    // see https://stackoverflow.com/questions/78468266/spring-boot-3-2-5-when-in-restcontrolleradvice-jakarta-servlet-error-status-cod
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public Map<String, Object> handleMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException ex, WebRequest request) {
        DefaultErrorAttributes error = new DefaultErrorAttributes();
        return error.getErrorAttributes(request, ErrorAttributeOptions.defaults());
    }

    // Not able to reproduce spring standard error response
    // see https://stackoverflow.com/questions/78468266/spring-boot-3-2-5-when-in-restcontrolleradvice-jakarta-servlet-error-status-cod
    // I wanted to log exceptions but I had to catch HttpMediaTypeNotAcceptableException
    // otherwise 500 will be returned instead of 415
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Map<String, Object> handleException(Exception ex, WebRequest request) {
        logger.error("Exception: ", ex);
        DefaultErrorAttributes error = new DefaultErrorAttributes();
        return error.getErrorAttributes(request, ErrorAttributeOptions.defaults());
    }


}
