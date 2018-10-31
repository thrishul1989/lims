package com.todaysoft.lims.system.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpMessageConverterExtractor;

import com.todaysoft.lims.exception.ServiceException;

public class RestfulResponseErrorHandler extends DefaultResponseErrorHandler
{
    private Logger logger = Logger.getLogger(RestfulResponseErrorHandler.class);
    
    private List<HttpMessageConverter<?>> converters;
    
    public RestfulResponseErrorHandler(List<HttpMessageConverter<?>> converters)
    {
        this.converters = converters;
    }
    
    @Override
    public void handleError(ClientHttpResponse response) throws IOException
    {
        try
        {
            HttpStatus status = response.getStatusCode();
            
            switch (status.series())
            {
                case CLIENT_ERROR:
                    logger.error("Client error, status " + status + ":" + response.getStatusText());
                    throw ServiceException.innerError();
                case SERVER_ERROR:
                    logger.error("Server error, status " + status + ":" + response.getStatusText());
                    ServiceException e = new HttpMessageConverterExtractor<ServiceException>(ServiceException.class, this.converters).extractData(response);
                    throw e;
                default:
                    logger.error("Error, status " + status + ":" + response.getStatusText());
                    throw ServiceException.innerError();
            }
        }
        catch (ServiceException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw ServiceException.innerError();
        }
    }
}
