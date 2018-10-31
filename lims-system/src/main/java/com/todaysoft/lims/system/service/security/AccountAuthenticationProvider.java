package com.todaysoft.lims.system.service.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.todaysoft.lims.exception.ServiceException;
import com.todaysoft.lims.persist.DataAuthoritySearcher;
import com.todaysoft.lims.system.model.vo.Department;
import com.todaysoft.lims.system.model.vo.UserAuthorizedDetails;
import com.todaysoft.lims.system.modules.smm.model.DataAuthority;
import com.todaysoft.lims.system.modules.smm.model.DataAuthorityRole;
import com.todaysoft.lims.system.modules.smm.model.DataAuthorityUser;
import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;
import com.todaysoft.lims.system.modules.smm.service.IDataAuthorityService;
import com.todaysoft.lims.system.service.IDepartmentService;
import com.todaysoft.lims.system.service.IUserService;
import com.todaysoft.lims.system.service.adapter.request.LoginRequest;
import com.todaysoft.lims.system.service.impl.ErrorCode;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;
import com.todaysoft.lims.system.util.Base64;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Component
public class AccountAuthenticationProvider extends RestService implements AuthenticationProvider
{
    
    private static Logger log = LoggerFactory.getLogger(AccountAuthenticationProvider.class);
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IDataAuthorityService dataAuthorityService;
    
    @Autowired
    private IDepartmentService departmentService;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        if (null == authentication.getPrincipal())
        {
            throw new BadCredentialsException("用户名或者密码错误");
        }
        
        if (null == authentication.getCredentials())
        {
            throw new BadCredentialsException("用户名或者密码错误");
        }
        
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
        {
            throw new BadCredentialsException("用户名或者密码错误");
        }
        
        AccountDetails details = login(username, password);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(details, authentication, details.getAuthorities());
        token.setDetails(authentication.getDetails());
        
        try
        {
            initDataAuthority(details);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new BadCredentialsException("数据权限设置错误，请联系管理员");
        }
        
