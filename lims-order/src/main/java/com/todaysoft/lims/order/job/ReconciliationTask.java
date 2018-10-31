package com.todaysoft.lims.order.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.order.model.reconciliation.ReconciliationInterface;
import com.todaysoft.lims.order.mybatis.model.OrderAccountCheckTask;
import com.todaysoft.lims.order.mybatis.model.OrderAccountStateRecord;
import com.todaysoft.lims.order.mybatis.parameter.OrderAccountStateRecordSearch;
import com.todaysoft.lims.order.service.IAccountCheckMistakeScratchPoolService;
import com.todaysoft.lims.order.service.IAccountCheckTaskService;
import com.todaysoft.lims.order.service.IReconciliationService;
import com.todaysoft.lims.order.util.DateUtil;
import com.todaysoft.lims.order.util.IdGen;

@Component
public class ReconciliationTask
{
    private static final Log LOG = LogFactory.getLog(ReconciliationTask.class);
    
    @Autowired
    private IReconciliationService service;
    
    @Autowired
    private IAccountCheckTaskService accountCheckTaskService;
    
    @Autowired
    private IAccountCheckMistakeScratchPoolService accountCheckMistakeScratchPoolService;
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    
    //@Scheduled(cron = "0 0/3 * * * ? ")
    //生产环境改成 接口出发调用
    public void executeTask()
    {
        
        try
        {
            List<?> reconciliationInterList = ReconciliationInterface.getInterface();
            // 获取业务biz实体
            
            for (int num = 0; num < reconciliationInterList.size(); num++)
            {
                ReconciliationInterface reconciliationInter = (ReconciliationInterface)reconciliationInterList.get(num);
                if (reconciliationInter == null)
                {
                    LOG.info("对账接口信息" + reconciliationInter + "为空");
                    continue;
                }
                // 获取需要对账的对账单时间
                // 获取对账渠道
                int interfaceCode = reconciliationInter.getInterfaceCode();
                
                /** step1:判断是否对过账 **/
                
                String billdate = sdf.format(DateUtil.addDay(new Date(), -1));
                OrderAccountCheckTask batch = accountCheckTaskService.selectByExample(interfaceCode, billdate);
                if (batch == null)
                {
                    // 创建对账任务
                    batch = makeBasic(interfaceCode, billdate);
                }
                
                /** step2:查询王府井对账数据 **/
                OrderAccountStateRecordSearch searcher = new OrderAccountStateRecordSearch();
                searcher.setTransactionDate(billdate);
                List<OrderAccountStateRecord> bankList = service.searchAccountStateRecord(searcher);
                
                /** step3:对账流程 **/
                try
                {
                    service.reconciliation(bankList, interfaceCode, batch);
                }
                catch (Exception e)
                {
                    LOG.error("对账异常:", e);
                    accountCheckTaskService.saveOrUpdateData(batch);
                    continue;
                }
                
                /** step4:清理缓冲池 **/
                // 如果缓冲池中有数据就清理掉并记录差错
                accountCheckMistakeScratchPoolService.validateScratchPool();
            }
        }
        catch (Exception e)
        {
            LOG.error("对账异常 reconciliation error:", e);
        }
        
    }
    
    private OrderAccountCheckTask makeBasic(int interfaceCode, String billdate)
    {
        OrderAccountCheckTask batch = new OrderAccountCheckTask();
        batch.setId(IdGen.uuid());
        batch.setInterfaceCode(interfaceCode);
        batch.setReconciliationDate(sdf.format(new Date()));
        batch.setReconciliationResult(0);
        batch.setReconciliationStatus(1);
        batch.setSettleStatus(1);
        batch.setSolveResult(0);
        batch.setTradingDate(billdate);
        return batch;
    }
}
