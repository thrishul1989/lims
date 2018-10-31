package com.todaysoft.lims.system.service.security;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.base.RecordFilter;
import com.todaysoft.lims.persist.DataAuthoritySearcher;

public class AccountDetails implements UserDetails
{
    private static final long serialVersionUID = -3387979682204989700L;
    
    private String userId;
    
    private String name;
    
    private String username;
    
    private String token;
    
    private Set<GrantedAuthority> authorities;
    
    private Map<String, List<DataAuthoritySearcher>> dataAuthoritySearcher;//用户数据权限
    
    private Map<String, RecordFilter> recordFilters;
    
    public AccountDetails(String name, String username, String token, String userId)
    {
        this.name = name;
        this.username = username;
        this.token = token;
        this.userId = userId;
    }
    
    public RecordFilter getRecordFilter(String category)
    {
        if (CollectionUtils.isEmpty(recordFilters))
        {
            return null;
        }
        
        return recordFilters.get(category);
    }
    
    public String getUserId()
    {
        return userId;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getToken()
    {
        return token;
    }
    
    @Override
    public String getUsername()
    {
        return username;
    }
    
    @Override
    public String getPassword()
    {
        return null;
    }
    
    @Override
    public boolean isEnabled()
    {
        return true;
    }
    
    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }
    
    public Set<String> getSimpleAuthorities()
    {
        if (CollectionUtils.isEmpty(authorities))
        {
            return Collections.emptySet();
        }
        
        Set<String> values = new HashSet<String>();
        
        for (GrantedAuthority authority : authorities)
        {
            values.add(authority.getAuthority());
        }
        
        return values;
    }
    
    @Override
    public Set<GrantedAuthority> getAuthorities()
    {
        return authorities;
    }
    
    public void setAuthorities(Set<GrantedAuthority> authorities)
    {
        this.authorities = authorities;
    }
    
    public Map<String, List<DataAuthoritySearcher>> getDataAuthoritySearcher()
    {
        return dataAuthoritySearcher;
    }
    
    public void setDataAuthoritySearcher(Map<String, List<DataAuthoritySearcher>> dataAuthoritySearcher)
    {
        this.dataAuthoritySearcher = dataAuthoritySearcher;
    }
    
    public Map<String, RecordFilter> getRecordFilters()
    {
        return recordFilters;
    }
    
    public void setRecordFilters(Map<String, RecordFilter> recordFilters)
    {
        this.recordFilters = recordFilters;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((token == null) ? 0 : token.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AccountDetails other = (AccountDetails)obj;

        if (name == null)
        {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        if (token == null)
        {
            if (other.token != null)
                return false;
        }
        else if (!token.equals(other.token))
            return false;
        if (userId == null)
        {
            if (other.userId != null)
                return false;
        }
        else if (!userId.equals(other.userId))
            return false;
        if (username == null)
        {
            if (other.username != null)
                return false;
        }
        else if (!username.equals(other.username))
            return false;
        return true;
    }
}
