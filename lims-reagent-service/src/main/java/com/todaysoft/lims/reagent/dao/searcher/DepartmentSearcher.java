package com.todaysoft.lims.reagent.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.reagent.entity.Customer;
import com.todaysoft.lims.reagent.entity.Department;
import com.todaysoft.lims.utils.StringUtils;

public class DepartmentSearcher implements ISearcher<Department>
{
    
    private String id;
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public Department getParentId()
    {
        return parentId;
    }
    public void setParentId(Department parentId)
    {
        this.parentId = parentId;
    }
    public String getText()
    {
        return text;
    }
    public void setText(String text)
    {
        this.text = text;
    }
    public String getPrincipalId()
    {
        return principalId;
    }
    public void setPrincipalId(String principalId)
    {
        this.principalId = principalId;
    }
    public String getRemark()
    {
        return remark;
    }
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    public String getCreateTime()
    {
        return createTime;
    }
    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
    public Integer getDelFlag()
    {
        return delFlag;
    }
    public void setDelFlag(Integer delFlag)
    {
        this.delFlag = delFlag;
    }
    public String getDeleteTime()
    {
        return deleteTime;
    }
    public void setDeleteTime(String deleteTime)
    {
        this.deleteTime = deleteTime;
    }
    private Department parentId;
    private String text;
    private String principalId;
    private String remark;
    private String createTime;
    private Integer delFlag;
    private String deleteTime;
    private Integer pageNo;
    private Integer pageSize;
    
    
    public Integer getPageNo()
    {
        return pageNo;
    }
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    public Integer getPageSize()
    {
        return pageSize;
    }
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append(" FROM Department d  where  d.parentId is null  and d.delFlag = 0  ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        
      
      
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    @Override
    public Class<Department> getEntityClass()
    {
        return Department.class;
    }
   
    
    
    
}
