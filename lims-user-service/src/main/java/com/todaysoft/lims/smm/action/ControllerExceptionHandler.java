package com.todaysoft.lims.smm.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.todaysoft.lims.exception.ServiceException;



@ControllerAdvice
public class ControllerExceptionHandler
{
    private static Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);
    
    /**
     * Controller中抛出异常时，统一封装成ServiceException返回
     */
    @ExceptionHandler
    public ResponseEntity<ServiceException> handle(Exception exception)
    {
        ServiceException ex;
        
        if (exception instanceof ServiceException)
        {
            ex = (ServiceException)exception;
        }
        else
        {
            log.error(exception.getMessage(), exception);
            ex = new ServiceException();
        }
        
        return new ResponseEntity<ServiceException>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
