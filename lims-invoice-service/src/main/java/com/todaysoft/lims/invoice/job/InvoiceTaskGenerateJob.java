package com.todaysoft.lims.invoice.job;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.todaysoft.lims.invoice.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.todaysoft.lims.invoice.ons.InvoiceApplyEvent;
import com.todaysoft.lims.invoice.service.IInvoiceService;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.utils.Collections3;

@Component
public class InvoiceTaskGenerateJob
{
    private static Logger log = LoggerFactory.getLogger(InvoiceTaskGenerateJob.class);
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private IInvoiceService invoiceService;
    
    private JobMonitor autoApplyMonitor = new JobMonitor();
    
    private JobMonitor manualApplyMonitor = new JobMonitor();
    
    private JobMonitor updateInvoiceTaskStatusMonitor = new JobMonitor();

    private JobMonitor dockingInvoiceAutoMonitor = new JobMonitor();

    private JobMonitor dockingInvoiceManualMonitor = new JobMonitor();

    private JobMonitor updateInvoiceInfoMonitor = new JobMonitor();
    
    @Scheduled(cron = "0 0 1,12 * * ? ")
    public void executeOrder()
    {
        if (!autoApplyMonitor.isStartable())
        {
            return;
        }
        
        autoApplyMonitor.setStart(true);
        autoApplyMonitor.setFinish(false);
        
        try
        {
            if (log.isDebugEnabled())
            {
                log.debug("Start to generate invoice tasks for auto apply orders.");
            }
            
            StringBuffer hql = new StringBuffer(1024);
            hql.append("FROM Order o");
            hql.append(" WHERE (o.invoiceApplyStatus IS NULL OR o.invoiceApplyStatus = :invoiceUnappliedStatus)");
            hql.append(" AND (o.contractId IS NULL OR EXISTS (SELECT c.id FROM ContractContent c WHERE c.contractId = o.contractId AND c.invoiceMethod = :invoiceType))");
            hql.append(" AND NOT EXISTS (SELECT t.id FROM FinanceInvoiceTask t WHERE t.recordKey = o.id AND t.category = :autoApplyCategory)");
            hql.append(" AND o.scheduleStatus = :scheduleStartedStatus AND o.deleted = false");
            
            List<String> names = new ArrayList<String>();
            List<Object> values = new ArrayList<Object>();
            
            names.add("invoiceUnappliedStatus");
            values.add(Order.INVOICE_UNAPPLIED);
            
            names.add("invoiceType");
            values.add(ContractContent.INVOICE_TYPE_SINGLE);
            
            names.add("autoApplyCategory");
            values.add(FinanceInvoiceTask.CATEGORY_AUTO);
            
            names.add("scheduleStartedStatus");
            values.add(Order.SCHEDULE_STARTED);
            
            NamedQueryer queryer = NamedQueryer.builder().hql(hql.toString()).names(names).values(values).build();
            
            List<Order> records = baseDaoSupport.find(queryer, Order.class);
            
            if (Collections3.isNotEmpty(records))
            {
                if (log.isDebugEnabled())
                {
                    log.debug("Found {} orders to generate invoice tasks.", records.size());
                }
                
                for (Order record : records)
                {
                    InvoiceApplyEvent event = new InvoiceApplyEvent();
                    event.setApplyType(InvoiceApplyEvent.APPLY_AUTO);
                    event.setApplyKey(record.getId());
                    invoiceService.apply(event);
                }
            }
            else
            {
                if (log.isDebugEnabled())
                {
                    log.debug("Not found any orders to generate invoice tasks.");
                }
            }
            
            if (log.isDebugEnabled())
            {
                log.debug("Generate invoice tasks for auto apply orders finished.");
            }
            
            autoApplyMonitor.setFinish(true);
        }
        catch (Exception e)
        {
            log.error("Generate invoice tasks for auto apply orders error.", e);
            autoApplyMonitor.setFinish(true);
        }
    }
    
