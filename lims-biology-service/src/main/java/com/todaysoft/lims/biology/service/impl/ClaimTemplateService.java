package com.todaysoft.lims.biology.service.impl;

import com.todaysoft.lims.biology.model.Pagination;
import com.todaysoft.lims.biology.model.entity.ClaimTemplate;
import com.todaysoft.lims.biology.model.entity.ClaimTemplateColumn;
import com.todaysoft.lims.biology.model.entity.DataTemplateColumn;
import com.todaysoft.lims.biology.model.entity.FilterItems;
import com.todaysoft.lims.biology.model.request.ClaimTemplateColumnModel;
import com.todaysoft.lims.biology.model.request.ClaimTemplateRequest;
import com.todaysoft.lims.biology.model.request.FilterItemsRequest;
import com.todaysoft.lims.biology.model.response.ClaimTemplateModel;
import com.todaysoft.lims.biology.model.response.DataTemplateModel;
import com.todaysoft.lims.biology.mybatis.mapper.ClaimTemplateColumnMapper;
import com.todaysoft.lims.biology.mybatis.mapper.ClaimTemplateMapper;
import com.todaysoft.lims.biology.mybatis.mapper.FilterItemsMapper;
import com.todaysoft.lims.biology.service.IClaimTemplateService;
import com.todaysoft.lims.biology.service.IDataTemplateService;
import com.todaysoft.lims.biology.service.IFilterItemsService;
import com.todaysoft.lims.biology.util.JsonUtils;
import com.todaysoft.lims.utils.IdGen;
import com.todaysoft.lims.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ClaimTemplateService implements IClaimTemplateService
{
    @Autowired
    private ClaimTemplateMapper claimTemplatemapper;
    
    @Autowired
    private ClaimTemplateColumnMapper columnMapper;
    
    @Autowired
    private FilterItemsMapper filterItemsMapper;
    
    @Autowired
    private IDataTemplateService dataTemplateService;
    
    @Autowired
    private IFilterItemsService filterItemsService;
    
    @Override
    public Pagination<ClaimTemplate> pager(ClaimTemplateRequest request)
    {
        Pagination<ClaimTemplate> result = new Pagination<ClaimTemplate>();
        result.setTotalCount(count(request));
        result.setRecords(search(request));
        result.setPageNo(request.getPageNo());
        result.setPageSize(request.getPageSize());
        return result;
    }
    
    private int count(ClaimTemplateRequest request)
    {
        return claimTemplatemapper.count(request);
    }
    
    private List<ClaimTemplate> search(ClaimTemplateRequest request)
    {
        return claimTemplatemapper.search(request);
    }
    
    @Override
    public List<FilterItems> getItemList(FilterItems record)
    {
        List<FilterItems> list = new ArrayList<>();
        if (record.getSemantic() == null || record.getSemantic() == "")
        {
            return list;
        }
        return filterItemsMapper.select(record);
    }
    
    @Override
    public boolean validate(ClaimTemplateRequest request)
    {
        ClaimTemplate dt = claimTemplatemapper.selectByName(request.getName());
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
    public void create(ClaimTemplateRequest request)
    {
        ClaimTemplate record = new ClaimTemplate();
        BeanUtils.copyProperties(request, record);
        record.setId(IdGen.uuid());
        record.setCreateTime(new Date());
        record.setPriFlag(false);
        claimTemplatemapper.insert(record);
        List<ClaimTemplateColumnModel> modelList = request.getColumnList();
        if (null != modelList && modelList.size() > 0)
        {
            for (ClaimTemplateColumnModel model : modelList)
            {
                ClaimTemplateColumn column = new ClaimTemplateColumn();
                BeanUtils.copyProperties(model, column);
                column.setId(IdGen.uuid());
                column.setTemplateId(record.getId());
                column.setDataColumnId(model.getDataTemplateColumnId());
                if (null != model.getRadioValue() && "" != model.getRadioValue())
                {
                    column.setDefaultValue(model.getRadioValue());
                }
                if (null != model.getRangeValue1() && "" != model.getRangeValue1() && null != model.getRangeValue2() && "" != model.getRangeValue2())
                {
                    column.setDefaultValue(model.getRangeValue1() + "-" + model.getRangeValue2());
                }
                else if (null != model.getRangeValue1() && "" != model.getRangeValue1())
                {
                    column.setDefaultValue(model.getRangeValue1() + "-");
                }
                else if (null != model.getRangeValue2() && "" != model.getRangeValue2())
                {
                    column.setDefaultValue("-" + model.getRangeValue2());
                }
                columnMapper.insert(column);
            }
        }
        
    }
    
    @Override
    public ClaimTemplate get(String id)
    {
        return claimTemplatemapper.selectByPrimaryKey(id);
    }
    
    @Override
    public List<ClaimTemplateColumn> getColumn(String templateId)
    {
        return columnMapper.selectByTemplateId(templateId);
    }
    
    /*
     
    
     @Override
     public List<ClaimTemplate> selectForPriFlag()
     {
         return mapper.selectForPriFlag();
     }*/
    
    @Override
    @Transactional
    public void saveItem(FilterItemsRequest request)
    {
        FilterItems record = new FilterItems();
        BeanUtils.copyProperties(request, record);
        record.setId(IdGen.uuid());
        record.setDelFlag(false);
        filterItemsMapper.insert(record);
    }
    
    @Override
    public ClaimTemplateModel getByIdForView(String id)
    {
        ClaimTemplateModel model = new ClaimTemplateModel();
        List<ClaimTemplateColumnModel> colModellist = new ArrayList<ClaimTemplateColumnModel>();
        ClaimTemplate ct = get(id);
        BeanUtils.copyProperties(ct, model);
        DataTemplateModel dt = dataTemplateService.getById(ct.getTemplateId());
        if (null != dt)
        {
            model.setTemplateId(dt.getName());
        }
        else
        {
            model.setTemplateId("");
        }
        List<ClaimTemplateColumn> list = getColumn(id);
        if (null != list && list.size() > 0)
        {
            for (ClaimTemplateColumn col : list)
            {
                ClaimTemplateColumnModel colModel = new ClaimTemplateColumnModel();
                DataTemplateColumn dataCol = dataTemplateService.getColumnById(col.getDataColumnId());
                if (null != dataCol)
                {
                    colModel.setDataColumnId(dataCol.getColumnName());
                    if ("1".equals(dataCol.getType()) || "2".equals(dataCol.getType()))
                    {
                        if (StringUtils.isNotEmpty(col.getFilterName()))
                        {
                            String s = "";
                            String[] strArr = col.getFilterName().split(",");
                            for (int i = 0; i < strArr.length; i++)
                            {
                                FilterItems fi = filterItemsService.get(strArr[i]);
                                if (i == 0)
                                {
                                    s = fi.getName();
                                }
                                else
                                {
                                    s += "," + fi.getName();
                                }
                            }
                            colModel.setFilterName(s);
                        }
                    }
                    else
                    {
                        colModel.setFilterName(col.getFilterName());
                    }
                    if ("1".equals(dataCol.getType()))
                    {
                        if (StringUtils.isNotEmpty(col.getDefaultValue()))
                        {
                            String s = "";
                            String[] strArr = col.getDefaultValue().split(",");
                            for (int i = 0; i < strArr.length; i++)
                            {
                                FilterItems fi = filterItemsService.get(strArr[i]);
                                if (i == 0)
                                {
                                    s = fi.getName();
                                }
                                else
                                {
                                    s += "," + fi.getName();
                                }
                            }
                            colModel.setDefaultValue(s);
                        }
                    }
                    else
                    {
                        colModel.setDefaultValue(col.getDefaultValue());
                    }
                    colModel.setColumnIndex(col.getColumnIndex());
                }
                colModellist.add(colModel);
            }
        }
        model.setColumnList(colModellist);
        return model;
    }
    
    @Override
    @Transactional
    public void delete(ClaimTemplateRequest request)
    {
        ClaimTemplate record = new ClaimTemplate();
        record.setDelFlag(true);
        record.setId(request.getId());
        claimTemplatemapper.update(record);
    }
    
    @Override
    @Transactional
    public void setPriFlag(ClaimTemplateRequest request)
    {
        ClaimTemplate record = new ClaimTemplate();
        claimTemplatemapper.updatePriFlagFalse();
        record.setId(request.getId());
        record.setPriFlag(true);
        claimTemplatemapper.update(record);
    }
    
    @Override
    public ClaimTemplateModel getById(String id)
    {
        ClaimTemplateModel model = new ClaimTemplateModel();
        ClaimTemplate ct = get(id);
        List<ClaimTemplateColumn> list = getColumn(id);
        List<ClaimTemplateColumnModel> colModellist = new ArrayList<ClaimTemplateColumnModel>();
        BeanUtils.copyProperties(ct, model);
        if (null != list && list.size() > 0)
        {
            for (ClaimTemplateColumn col : list)
            {
                ClaimTemplateColumnModel colModel = new ClaimTemplateColumnModel();
                BeanUtils.copyProperties(col, colModel, "defaultValue");
                DataTemplateColumn dataCol = dataTemplateService.getColumnById(col.getDataColumnId());
                
                if (null != dataCol)
                {
                    colModel.setType(dataCol.getType());
                    colModel.setSemantic(dataCol.getSemantic());
                    if ("1".equals(dataCol.getType()))
                    {
                        // colModel.setDefaultValue(col.getDefaultValue());
                        if (StringUtils.isNotEmpty(col.getDefaultValue()))
                        {
                            List<FilterItems> fiList = new ArrayList<FilterItems>();
                            String[] strArr = col.getDefaultValue().split(",");
                            for (String s : strArr)
                            {
                                FilterItems fi = filterItemsService.get(s);
                                fiList.add(fi);
                            }
                            colModel.setDefaultValue(JsonUtils.toJson(fiList));
                        }
                    }
                    if ("2".equals(dataCol.getType()))
                    {
                        colModel.setRadioValue(col.getDefaultValue());
                        FilterItems request = new FilterItems();
                        request.setSemantic(dataCol.getSemantic());
                        colModel.setFilterItemsList(getItemList(request));
                    }
                    if ("3".equals(dataCol.getType()))
                    {
                        if (StringUtils.isNotEmpty(col.getDefaultValue()))
                        {
                            String[] strArr = col.getDefaultValue().split("-");
                            colModel.setRangeValue1(strArr[0]);
                            colModel.setRangeValue2(strArr[1]);
                        }
                    }
                }
                if (StringUtils.isNotEmpty(col.getFilterName()))
                {
                    List<FilterItems> fiList = new ArrayList<FilterItems>();
                    String[] strArr = col.getFilterName().split(",");
                    for (String s : strArr)
                    {
                        FilterItems fi = filterItemsService.get(s);
                        fiList.add(fi);
                    }
                    colModel.setFilterName(JsonUtils.toJson(fiList));
                }
                
                colModellist.add(colModel);
            }
        }
        model.setColumnList(colModellist);
        return model;
    }
    
    @Override
    @Transactional
    public void update(ClaimTemplateRequest request)
    {
        ClaimTemplate record = new ClaimTemplate();
        BeanUtils.copyProperties(request, record);
        claimTemplatemapper.update(record);
        columnMapper.deleteByTemplateId(request.getId());
        List<ClaimTemplateColumnModel> modelList = request.getColumnList();
        if (null != modelList && modelList.size() > 0)
        {
            for (ClaimTemplateColumnModel model : modelList)
            {
                ClaimTemplateColumn column = new ClaimTemplateColumn();
                BeanUtils.copyProperties(model, column);
                column.setId(IdGen.uuid());
                column.setTemplateId(record.getId());
                column.setDataColumnId(model.getDataTemplateColumnId());
                if (null != model.getRadioValue() && "" != model.getRadioValue())
                {
                    column.setDefaultValue(model.getRadioValue());
                }
                if (null != model.getRangeValue1() && "" != model.getRangeValue1() && null != model.getRangeValue2() && "" != model.getRangeValue2())
                {
                    column.setDefaultValue(model.getRangeValue1() + "-" + model.getRangeValue2());
                }
                else if (null != model.getRangeValue1() && "" != model.getRangeValue1())
                {
                    column.setDefaultValue(model.getRangeValue1() + "-");
                }
                else if (null != model.getRangeValue2() && "" != model.getRangeValue2())
                {
                    column.setDefaultValue("-" + model.getRangeValue2());
                }
                columnMapper.insert(column);
            }
        }
    }

    @Override
    public List<ClaimTemplate> claimTemplateList()
    {
        List<ClaimTemplate> list = claimTemplatemapper.claimTemplateList();
        return list;
    }


}
