package com.todaysoft.lims.biology.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.lims.biology.model.Pagination;
import com.todaysoft.lims.biology.model.entity.DataTemplate;
import com.todaysoft.lims.biology.model.entity.DataTemplateColumn;
import com.todaysoft.lims.biology.model.request.DataTemplateRequest;
import com.todaysoft.lims.biology.model.response.DataTemplateModel;
import com.todaysoft.lims.biology.mybatis.mapper.DataTemplateColumnMapper;
import com.todaysoft.lims.biology.mybatis.mapper.DataTemplateMapper;
import com.todaysoft.lims.biology.service.IDataTemplateService;
import com.todaysoft.lims.utils.IdGen;

@Service
public class DataTemplateService implements IDataTemplateService
{
    
    private static Logger log = LoggerFactory.getLogger(DataTemplateService.class);
    
    @Autowired
    private DataTemplateMapper dataTemplateMapper;
    
    @Autowired
    private DataTemplateColumnMapper columnMapper;
    
    @Override
    public Pagination<DataTemplate> pager(DataTemplateRequest request)
    {
        Pagination<DataTemplate> result = new Pagination<DataTemplate>();
        result.setTotalCount((int)count(request));
        result.setRecords(search(request));
        result.setPageNo(request.getPageNo());
        result.setPageSize(request.getPageSize());
        return result;
    }
    
    private List<DataTemplate> search(DataTemplateRequest request)
    {
        return dataTemplateMapper.search(request);
    }
    
    private long count(DataTemplateRequest request)
    {
        return dataTemplateMapper.count(request);
    }
    
    @Override
    @Transactional
    public void create(DataTemplateRequest request)
    {
        DataTemplate record = new DataTemplate();
        BeanUtils.copyProperties(request, record);
        record.setId(IdGen.uuid());
        record.setCreateTime(new Date());
        dataTemplateMapper.insert(record);
        List<DataTemplateColumn> columnList = request.getColumnList();
        if (null != columnList && columnList.size() > 0)
        {
            for (DataTemplateColumn dc : columnList)
            {
                DataTemplateColumn colum = new DataTemplateColumn();
                BeanUtils.copyProperties(dc, colum);
                colum.setId(IdGen.uuid());
                colum.setTemplateId(record.getId());
                columnMapper.insert(colum);
            }
        }
        
    }
    
    @Override
    public boolean validate(DataTemplateRequest request)
    {
        DataTemplate dt = dataTemplateMapper.selectByName(request.getName());
        if (null != dt)
        {
            if (StringUtils.isEmpty(request.getId()))
            {
                return false;
            }
            else
            {
                if (!dt.getId().equals(request.getId()))
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override
    @Transactional
    public void update(DataTemplateRequest request)
    {
        DataTemplate record = new DataTemplate();
        BeanUtils.copyProperties(request, record);
        dataTemplateMapper.update(record);
        List<DataTemplateColumn> list = request.getColumnList();
        if (null != list && list.size() > 0)
        {
            for (DataTemplateColumn col : list)
            {
                if (StringUtils.isNotEmpty(col.getId()))
                {
                    columnMapper.update(col);
                }
                else
                {
                    col.setId(IdGen.uuid());
                    col.setTemplateId(request.getId());
                    columnMapper.insert(col);
                }
            }
        }
        List<String> deleteIds = request.getDeleteIds();
        if (null != deleteIds && deleteIds.size() > 0)
        {
            for (String id : deleteIds)
            {
                columnMapper.deleteByPrimaryKey(id);
            }
        }
    }
    
    @Override
    @Transactional
    public void delete(DataTemplateRequest request)
    {
        DataTemplate record = new DataTemplate();
        record.setDelFlag(true);
        record.setId(request.getId());
        dataTemplateMapper.update(record);
    }
    
    @Override
    public DataTemplateModel getById(String id)
    {
        DataTemplateModel model = new DataTemplateModel();
        DataTemplate dt = dataTemplateMapper.selectByPrimaryKey(id);
        List<DataTemplateColumn> list = columnMapper.selectByTemplateId(id);
        BeanUtils.copyProperties(dt, model);
        model.setColumnList(list);
        return model;
    }
    
    @Override
    public List<DataTemplate> getDataTemplateList(DataTemplateRequest request)
    {
        return search(request);
    }
    
    @Override
    public DataTemplateColumn getColumnById(String dataColumnId)
    {
        return columnMapper.selectByPrimaryKey(dataColumnId);
    }
    
}
