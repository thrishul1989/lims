package com.todaysoft.lims.report.ons;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtils
{
    public static String toJson(Object object)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(object);
        }
        catch (JsonProcessingException e)
        {
            return null;
        }
    }
    
    public static <T> T fromJson(String json, Class<T> type)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, type);
        }
        catch (JsonProcessingException e)
        {
            return null;
        }
        catch (IOException e)
        {
            return null;
        }
    }
}
