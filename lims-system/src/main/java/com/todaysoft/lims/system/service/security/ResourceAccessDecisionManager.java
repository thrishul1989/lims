package com.todaysoft.lims.system.service.security;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.system.service.IResourceService;

@Component
public class ResourceAccessDecisionManager implements AccessDecisionManager
{
    @Autowired
    private IResourceService service;
    
    @Override
    public void decide(Authentication auth, Object object, Collection<ConfigAttribute> attributes) throws AccessDeniedException, InsufficientAuthenticationException
    {
        if (auth instanceof AnonymousAuthenticationToken)
        {
            throw new InsufficientAuthenticationException("Anonymous Denied");
        }
        
        String uri = ((FilterInvocation)object).getRequest().getRequestURI();
        
        if (service.isGrantedResource(uri))
        {
            return;
        }
        else
        {
            
            throw new AccessDeniedException("You are not allowed to visit this url");
            
        }
    }
    
    @Override
    public boolean supports(ConfigAttribute attribute)
    {
        return true;
    }
    
    @Override
    public boolean supports(Class<?> clazz)
    {
        return true;
    }
}