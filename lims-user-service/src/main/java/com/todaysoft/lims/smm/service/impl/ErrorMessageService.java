package com.todaysoft.lims.smm.service.impl;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.todaysoft.lims.smm.service.IErrorMessageService;




@Service
public class ErrorMessageService implements IErrorMessageService
{
    private static Logger log = LoggerFactory.getLogger(ErrorMessageService.class);
    
    private static final int ERROR_CODE_LENGTH = 5;
    
    private static final String PREFIX_COMMON_ERROR = "C";
    
    private static final String PREFIX_SERVICE_ERROR = "S";
    
    @Autowired
    private MessageSource messageSource;
    
    @Override
    public String getMessage(String errorCode)
    {
        if (StringUtils.isEmpty(errorCode) || errorCode.length() != ERROR_CODE_LENGTH)
        {
            log.warn("Get message for error code {} failure, error code invalid", errorCode);
            return "";
        }
        
        String prefix = errorCode.substring(0, 1);
        
        if (PREFIX_COMMON_ERROR.equals(prefix))
        {
            String code = errorCode.substring(3, 5);
            String key = prefix + "00" + code;
            return getLocaleMessage(key);
        }
        else if (PREFIX_SERVICE_ERROR.equals(prefix))
        {
            return getLocaleMessage(errorCode);
        }
        else
        {
            log.warn("Get message for error code {} failure, error code invalid", errorCode);
            return "";
        }
    }
    
    private String getLocaleMessage(String key, Object... args)
    {
        try
        {
            return messageSource.getMessage(key, args, Locale.getDefault());
        }
        catch (NoSuchMessageException e)
        {
            return key;
        }
    }
}
