package com.employee.exception;

import com.employee.dtos.ApiResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    private Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> resouceNotFoundExceptionHandler(ResourceNotFoundException ex)
    {
        logger.info("Custom Exception Handler invoked!");
        ApiResponseMessage message=ApiResponseMessage
                .builder().message(ex.getMessage()).success(true).status(HttpStatus.NOT_FOUND).build();

        return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseMessage> runTimeExceptionHandler(Exception ex)
    {
        logger.info("Exception Handler invoked!");
        ApiResponseMessage message=ApiResponseMessage
                .builder().message(ex.getMessage()).success(true).status(HttpStatus.NOT_FOUND).build();

        return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
    {
        logger.warn("Method Argument Exception Invoked!");

        Map<String,Object> response=new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName=((FieldError)error).getField();
            String errorMessage=error.getDefaultMessage();
            response.put(fieldName,errorMessage);
        });
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

}
