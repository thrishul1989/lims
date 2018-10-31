package com.todaysoft.lims.schedule.service.impl;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.schedule.entity.OrderPlanTask;
import com.todaysoft.lims.schedule.service.IReportGenerateService;
import com.todaysoft.lims.schedule.service.ISchedulePlanService;

@Service
public class ReportGenerateService implements IReportGenerateService
{
    private static final String REPORT_GENERATE = "REPORT_GENERATE";
    
    private static Logger log = LoggerFactory.getLogger(ReportGenerateService.class);
    
    @Autowired
    private ISchedulePlanService schedulePlanService;
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public void updatePlanWithReportGenerate(String orderId)
    {
        schedulePlanService.updatePlanFinish(orderId, REPORT_GENERATE, null);
        
    }

    @Override
    @Transactional
    public void openReportGenerate(String orderId, String productId)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM OrderPlanTask opt WHERE opt.orderId = :orderId AND opt.productId = :productId AND opt.taskSemantic = :reportGenerateSemantic")
                .names(Arrays.asList("orderId", "productId", "reportGenerateSemantic"))
                .values(Arrays.asList(orderId, productId, OrderPlanTask.SEMANTIC_REPORT_GENERATE))
                .build();
        
        List<OrderPlanTask> records = baseDaoSupport.find(queryer, OrderPlanTask.class);
        
        if (CollectionUtils.isEmpty(records))
        {
            log.error("Can not found the report generate plan task for order {}.", orderId);
            return;
        }
        
        if (records.size() > 1)
        {
            throw new IllegalStateException();
        }
        
        OrderPlanTask task = records.get(0);
        task.setActived(true);
        task.setActualStartDate(new Date());
        task.setStarted(true);
        baseDaoSupport.update(task);
        
    }
    
}
