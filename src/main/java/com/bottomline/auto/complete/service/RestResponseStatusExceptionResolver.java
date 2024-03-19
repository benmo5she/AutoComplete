package com.bottomline.auto.complete.service;


import java.util.Map;
import javax.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class RestResponseStatusExceptionResolver {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    protected ResponseEntity<Map<String, String>> handleValidationException(ValidationException ex, WebRequest request) {
        Map<String, String> errorResponse = Map.of("Invalid request", ex.getMessage());
        logger.info("Invalid arguments received for request: " + request.getDescription(false));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    protected ResponseEntity<Map<String, String>> handleAllExceptions(Exception ex, WebRequest request) {
        Map<String, String> errorResponse = Map.of("Error", "An unexpected error occurred, contact support for further details");
        logger.error("Internal error occurred during processing of request for : " + request.getDescription(false), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }


}
