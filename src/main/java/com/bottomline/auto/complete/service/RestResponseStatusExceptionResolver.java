package com.bottomline.auto.complete.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestResponseStatusExceptionResolver extends ResponseEntityExceptionHandler {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

    /*
     * Exception handler for REST controllers defined in the application.
     * Should an validation exception be thrown from one of the controllers, this method will be invoked
     * with the URI and the exception causing the error
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, String> handleConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        
        Map<String, String> fieldException = new HashMap<>();
        constraintViolations.stream().forEach(e -> {
        	fieldException.put("Status", HttpStatus.BAD_REQUEST.toString() + " - " + HttpStatus.BAD_REQUEST.getReasonPhrase());
            fieldException.put("Message", e.getMessage());
            String argVal = Objects.requireNonNullElse(e.getInvalidValue(), "").toString(); 
            if(argVal != null) {
            	fieldException.put("Value", e.getInvalidValue().toString());            	
            }
            logger.info(e.getMessage() + " for value: " + argVal);
            logger.error("Error for request param", ex);
        });
        return fieldException;
    }
    

}