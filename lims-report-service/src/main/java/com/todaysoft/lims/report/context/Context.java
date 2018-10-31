package com.todaysoft.lims.report.context;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component(value = "systemContext")
@Lazy(value = false)
public class Context {
	@Autowired
    private MessageSource messageSource;
	
	public String getMessage(String key, Object... objects)
    {
        try
        {
            return messageSource.getMessage(key, objects, Locale.getDefault());
        }
        catch (NoSuchMessageException e)
        {
            //log.warn("Cannot found message for key: {}", key);
            return key;
        }
    }
}
