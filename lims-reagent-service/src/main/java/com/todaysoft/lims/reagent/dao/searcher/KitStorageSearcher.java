package com.todaysoft.lims.reagent.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.reagent.entity.KitStorage;
import com.todaysoft.lims.reagent.entity.ReagentKit;

public class KitStorageSearcher implements ISearcher<KitStorage>
{
    private String code;
    
    private Integer reactionNum;
    
    private Integer pageSize;
    
    private Integer pageNo;
    
    private Integer createNum;
    
    public Integer getCreateNum()
    {
        return createNum;
    }
    
    public void setCreateNum(Integer createNum)
    {
        this.createNum = createNum;
    }
    
    public Integer getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public Integer getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public Integer getReactionNum()
    {
        return reactionNum;
    }
    
    public void setReactionNum(Integer reactionNum)
    {
        this.reactionNum = reactionNum;
    }
    
    public ReagentKit getReagentKit()
    {
        return reagentKit;
    }
    
    public void setReagentKit(ReagentKit reagentKit)
    {
        this.reagentKit = reagentKit;
    }
    
    public Integer getSeq()
    {
        return seq;
    }
    
    public void setSeq(Integer seq)
    {
        this.seq = seq;
    }
    
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    private ReagentKit reagentKit;
    
    private Integer seq;
    
    private Integer id;
    
    @Override
    public Class<KitStorage> getEntityClass()
    {
        return KitStorage.class;
    }
    
    private void addCodeFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != code && !"".equals(code))
        {
            hql.append(" AND p.code LIKE :code");
            paramNames.add("code");
            parameters.add("%" + code + "%");
        }
    }
    
    private void addKitFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (null != reagentKit && !"".equals(reagentKit))
        {
            hql.append(" AND p.reagentKit.id=:kitId");
            paramNames.add("kitId");
            parameters.add(reagentKit.getId());
        }
    }
    
    private void addStateFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        
        hql.append(" AND p.reactionNum != 0");
        
    }
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM KitStorage p WHERE 1=1");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addCodeFilter(hql, paramNames, parameters);
        addKitFilter(hql, paramNames, parameters);
        addStateFilter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
}