        return token;
    }
    
    protected AccountDetails login(String username, String password)
    {
        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        
        request.setPassword(Base64.getBase64(password));
        
        String url = GatewayService.getServiceUrl("/smm/authorize/login");
        
        try
        {
            ResponseEntity<?> exchange =
                template.exchange(url, HttpMethod.POST, new HttpEntity<LoginRequest>(request), new ParameterizedTypeReference<UserAuthorizedDetails>()
                {
                });
            
            UserAuthorizedDetails details = (UserAuthorizedDetails)exchange.getBody();
            if (ErrorCode.LOGIN_ACCOUNT_DISABLED.equals(details.getErroCode()))
            {
                throw new ServiceException("用户已被禁用，请联系管理员！");
            }
            else if (ErrorCode.LOGIN_BAD_CREDENTIALS.equals(details.getErroCode()))
            {
                throw new ServiceException("用户名或者密码错误！");
            }
            
            AccountDetails account = new AccountDetails(details.getName(), details.getUsername(), details.getToken(), details.getUserId());
            account.setRecordFilters(details.getRecordFilters());
            
            if (!CollectionUtils.isEmpty(details.getAuthorities()))
            {
                GrantedAuthority authority;
                Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
                
                for (String key : details.getAuthorities())
                {
                    authority = new SimpleGrantedAuthority(key);
                    authorities.add(authority);
                }
                
                account.setAuthorities(authorities);
            }
            
            return account;
        }
        catch (ServiceException e)
        {
            
            throw new BadCredentialsException(e.getErrorCode());
        }
        catch (Exception e)
        {
            
            throw new BadCredentialsException("登录服务异常，请稍后重试");
        }
    }
    
    @Override
    public boolean supports(Class<?> authentication)
    {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
    
    public void initDataAuthority(AccountDetails account)
    {
        if (null == account.getDataAuthoritySearcher())
        {
            UserDetailsModel userModel = userService.getUserByID(account.getUserId());
            //查询改用户所在机构及下属机构
            Set<String> deptIds = new HashSet<String>();
            if (StringUtils.isNotEmpty(userModel.getArchive().getDeptId()))
            {
                Department department = departmentService.get(userModel.getArchive().getDeptId());
                if (null != department)
                {
                    deptIds.add(department.getId());
                    filterDepartments(department.getNodes(), deptIds);
                }
            }
            
            Map<String, List<DataAuthoritySearcher>> dataAuthorityMap = new HashMap<String, List<DataAuthoritySearcher>>();
            List<DataAuthority> list = dataAuthorityService.list();
            for (DataAuthority da : list)
            {
                List<DataAuthoritySearcher> searcherList = new ArrayList<DataAuthoritySearcher>();
                
                //查询用户数据权限
                DataAuthorityUser userSearcher = new DataAuthorityUser();
                userSearcher.setUserId(account.getUserId());
                DataAuthority mm = new DataAuthority();
                mm.setResourceKey(da.getResourceKey());
                userSearcher.setResourceKey(mm);
                List<DataAuthorityUser> userAuthorityList = dataAuthorityService.dataAuthorityUserByUserId(userSearcher);
                if (null != userAuthorityList && Collections3.isNotEmpty(userAuthorityList))
                {
                    DataAuthoritySearcher dataSearcher = new DataAuthoritySearcher();
                    dataSearcher.setConfig(userAuthorityList.get(0).getConfig());
                    if (StringUtils.isNotEmpty(userAuthorityList.get(0).getSpecialDepts()))
                    {
                        Set<String> depts = new HashSet<String>();
                        for (String dep : userAuthorityList.get(0).getSpecialDepts().split("\\,"))
                        {
                            depts.add(dep);
                        }
                        dataSearcher.setDepts(depts);
                    }
                    
                    Set<String> archives = new HashSet<String>();
                    List<String> leaderIds = Lists.newArrayList();
                    leaderIds.add(account.getUserId());
                    getArchives(leaderIds, archives);
                    archives.add(account.getUserId());
                    
                    dataSearcher.setUserAndSons(archives);
                    dataSearcher.setUserId(account.getUserId());
                    dataSearcher.setDeptAndSons(deptIds);
                    searcherList.add(dataSearcher);
                    
                }
                
                //查询用户的角色数据权限
                List<String> roles = userModel.getRoles();
                for (String role : roles)
                {
                    DataAuthorityRole roleSearcher = new DataAuthorityRole();
                    roleSearcher.setRoleId(role);
                    DataAuthority ff = new DataAuthority();
                    ff.setResourceKey(da.getResourceKey());
                    roleSearcher.setResourceKey(ff);
                    List<DataAuthorityRole> roleAuthorityList = dataAuthorityService.dataAuthorityRoleByRoleId(roleSearcher);
                    if (null != roleAuthorityList && Collections3.isNotEmpty(roleAuthorityList))
                    {
                        DataAuthoritySearcher dataSearcher2 = new DataAuthoritySearcher();
                        dataSearcher2.setConfig(roleAuthorityList.get(0).getConfig());
                        if (StringUtils.isNotEmpty(roleAuthorityList.get(0).getSpecialDepts()))
                        {
                            Set<String> depts = new HashSet<String>();
                            for (String dep : roleAuthorityList.get(0).getSpecialDepts().split("\\,"))
                            {
                                depts.add(dep);
                            }
                            dataSearcher2.setDepts(depts);
                        }
                        
                        Set<String> archives = new HashSet<String>();
                        List<String> leaderIds = Lists.newArrayList();
                        leaderIds.add(account.getUserId());
                        getArchives(leaderIds, archives);
                        archives.add(account.getUserId());
                        
                        dataSearcher2.setUserAndSons(archives);
                        dataSearcher2.setUserId(account.getUserId());
                        dataSearcher2.setDeptAndSons(deptIds);
                        searcherList.add(dataSearcher2);
                        
                    }
                }
                
                dataAuthorityMap.put(da.getResourceKey(), searcherList);
            }
            
            account.setDataAuthoritySearcher(dataAuthorityMap);
            
        }
        
    }
    
    void filterDepartments(List<Department> dep, Set<String> list)
    {
        if (null != dep && Collections3.isNotEmpty(dep))
        {
            Iterator<Department> it = dep.iterator();
            
            while (it.hasNext())
            {
                Department department = it.next();
                if (Collections3.isNotEmpty(department.getNodes()))
                {
                    filterDepartments(department.getNodes(), list);
                }
                list.add(department.getId());
                
            }
        }
        
    }
    
    private void getArchives(List<String> leaderIds, Set<String> archives)
    {
        StringBuffer leaders = new StringBuffer(256);
        if (Collections3.isNotEmpty(leaderIds))
        {
            for (String id : leaderIds)
            {
                if (StringUtils.isEmpty(leaders))
                {
                    leaders.append(id);
                }
                else
                {
                    leaders.append(",").append(id);
                }
            }
        }
        List<String> userIds = dataAuthorityService.getSubordinates(leaders.toString());
        if (Collections3.isNotEmpty(userIds))
        {
            leaderIds.clear();
            leaderIds.addAll(userIds);
            archives.addAll(userIds);
            getArchives(leaderIds, archives);
        }
    }
}
