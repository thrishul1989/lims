package com.todaysoft.lims.smm.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.smm.entity.User;
import com.todaysoft.lims.smm.response.UserSimpleModel;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.IEntityWrapper;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.smm.dao.searcher.RoleSearcher;
import com.todaysoft.lims.smm.entity.Authority;
import com.todaysoft.lims.smm.entity.Role;
import com.todaysoft.lims.smm.entity.RoleTemplate;
import com.todaysoft.lims.smm.request.RoleCreateRequest;
import com.todaysoft.lims.smm.request.RoleListRequest;
import com.todaysoft.lims.smm.request.RoleModifyRequest;
import com.todaysoft.lims.smm.response.RoleDetailsModel;
import com.todaysoft.lims.smm.response.RoleSimpleModel;
import com.todaysoft.lims.smm.service.IRoleService;

@Service
public class RoleService implements IRoleService, IEntityWrapper<Role, RoleSimpleModel>
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<RoleSimpleModel> paging(RoleListRequest request)
    {
        RoleSearcher searcher = new RoleSearcher();
        BeanUtils.copyProperties(request, searcher);
        return baseDaoSupport.find(searcher.toQuery(), request.getPageNo(), request.getPageSize(), this);
    }
    
    @Override
    public List<RoleSimpleModel> list()
    {
        RoleSearcher searcher = new RoleSearcher();
        return baseDaoSupport.find(searcher, this);
    }
    
    @Override
    public RoleDetailsModel get(String id)
    {
        Role entity = baseDaoSupport.get(Role.class, id);
        
        if (null == entity)
        {
            return null;
        }
        
        return new RoleDetailsModel(entity);
    }
    
    @Override
    @Transactional
    public String create(RoleCreateRequest request)
    {
        Role role = new Role();
        role.setName(request.getName());
        role.setDeleted(false);
        role.setCreateTime(new Date());
        
        if (!CollectionUtils.isEmpty(request.getAuthorities()))
        {
            Authority authority;
            Set<Authority> authorities = new HashSet<Authority>();
            
            for (String authorityId : request.getAuthorities())
            {
                authority = new Authority();
                authority.setId(authorityId);
                authorities.add(authority);
            }
            
            role.setAuthorities(authorities);
        }
        
        baseDaoSupport.insert(role);
        return role.getId();
    }
    
    @Override
    @Transactional
    public void modify(RoleModifyRequest request)
    {
        Role role = getTargetRecord(request.getId());
        role.setName(request.getName());
        
        if (!CollectionUtils.isEmpty(request.getAuthorities()))
        {
            Authority authority;
            Set<Authority> authorities = new HashSet<Authority>();
            
            for (String authorityId : request.getAuthorities())
            {
                authority = new Authority();
                authority.setId(authorityId);
                authorities.add(authority);
            }
            
            role.setAuthorities(authorities);
        }
        
        baseDaoSupport.update(role);
    }
    
    @Override
    @Transactional
    public void delete(String id)
    {
        Role role = getTargetRecord(id);
        role.setUsers(null);
        role.setAuthorities(null);
        role.setDeleted(true);
        role.setDeleteTime(new Date());
        role.setDeleted(true);
        baseDaoSupport.update(role);
    }
    
    private Role getTargetRecord(String id)
    {
        Role role = baseDaoSupport.get(Role.class, id);
        
        if (null == role)
        {
            throw new ServiceException(ErrorCode.RECORD_NOT_EXISTS);
        }
        
        return role;
    }
    
    @Override
    public RoleSimpleModel wrap(Role entity)
    {
        return new RoleSimpleModel(entity);
    }
    
    @Override
    public List<RoleTemplate> roleSelect(RoleListRequest request)
    {
        List<RoleTemplate> list = Lists.newArrayList();
        request.setName(null == request.getName() ? "" : request.getName());
        List<Role> s = baseDaoSupport.find(Role.class, " from Role r where r.name like '%" + request.getName() + "%' and r.deleted = false");
        for (Role role : s)
        {
            RoleTemplate rt = new RoleTemplate();
            rt.setId(role.getId());
            rt.setName(role.getName());
            list.add(rt);
        }
        return list;
    }

    @Override
    public List<UserSimpleModel> getUserListByRole(String id) {
        List<UserSimpleModel> results = Lists.newArrayList();
        String hql = "FROM User u WHERE u.deleted = false AND EXISTS(SELECT r.id FROM UserRole r WHERE r.user=u.id AND r.role='"+id+"')";
        List<User> userList = baseDaoSupport.find(User.class,hql);
        if(Collections3.isNotEmpty(userList))
        {
            userList.forEach(x->results.add(new UserSimpleModel(x)));
        }
        return results;
    }

    @Override
    public boolean validate(RoleSearcher request) {
        NamedQueryer queryer =
                NamedQueryer.builder().hql("from Role r where r.name = :name and r.deleted = false ")
                        .names(Lists.newArrayList("name"))
                        .values(Lists.newArrayList(request.getName()))
                        .build();
        List<Role> roles = baseDaoSupport.find(queryer, Role.class);

        NamedQueryer queryers =
                NamedQueryer.builder().hql("from Role r where r.id = :id and r.deleted = false ")
                        .names(Lists.newArrayList("id"))
                        .values(Lists.newArrayList(request.getId()))
                        .build();
        List<Role> rolees = baseDaoSupport.find(queryers, Role.class);

        if (Collections3.isNotEmpty(roles)){
            if (Collections3.isNotEmpty(rolees))
            {
                return true;
            }else {
                return false;
            }
        }
        return true;
    }

    @Override
    public RoleSimpleModel getRoleByName(RoleSearcher searcher)
    {
        String hql = "from Role r where r.name = :name and r.deleted = false";
        List<Role> records = baseDaoSupport.findByNamedParam(Role.class, hql, new String[] {"name"}, new Object[] {searcher.getName()});
        Role role = Collections3.getFirst(records);
        RoleSimpleModel rsm = new RoleSimpleModel();
        BeanUtils.copyProperties(role, rsm); //BeanUtils.copyProperties(A,B); 是A中的值赋值给B
        return rsm;
    }
}
