package com.todaysoft.lims.reagent.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.ConnectSearcher;
import com.todaysoft.lims.reagent.entity.Connect;
import com.todaysoft.lims.reagent.model.request.ConnectRequest;
import com.todaysoft.lims.reagent.service.IConnectService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class ConnectService implements IConnectService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<Connect> paging(ConnectRequest request)
    {
        
        ConnectSearcher connectSearcher = new ConnectSearcher();
        BeanUtils.copyProperties(request, connectSearcher);
        return baseDaoSupport.find(connectSearcher.toQuery(), request.getPageNo(), request.getPageSize(), Connect.class);
    }
    
    @Override
    public Connect getConnect(String id)
    {
        return baseDaoSupport.get(Connect.class, id);
    }
    
    @Override
    @Transactional
    public void create(Connect request)
    {
        Connect entity = new Connect();
        BeanUtils.copyProperties(request, entity);
        entity.setCreateTime(new Date());
        entity.setDeleted(false);
        baseDaoSupport.insert(entity);
    }
    
    @Override
    @Transactional
    public void modify(Connect request)
    {
        baseDaoSupport.update(request);
    }
    
    @Override
    @Transactional
    public void delete(String id)
    {
        Connect entity = getConnect(id);
        entity.setDeleted(true);
        entity.setDeleteTime(new Date());
        baseDaoSupport.update(entity);
    }
    
    @Override
    @Transactional
    public Boolean checkedconnectNum(ConnectRequest connect)
    {
        NamedQueryer query = new NamedQueryer();
        String hql = null;
        if (StringUtils.isNotEmpty(connect.getId()))
        {
            hql = " from Connect c where c.connectNum = :connectNum and c.id != :connectId and c.deleted = false ";
            query.setHql(hql);
            query.setNames(Arrays.asList("connectNum", "connectId"));
            query.setValues(Arrays.asList((Object)connect.getConnectNum(), (Object)connect.getId()));
        }
        else
        {
            hql = " from Connect c where c.connectNum = :connectNum and c.deleted = false ";
            query.setHql(hql);
            query.setNames(Arrays.asList("connectNum"));
            query.setValues(Arrays.asList((Object)connect.getConnectNum()));
        }
        List<Connect> list = baseDaoSupport.find(query, Connect.class);
        if (Collections3.isNotEmpty(list))
        {
            return false;
        }
        return true;
    }
    
    @Override
    public List<Connect> getConnectList(ConnectRequest request)
    {
        return baseDaoSupport.find(Connect.class, "from Connect c where c.deleted = false");
    }
    
    @Override
    public List<Connect> getConnectListById(String ids)
    {
        List<Connect> list = new ArrayList<Connect>();
        String[] string = ids.split(",");
        if (StringUtils.isNotEmpty(string))
        {
            
            for (String id : string)
            {
                list.add(getConnect(id));
            }
        }
        return list;
    }
    
    @Override
    public List<Connect> ConnectListForWkcreate(ConnectRequest request)
    {
        NamedQueryer query = new NamedQueryer();
        String hql = " from Connect c where c.connectNum >= :connectNum and c.deleted = false order by c.connectNum ";
        query.setHql(hql);
        query.setNames(Arrays.asList("connectNum"));
        query.setValues(Arrays.asList((Object)request.getConnectNum()));
        List<Connect> listResult = Lists.newArrayList();
        List<Connect> list1 = baseDaoSupport.find(query, Connect.class);
        if (Collections3.isEmpty(list1))
        {
            list1 = Lists.newArrayList();
        }
        listResult.addAll(list1);
        String hql2 = " from Connect c where c.connectNum < :connectNum and c.deleted = false order by c.connectNum ";
        query.setHql(hql2);
        List<Connect> list2 = baseDaoSupport.find(query, Connect.class);
        if (Collections3.isEmpty(list2))
        {
            list2 = Lists.newArrayList();
        }
        listResult.addAll(list2);
        return listResult;
    }
}
