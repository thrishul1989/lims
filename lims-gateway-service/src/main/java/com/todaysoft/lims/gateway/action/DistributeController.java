package com.todaysoft.lims.gateway.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.gateway.service.IDistributeService;
import com.todaysoft.lims.gateway.service.impl.UnsupportedDistributeUrlException;

@RestController
public class DistributeController
{
    @Autowired
    private IDistributeService service;
    
    @RequestMapping(value = "/**/*", method = RequestMethod.GET)
    public ResponseEntity<?> get(HttpServletRequest request)
    {
        try
        {
            String rsp = service.distributeGetRequest(request);
            return ResponseEntity.ok(rsp);
        }
        catch (UnsupportedDistributeUrlException e)
        {
            return ResponseEntity.notFound().build();
        }
    }
    
    @RequestMapping(value = "/**/*", method = RequestMethod.POST)
    public ResponseEntity<?> post(HttpServletRequest request, @RequestBody(required = false) String body)
    {
        try
        {
            String rsp = service.distributePostRequest(request, body);
            return ResponseEntity.ok(rsp);
        }
        catch (UnsupportedDistributeUrlException e)
        {
            return ResponseEntity.notFound().build();
        }
    }
    
    @RequestMapping(value = "/**/*", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(HttpServletRequest request)
    {
        try
        {
            service.distributeDeleteRequest(request);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (UnsupportedDistributeUrlException e)
        {
            return ResponseEntity.notFound().build();
        }
    }
}
