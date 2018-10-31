package com.todaysoft.lims.smm.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.base.RecordFilter;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.smm.entity.User;
import com.todaysoft.lims.smm.entity.UserToken;
import com.todaysoft.lims.smm.entity.enums.UserState;
import com.todaysoft.lims.smm.request.LoginRequest;
import com.todaysoft.lims.smm.response.RoleDetailsModel;
import com.todaysoft.lims.smm.response.UserAuthorizedDetails;
import com.todaysoft.lims.smm.response.UserDetailsModel;
import com.todaysoft.lims.smm.response.UserMinimalModel;
import com.todaysoft.lims.smm.service.IAuthorizeService;
import com.todaysoft.lims.smm.service.IDataAuthorityService;
import com.todaysoft.lims.smm.service.IRoleService;
import com.todaysoft.lims.smm.service.IUserService;
import com.todaysoft.lims.smm.utils.AuthenticateUtil;
import com.todaysoft.lims.utils.Collections3;

@Service
public class AuthorizeService implements IAuthorizeService
{
    
    private static Logger log = LoggerFactory.getLogger(AuthorizeService.class);
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private IRoleService roleService;
    
    @Autowired
    private IDataAuthorityService dataAuthorityService;
    
    @Override
    @Transactional
    public ResponseEntity<?> login(LoginRequest request)
        throws NoSuchAlgorithmException
    {
        User user = userService.getUserByUsername(request.getUsername());
        
        if (null == user)
        {
            UserAuthorizedDetails response = new UserAuthorizedDetails();
            response.setErroCode(ErrorCode.LOGIN_BAD_CREDENTIALS);
            return new ResponseEntity<UserAuthorizedDetails>(response, HttpStatus.OK);
        }
        
        if (!user.getPassword().equals(AuthenticateUtil.salt(request.getPassword(), user.getSalt())))
        {
            UserAuthorizedDetails response = new UserAuthorizedDetails();
            response.setErroCode(ErrorCode.LOGIN_BAD_CREDENTIALS);
            return new ResponseEntity<UserAuthorizedDetails>(response, HttpStatus.OK);
        }
        
        if (UserState.DISABLED.equals(user.getState()))
        {
            log.warn(ErrorCode.LOGIN_ACCOUNT_DISABLED);
            UserAuthorizedDetails response = new UserAuthorizedDetails();
            response.setErroCode(ErrorCode.LOGIN_ACCOUNT_DISABLED);
            return new ResponseEntity<UserAuthorizedDetails>(response, HttpStatus.OK);
        }
        
        if (UserState.LOCKED.equals(user.getState()))
        {
            UserAuthorizedDetails response = new UserAuthorizedDetails();
            response.setErroCode(ErrorCode.LOGIN_ACCOUNT_LOCKED);
            return new ResponseEntity<UserAuthorizedDetails>(response, HttpStatus.OK);
        }
        
        Date timestamp = new Date();
        UserToken userToken = baseDaoSupport.get(UserToken.class, user.getId());
        
        String token;
        
        if (null == userToken)
        {
            token = generateToken();
            userToken = new UserToken();
            userToken.setUserId(user.getId());
            userToken.setToken(token);
            userToken.setCreateTime(timestamp);
            userToken.setExpireTime(DateUtils.addDays(timestamp, 7));
            baseDaoSupport.insert(userToken);
        }
        else
        {
            Date expireTime = userToken.getExpireTime();
            
            if (timestamp.before(expireTime))
            {
                token = userToken.getToken();
            }
            else
            {
                token = generateToken();
                userToken.setToken(token);
            }
            
            userToken.setCreateTime(timestamp);
            userToken.setExpireTime(DateUtils.addDays(timestamp, 7));
            baseDaoSupport.update(userToken);
        }
        
        UserAuthorizedDetails details = new UserAuthorizedDetails();
        details.setToken(token);
        details.setUsername(user.getUsername());
        details.setName(user.getArchive().getName());
        details.setUserId(user.getId());
        
        // TODO:获取用户权限集合
        //获取用户角色
        UserDetailsModel userModel = userService.getUserByID(user.getId());
        if (Collections3.isNotEmpty(userModel.getRoles()))
        {
            List<String> roles = userModel.getRoles();
            //查询每个角色的权限\
            Set<String> auSet = new HashSet<String>();
            for (String role : roles)
            {
                RoleDetailsModel roleModel = roleService.get(role);
                if (null != roleModel.getAuthorities() && Collections3.isNotEmpty(roleModel.getAuthorities()))
                {
                    for (String authority : roleModel.getAuthorities())
                    {
                        auSet.add(authority);
                        
                    }
                }
                
            }
            
            details.setAuthorities(auSet);
        }
        
        // 数据权限
        Map<String, RecordFilter> filters = dataAuthorityService.getRecordFilters(user.getId());
        details.setRecordFilters(filters);
        return new ResponseEntity<UserAuthorizedDetails>(details, HttpStatus.OK);
    }
    
    @Override
    public UserMinimalModel getAuthorizedUser(String token)
    {
        return userService.getByToken(token);
    }
    
    private String generateToken()
    {
        return RandomStringUtils.randomAlphabetic(32);
    }
    
}
