package com.todaysoft.lims.reagent.model.request;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.reagent.entity.ReagentKit;
import com.todaysoft.lims.reagent.entity.ReagentKitReagent;
import com.todaysoft.lims.reagent.entity.ReagentKitTask;
import com.todaysoft.lims.utils.StringUtils;

public class ReagentKitModel implements ISearcher<ReagentKit>
{
    private int pageNo;
    
    private int pageSize;
    
    private String id;
    
    private String code;
    
    private String name;
    
    private String type;
    
    private Integer reaction;
    
    private String taskId;
    
    public Integer getReaction()
    {
        return reaction;
    }
    
    public void setReaction(Integer reaction)
    {
        this.reaction = reaction;
    }
    
    private List<ReagentKitTask> kitTaskList;
    
    private List<ReagentKitReagent> reagentKitReagentList;
    
    public List<ReagentKitReagent> getReagentKitReagentList()
    {
        return reagentKitReagentList;
    }
    
    public void setReagentKitReagentList(List<ReagentKitReagent> reagentKitReagentList)
    {
        this.reagentKitReagentList = reagentKitReagentList;
    }
    
    public List<ReagentKitTask> getKitTaskList()
    {
        return kitTaskList;
    }
    
    public void setKitTaskList(List<ReagentKitTask> kitTaskList)
    {
        this.kitTaskList = kitTaskList;
    }
    
    @Override
    public Class<ReagentKit> getEntityClass()
    {
        return ReagentKit.class;
    }
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM ReagentKit r WHERE 1=1");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != code && !"".equals(code))
        {
            hql.append(" AND r.code LIKE :code");
            paramNames.add("code");
            parameters.add("%" + code + "%");
        }
        
        if (null != name && !"".equals(name))
        {
            hql.append(" AND r.name LIKE :name");
            paramNames.add("name");
            parameters.add("%" + name + "%");
        }
        
        if (null != type && !"".equals(type))
        {
            hql.append(" AND r.type = :type");
            paramNames.add("type");
            parameters.add(type);
        }
        if (StringUtils.isNotEmpty(taskId))
        {
            hql.append(" AND r.id in (select rkt.reagentKit.id from ReagentKitTask rkt where rkt.taskConfigId = :taskId)");
            paramNames.add("taskId");
            parameters.add(taskId);
        }
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public int getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public String getTaskId()
    {
        return taskId;
    }
    
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
}
