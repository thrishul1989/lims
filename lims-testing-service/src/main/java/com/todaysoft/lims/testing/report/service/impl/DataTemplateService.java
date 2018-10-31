package com.todaysoft.lims.testing.report.service.impl;

import java.util.List;
import java.util.Map;

import com.todaysoft.lims.testing.base.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.report.dao.DataTemplateColumnSearcher;
import com.todaysoft.lims.testing.report.dao.DataTemplateSearcher;
import com.todaysoft.lims.testing.report.service.IDataTemplateService;
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
    public List<DataTemplate> dataTemplateList(String testingMethodId)
    {
        String hql = "FROM DataTemplate d WHERE d.delFlag = false AND d.testingMethod.id='" + testingMethodId + "'";
        List<DataTemplate> list = baseDaoSupport.find(DataTemplate.class, hql);
        return Collections3.isNotEmpty(list) ? list : Lists.newArrayList();
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

    @Override
    public List<TestingMethod> getTestingMethodList()
    {
        String hql = "FROM TestingMethod";
        List<TestingMethod> list = baseDaoSupport.find(TestingMethod.class, hql);
        return list;
    }

    @Override
    public DataTemplate getDataTemplateMapBySheetId(String sheetId)
    {
        TestingSheet testingSheet = baseDaoSupport.get(TestingSheet.class, sheetId);
        if (null == testingSheet)
        {
            return null;
        }
        TestingSheetTask sheetTask = Collections3.getFirst(testingSheet.getTestingSheetTaskList());
        if (null == sheetTask)
        {
            return null;
        }
        String taskId = sheetTask.getTestingTaskId();
        String hql = "FROM TestingSchedule t WHERE EXISTS(select his.scheduleId FROM TestingScheduleHistory his WHERE t.id = his.scheduleId AND his.taskId=:taskId)";
        List<TestingSchedule> schedules = baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"taskId"}, new String[] {taskId});
        if(Collections3.isEmpty(schedules))
        {
            String hql2 = "FROM TestingSchedule t WHERE EXISTS(select act.schedule.id FROM TestingScheduleActive act WHERE t.id = act.schedule.id AND act.taskId=:taskId)";
            schedules = baseDaoSupport.findByNamedParam(TestingSchedule.class, hql2, new String[] {"taskId"}, new String[] {taskId});
        }
        if (Collections3.isNotEmpty(schedules))
        {
            String productId = Collections3.getFirst(schedules).getProductId();
            String methodId = Collections3.getFirst(schedules).getMethodId();
            //如果是验证直接去取模板表 否则根据产品去查
            TestingMethod method = baseDaoSupport.get(TestingMethod.class,methodId);
            if("2".equals(method.getType()))
            {
                String templateHql = " FROM DataTemplate d WHERE d.testingMethod.id=:methodId AND d.delFlag=false ";
                List<DataTemplate> templates = baseDaoSupport.findByNamedParam(DataTemplate.class,templateHql,new String[]{"methodId"},new String[]{methodId});
                DataTemplate dataTemplate = Collections3.getFirst(templates);
                wrap(dataTemplate);
                return dataTemplate;
            }else{
                //根据产品id和检测方法id定位到数据上传模板
                String hql2 = " FROM ProductTestingMethod p WHERE p.product.id=:productId AND p.testingMethod.id=:methodId ";
                List<ProductTestingMethod> pMethod = baseDaoSupport.findByNamedParam(ProductTestingMethod.class,hql2,new String[]{"productId","methodId"},new String[]{productId,methodId});
                if(Collections3.isNotEmpty(pMethod))
                {
                    String dataTemplateId = Collections3.getFirst(pMethod).getDataTemplateId();
                    if(StringUtils.isNotEmpty(dataTemplateId))
                    {
                        DataTemplate dataTemplate = baseDaoSupport.get(DataTemplate.class,dataTemplateId);
                        wrap(dataTemplate);
                        return dataTemplate;
                    }
                }
            }
        }
        return null;
    }

    public void wrap(DataTemplate dataTemplate)
    {
        Map<String, String> map = Maps.newHashMap();
        if (null == dataTemplate)
        {
            return;
        }
        List<DataTemplateColumn> columnList = dataTemplate.getColumnList();
        for (DataTemplateColumn dc : columnList)
        {
            if (StringUtils.isEmpty(dc.getSemantic()))
            {
                continue;
            }
            map.put(dc.getColumnName(), dc.getSemantic());
        }
        dataTemplate.setMap(map);
    }

    @Override
    public boolean validateTestingMethod(String testingMethodId)
    {
        String hql = "select d.testingMethod.id FROM DataTemplate d left join d.testingMethod tm where tm.type=2 and d.delFlag=false";
        List<String> testingMethodIds = baseDaoSupport.find(String.class, hql);
        return testingMethodIds.contains(testingMethodId);
    }

    @Override
    public DataTemplate dataTemplateByMethodSemantic(String semantic) {
        String hql = "FROM DataTemplate d WHERE d.delFlag = false AND d.testingMethod.semantic='" + semantic + "'";
        List<DataTemplate> list = baseDaoSupport.find(DataTemplate.class, hql);
        return Collections3.getFirst(list);
    }
}
