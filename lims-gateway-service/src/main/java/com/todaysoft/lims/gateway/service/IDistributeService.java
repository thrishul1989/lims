package com.todaysoft.lims.gateway.service;

import javax.servlet.http.HttpServletRequest;

import com.todaysoft.lims.gateway.service.impl.UnsupportedDistributeUrlException;

public interface IDistributeService
{
    String distributeGetRequest(HttpServletRequest request)
        throws UnsupportedDistributeUrlException;
    
    String distributePostRequest(HttpServletRequest request, String body)
        throws UnsupportedDistributeUrlException;
    
    void distributeDeleteRequest(HttpServletRequest request)
        throws UnsupportedDistributeUrlException;
}
