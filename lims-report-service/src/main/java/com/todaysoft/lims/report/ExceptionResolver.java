package com.todaysoft.lims.report;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@ControllerAdvice
public class ExceptionResolver
{
    private Logger logger = Logger.getLogger(ExceptionResolver.class);
    
    @ExceptionHandler(value = Exception.class)
    public void defaultErrorHandler(HttpServletRequest req, Exception e)
    {
        e.printStackTrace();
        logger.error("/************************\n*************\nproduct全局异常**************\n**************\n", e);
    }
}
