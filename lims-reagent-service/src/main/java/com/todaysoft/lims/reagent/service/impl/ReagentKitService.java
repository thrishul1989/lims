package com.todaysoft.lims.reagent.service.impl;

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
import com.todaysoft.lims.reagent.entity.ReagentKit;
import com.todaysoft.lims.reagent.entity.ReagentKitReagent;
import com.todaysoft.lims.reagent.entity.ReagentKitTask;
import com.todaysoft.lims.reagent.model.request.ReagentKitModel;
import com.todaysoft.lims.reagent.service.IReagentKitService;
import com.todaysoft.lims.utils.Collections3;

@Service
public class ReagentKitService implements IReagentKitService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<ReagentKit> paging(ReagentKitModel request)
    {
        return baseDaoSupport.find(request.toQuery(), request.getPageNo(), request.getPageSize(), ReagentKit.class);
    }
    
    @Override
    public List<ReagentKit> list(ReagentKitModel request)
    {
        return baseDaoSupport.find(request);
    }
    
    @Override
    public ReagentKit get(String id)
    {
        return baseDaoSupport.get(ReagentKit.class, id);
    }
    
    @Override
    @Transactional
    public void create(ReagentKitModel request)
    {
        ReagentKit entity = new ReagentKit();
        BeanUtils.copyProperties(request, entity);
        entity.setDeleted(false);
        entity.setCreateTime(new Date());
        
        baseDaoSupport.insert(entity);
        for (ReagentKitTask kitTask : request.getKitTaskList())
        {
            /*ReagentKit reagentKit = new ReagentKit();
            reagentKit.setId(entity.getId());*/
            kitTask.setReagentKit(entity);
            baseDaoSupport.insert(kitTask);
        }
        for (ReagentKitReagent reagentKitReagent : request.getReagentKitReagentList())
        {
            ReagentKit reagentKit = new ReagentKit();
            reagentKit.setId(entity.getId());
            reagentKitReagent.setReagentKit(entity);
            baseDaoSupport.insert(reagentKitReagent);
        }
    }
    
    @Override
    @Transactional
    public void modify(ReagentKit request)
    {
        ReagentKit entity = new ReagentKit();
        BeanUtils.copyProperties(request, entity);
        
        baseDaoSupport.update(entity);
        //先清空
        baseDaoSupport.executeHql("delete  ReagentKitTask r where r.reagentKit.id= ?", new Object[] {entity.getId()});
        baseDaoSupport.executeHql("delete ReagentKitReagent r where r.reagentKit.id = ?", new Object[] {entity.getId()});
        //        baseDaoSupport.execute("delete  ReagentKitTask r where r.reagentKit.id=" + entity.getId());
        //        baseDaoSupport.execute("delete ReagentKitReagent r where r.reagentKit.id=" + entity.getId());
        //重新插入
        for (ReagentKitTask kitTask : request.getKitTaskList())
        {
            ReagentKit reagentKit = new ReagentKit();
            reagentKit.setId(entity.getId());
            kitTask.setReagentKit(entity);
            baseDaoSupport.insert(kitTask);
        }
        for (ReagentKitReagent reagentKitReagent : request.getReagentKitReagentList())
        {
            ReagentKit reagentKit = new ReagentKit();
            reagentKit.setId(entity.getId());
            reagentKitReagent.setReagentKit(reagentKit);
            baseDaoSupport.insert(reagentKitReagent);
        }
        
    }
    
    @Override
    @Transactional
    public void delete(String id)
    {
        ReagentKit entity = get(id);
        entity.setDeleted(true);
        entity.setDeleteTime(new Date());
        baseDaoSupport.update(entity);
    }
    
    @Override
    public boolean validate(ReagentKit request)
    {
        
        if (Collections3.isNotEmpty(baseDaoSupport.vaildateUniquen(ReagentKit.class, request, "code", "name")))
        {
            return false;
        }
        return true;
    }
    
    @Override
    public Pagination<ReagentKit> selectReagentKit(ReagentKitModel request)
    {
        String hql = "FROM ReagentKit u WHERE u.name LIKE :name or u.code LIKE :code AND u.deleted = false";
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql(hql)
                .names(Lists.newArrayList("name", "code"))
                .values(Lists.newArrayList((Object)("%" + request.getName() + "%"), (Object)("%" + request.getName() + "%")))
                .build();
        Pagination<ReagentKit> pageReagentKit = baseDaoSupport.find(queryer, request.getPageNo(), request.getPageSize(), ReagentKit.class);
        
        return pageReagentKit;
    }
    
    @Override
    public List<ReagentKit> listByTaskId(String id)
    {
        String hql = "FROM ReagentKitTask r WHERE r.taskConfigId = :id AND r.reagentKit.deleted = false";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("id")).values(Lists.newArrayList((Object)id)).build();
        List<ReagentKitTask> list = baseDaoSupport.find(queryer, ReagentKitTask.class);
        List<ReagentKit> listResult = Lists.newArrayList();
        if (Collections3.isEmpty(list))
        {
            return listResult;
        }
        for (ReagentKitTask r : list)
        {
            listResult.add(r.getReagentKit());
        }
        
        return listResult;
    }
}
