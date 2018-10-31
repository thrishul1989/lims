package com.todaysoft.lims.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.report.dao.searcher.DataTemplateColumnSearcher;
import com.todaysoft.lims.report.dao.searcher.DataTemplateSearcher;
import com.todaysoft.lims.report.entity.DataTemplate;
import com.todaysoft.lims.report.entity.DataTemplateColumn;
import com.todaysoft.lims.report.service.IDataTemplateService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class DataTemplateService implements IDataTemplateService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<DataTemplate> paging(DataTemplateSearcher searcher)
    {
        return baseDaoSupport.find(searcher.toQuery(), searcher.getPageNo(), searcher.getPageSize(), DataTemplate.class);
    }
    
    @Override
    public DataTemplate get(String id)
    {
        return baseDaoSupport.get(DataTemplate.class, id);
    }
    
    @Override
    @Transactional
    public void create(DataTemplate template)
    {
        baseDaoSupport.insert(template);
        for (DataTemplateColumn column : template.getColumnList())
        {
            column.setTemplate(template);
            baseDaoSupport.insert(column);
        }
    }
    
    @Override
    @Transactional
    public void modify(DataTemplate template)
    {
        DataTemplate dt = get(template.getId());
        if (null != dt)
        {
            for (DataTemplateColumn column : dt.getColumnList())
            {
                baseDaoSupport.delete(column);
            }
        }
        for (DataTemplateColumn column : template.getColumnList())
        {
            column.setTemplate(template);
            baseDaoSupport.insert(column);
        }
        baseDaoSupport.merge(template);
    }
    
    @Override
    @Transactional
    public void delete(String id)
    {
        DataTemplate entity = get(id);
        entity.setDelFlag(true);
        baseDaoSupport.update(entity);
    }
    
    @Override
    public boolean validate(DataTemplate request)
    {
        String hql = "FROM DataTemplate dt WHERE dt.delFlag = false AND dt.name = :name";
        NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(Lists.newArrayList("name")).values(Lists.newArrayList(request.getName())).build();
        List<DataTemplate> list = baseDaoSupport.find(queryer, DataTemplate.class);
        if (Collections3.isNotEmpty(list))
        {
            if (StringUtils.isEmpty(request.getId()))
            {
                return false;
            }
            else
            {
                DataTemplate dt = Collections3.getFirst(list);
                if (!dt.getId().equals(request.getId()))
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override
    public List<DataTemplate> dataTemplateList(DataTemplateSearcher searcher)
    {
        Pagination<DataTemplate> pagination = paging(searcher);
        if (null != pagination)
        {
            return pagination.getRecords();
        }
        return null;
    }
    
    @Override
    public List<DataTemplateColumn> dataTemplateColumnList(DataTemplateColumnSearcher searcher)
    {
        return baseDaoSupport.find(searcher.toQuery(), DataTemplateColumn.class);
    }
    
    @Override
    public DataTemplateColumn getDataTemplateColumn(String id)
    {
        return baseDaoSupport.get(DataTemplateColumn.class, id);
    }
}
