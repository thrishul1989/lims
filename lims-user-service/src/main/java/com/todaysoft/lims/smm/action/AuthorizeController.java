package com.todaysoft.lims.smm.action;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.smm.request.LoginRequest;
import com.todaysoft.lims.smm.response.UserMinimalModel;
import com.todaysoft.lims.smm.response.UserAuthorizedDetails;
import com.todaysoft.lims.smm.service.IAuthorizeService;

@RestController
@RequestMapping("/smm/authorize")
public class AuthorizeController
{
    @Autowired
    private IAuthorizeService service;
    
    @RequestMapping("/login")
    public ResponseEntity<?>  login(@RequestBody LoginRequest request) throws NoSuchAlgorithmException
    {
        return service.login(request);
    }
    
    @RequestMapping("/user")
    public UserMinimalModel getAuthorizedUser(@RequestHeader(required = false, value = "member-token") String token)
    {
        return service.getAuthorizedUser(token);
    }
}
