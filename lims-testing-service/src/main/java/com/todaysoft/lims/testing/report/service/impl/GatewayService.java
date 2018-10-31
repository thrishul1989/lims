package com.todaysoft.lims.testing.report.service.impl;

public class GatewayService
{
    private static final String SERVICE_NAME = "lims-gateway-service";
    
    public static String getServiceUrl(String uri)
    {
        return "http://" + SERVICE_NAME + uri;
    }
}
