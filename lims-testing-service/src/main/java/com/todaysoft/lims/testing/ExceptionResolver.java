package com.todaysoft.lims.testing;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.todaysoft.lims.exception.ServiceException;

@EnableWebMvc
@ControllerAdvice
public class ExceptionResolver
{
    private Logger logger = Logger.getLogger(ExceptionResolver.class);
    
    @ExceptionHandler
    public ResponseEntity<?> handle(Exception exception)
    {
        ServiceException ex;
        
        if (exception instanceof ServiceException)
        {
            ex = (ServiceException)exception;
            logger.error(String.format("Service exception, error code %1$s, error message %2$s", ex.getErrorCode(), ex.getErrorMessage()));
        }
        else
        {
            ex = ServiceException.innerError();
            logger.error(exception.getMessage(), exception);
        }
        
        return new ResponseEntity<ServiceException>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
