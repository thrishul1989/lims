package com.todaysoft.lims.sample.service.adapter;

import com.todaysoft.lims.sample.service.impl.ErrorCode;
import com.todaysoft.lims.sample.service.impl.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;

public abstract class AbstractAdapter implements MicroserviceAdapter
{
    @Autowired
    protected RestTemplate template;
    
    @PostConstruct
    protected void setRestTemplateErrorHandler()
    {
        template.setErrorHandler(new ServiceResponseErrorHandler());
    }
    
    protected String getServiceUrl(String uri)
    {
        if (null == getName())
        {
            throw new IllegalStateException();
        }
        
        return "http://" + getName() + uri;
    }
    
    public void fallback()
    {
        throw new RuntimeException("TODO:FALLBACK");
    }
    
    private class ServiceResponseErrorHandler extends DefaultResponseErrorHandler
    {
        @Override
        public void handleError(ClientHttpResponse response)
            throws IOException
        {
            try
            {
                HttpStatus status = response.getStatusCode();
                
                switch (status.series())
                {
                    case CLIENT_ERROR:
                        throw new ServiceException(ErrorCode.UNDEFINED_ERROR, String.valueOf(status));
                    case SERVER_ERROR:
                        ServiceException e =
                            new HttpMessageConverterExtractor<ServiceException>(ServiceException.class, template.getMessageConverters()).extractData(response);
                        throw e;
                    default:
                        throw new ServiceException(ErrorCode.UNDEFINED_ERROR, String.valueOf(status));
                }
            }
            catch (ServiceException e)
            {
                throw e;
            }
            catch (Exception e)
            {
                throw new ServiceException(ErrorCode.UNDEFINED_ERROR, e.getMessage(), e);
            }
        }
    }
}
