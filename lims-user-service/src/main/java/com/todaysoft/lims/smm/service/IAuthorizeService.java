package com.todaysoft.lims.smm.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.http.ResponseEntity;

import com.todaysoft.lims.smm.request.LoginRequest;
import com.todaysoft.lims.smm.response.UserMinimalModel;
import com.todaysoft.lims.smm.response.UserAuthorizedDetails;

public interface IAuthorizeService
{
    ResponseEntity<?>  login(LoginRequest request) throws NoSuchAlgorithmException;
    
    UserMinimalModel getAuthorizedUser(String token);
}
