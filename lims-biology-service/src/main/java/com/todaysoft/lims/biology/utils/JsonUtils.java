package com.todaysoft.lims.biology.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils
{
    public static String asJson(Object data)
    {
        if (null == data)
        {
            return null;
        }
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_EMPTY);
        
        try
        {
            return mapper.writeValueAsString(data);
        }
        catch (Exception e)
        {
            return null;
        }
    }
    
    public static <T> T asObject(String json, Class<T> clasz)
    {
        if (null == json)
        {
            return null;
        }
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
        try
        {
            return mapper.readValue(json, clasz);
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