    @Scheduled(cron = "0 0 1,12 * * ? ")
    public void executeInvoiceApply()
    {
        if (!manualApplyMonitor.isStartable())
        {
            return;
        }
        
        manualApplyMonitor.setStart(true);
        manualApplyMonitor.setFinish(false);
        
        try
        {
            if (log.isDebugEnabled())
            {
                log.debug("Start to generate invoice tasks for apply records.");
            }
            
            NamedQueryer queryer =
                NamedQueryer.builder()
                    .hql("FROM InvoiceApply ia WHERE NOT EXISTS (SELECT fit.id FROM FinanceInvoiceTask fit WHERE fit.recordKey = ia.id AND fit.category = '2')")
                    .names(Lists.newArrayList())
                    .values(Lists.newArrayList())
                    .build();
            
            List<InvoiceApply> records = baseDaoSupport.find(queryer, InvoiceApply.class);
            
            if (Collections3.isNotEmpty(records))
            {
                if (log.isDebugEnabled())
                {
                    log.debug("Found {} records to generate invoice tasks.", records.size());
                }
                
                for (InvoiceApply record : records)
                {
                    InvoiceApplyEvent event = new InvoiceApplyEvent();
                    event.setApplyType(InvoiceApplyEvent.APPLY_MANUAL);
                    event.setApplyKey(record.getId());
                    invoiceService.apply(event);
                }
            }
            else
            {
                if (log.isDebugEnabled())
                {
                    log.debug("Not found any apply records to generate invoice tasks.");
                }
            }
            
            if (log.isDebugEnabled())
            {
                log.debug("Generate invoice tasks for apply records finished.");
            }
            
            manualApplyMonitor.setFinish(true);
        }
        catch (Exception e)
        {
            log.error("Generate invoice tasks for apply records error.", e);
            manualApplyMonitor.setFinish(true);
        }
    }
    
     @Scheduled(cron = "0 0 2,13 * * ? ")
    //每天凌晨一点钟执行一次
    public void executeStatus()
    {
        if (!updateInvoiceTaskStatusMonitor.isStartable())
        {
            return;
        }
        
        updateInvoiceTaskStatusMonitor.setStart(true);
        updateInvoiceTaskStatusMonitor.setFinish(false);
        
        try
        {
            if (log.isDebugEnabled())
            {
                log.debug("Start to update invoice task status.");
            }
            
            NamedQueryer queryer =
                NamedQueryer.builder().hql("FROM FinanceInvoiceTask t WHERE t.status = 1 and (t.synchroStatus = null or t.synchroStatus != 1)").names(Lists.newArrayList()).values(Lists.newArrayList()).build();
            List<FinanceInvoiceTask> records = baseDaoSupport.find(queryer, FinanceInvoiceTask.class);
            
            List<FinanceInvoiceTask> manualRecords =
                records.stream().filter(record -> InvoiceApplyEvent.APPLY_MANUAL.equals(record.getCategory())).collect(Collectors.toList());
            List<FinanceInvoiceTask> autoRecords =
                records.stream().filter(record -> InvoiceApplyEvent.APPLY_AUTO.equals(record.getCategory())).collect(Collectors.toList());
            
            if (Collections3.isNotEmpty(records))
            {
                if (log.isDebugEnabled())
                {
                    log.debug("Found {} records to update invoice task status.", records.size());
                }
                
                manualRecords.parallelStream().forEach(x -> {
                    invoiceService.updateStatusForManual(x);
                });
                
                autoRecords.parallelStream().forEach(x -> {
                    invoiceService.updateStatusForAuto(x);
                });
            }
            else
            {
                if (log.isDebugEnabled())
                {
                    log.debug("Not found any records to update invoice task status.");
                }
            }
            
            if (log.isDebugEnabled())
            {
                log.debug("Update invoice task status finished.");
            }
            
            updateInvoiceTaskStatusMonitor.setFinish(true);
        }
        catch (Exception e)
        {
            log.error("Update invoice task status error.", e);
            updateInvoiceTaskStatusMonitor.setFinish(true);
        }
    }

    @Scheduled(cron = "0 0/5 * * * ? ")
//    @Scheduled(cron = "0 0 3,14 * * ? ")
    public void executeDockingInvoiceManual()
    {
        if (!dockingInvoiceManualMonitor.isStartable())
        {
            return;
        }

        dockingInvoiceManualMonitor.setStart(true);
        dockingInvoiceManualMonitor.setFinish(false);

        try
        {
            if (log.isDebugEnabled())
            {
                log.debug("- - - - - - Start to docking manual invoice to golden - - - - - -");
            }


            NamedQueryer queryer =
                    NamedQueryer.builder().hql("FROM FinanceInvoiceTask t WHERE t.status = 2 and t.institution ='1' and t.category = '2' and  not exists(select ap.id from InvoiceApply ap where ap.id = t.recordKey and ap.contractId is not null))) ").names(Lists.newArrayList()).values(Lists.newArrayList()).build();
            List<FinanceInvoiceTask> manualRecords = baseDaoSupport.find(queryer, FinanceInvoiceTask.class);

            if (Collections3.isNotEmpty(manualRecords))
            {
                if (log.isDebugEnabled())
                {
                    log.debug("Found {} manualRecords to docking invoice.", manualRecords.size());
                }

                manualRecords.parallelStream().forEach(x -> {
                    invoiceService.dockingInvoiceForManual(x);
                });


            }
            else
            {
                if (log.isDebugEnabled())
                {
                    log.debug("Not found any manualRecords to docking golden invoice.");
                }
            }

            if (log.isDebugEnabled())
            {
                log.debug("- - - - - - Docking manual invoice tasks finished - - - - - -");
            }

            dockingInvoiceManualMonitor.setFinish(true);
        }
        catch (Exception e)
        {
            log.error("Docking manual invoice tasks to golden error.", e);
            dockingInvoiceManualMonitor.setFinish(true);
        }
    }

    @Scheduled(cron = "0 0/5 * * * ? ")
//    @Scheduled(cron = "0 0 3,14 * * ? ")
    public void executeDockingInvoiceAuto()
    {
        if (!dockingInvoiceAutoMonitor.isStartable())
        {
            return;
        }

        dockingInvoiceAutoMonitor.setStart(true);
        dockingInvoiceAutoMonitor.setFinish(false);

        try
        {
            if (log.isDebugEnabled())
            {
                log.debug("- - - - - - Start docking auto invoice to golden  - - - - - -");
            }

            NamedQueryer queryer =
                    NamedQueryer.builder().hql("FROM FinanceInvoiceTask t WHERE t.status = 2 and t.institution ='1' and t.category = '1' ").names(Lists.newArrayList()).values(Lists.newArrayList()).build();
            List<FinanceInvoiceTask> autoRecords = baseDaoSupport.find(queryer, FinanceInvoiceTask.class);

            if (Collections3.isNotEmpty(autoRecords))
            {
                if (log.isDebugEnabled())
                {
                    log.debug("Found {} autoRecords to docking invoice.", autoRecords.size());
                }

                autoRecords.parallelStream().forEach(x -> {
                    invoiceService.dockingInvoiceForAuto(x);
                });


            }
            else
            {
                if (log.isDebugEnabled())
                {
                    log.debug("Not found any autoRecords to docking golden invoice.");
                }
            }

            if (log.isDebugEnabled())
            {
                log.debug("- - - - - - Docking auto invoice tasks finished - - - - - -");
            }

            dockingInvoiceAutoMonitor.setFinish(true);
        }
        catch (Exception e)
        {
            log.error("Docking auto invoice tasks to golden error.", e);
            dockingInvoiceAutoMonitor.setFinish(true);
        }
    }

    @Scheduled(cron = "0 0/2 * * * ? ")
//    @Scheduled(cron = "0 0 4,14 * * ? ")
    public void executeUpdateInvoiceInfo()
    {
        if (!updateInvoiceInfoMonitor.isStartable())
        {
            return;
        }

        updateInvoiceInfoMonitor.setStart(true);
        updateInvoiceInfoMonitor.setFinish(false);

        try
        {
            if (log.isDebugEnabled())
            {
                log.debug("- - - - - - Start update invoice info by golden tax temporary - - - - - -");
            }

            NamedQueryer queryer =
                    NamedQueryer.builder().hql("FROM GoldenTemporary g WHERE 1 = 1 )  ").names(Lists.newArrayList()).values(Lists.newArrayList()).build();
            List<GoldenTemporary> records = baseDaoSupport.find(queryer, GoldenTemporary.class);

            if (Collections3.isNotEmpty(records))
            {
                if (log.isDebugEnabled())
                {
                    log.debug("Found {} records to docking invoice.", records.size());
                }

                records.parallelStream().forEach(x -> {
                    invoiceService.updateInvoiceInfoByTemporary(x);
                });

            }
            else
            {
                if (log.isDebugEnabled())
                {
                    log.debug("Not found any Records to update invoice info.");
                }
            }

            if (log.isDebugEnabled())
            {
                log.debug("- - - - - - update invoice info finished - - - - - -");
            }

            updateInvoiceInfoMonitor.setFinish(true);
        }
        catch (Exception e)
        {
            log.error("update invoice info by temporary error.", e);
            updateInvoiceInfoMonitor.setFinish(true);
        }
    }

}
