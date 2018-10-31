package com.todaysoft.lims.order.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.order.model.enums.MistakeHandleStatusEnum;
import com.todaysoft.lims.order.model.enums.ReconciliationMistakeTypeEnum;
import com.todaysoft.lims.order.model.enums.TranTypeEnum;
import com.todaysoft.lims.order.mybatis.mapper.OrderAccountStateRecordMapper;
import com.todaysoft.lims.order.mybatis.mapper.OrderPaymentConfirmMapper;
import com.todaysoft.lims.order.mybatis.model.AccountCheckMistake;
import com.todaysoft.lims.order.mybatis.model.AccountCheckMistakeScratchPool;
import com.todaysoft.lims.order.mybatis.model.OrderAccountCheckTask;
import com.todaysoft.lims.order.mybatis.model.OrderAccountStateRecord;
import com.todaysoft.lims.order.mybatis.model.OrderPaymentConfirm;
import com.todaysoft.lims.order.mybatis.model.OrderPaymentConfirmExample;
import com.todaysoft.lims.order.mybatis.parameter.OrderAccountStateRecordSearch;
import com.todaysoft.lims.order.service.IAccountCheckMistakeScratchPoolService;
import com.todaysoft.lims.order.service.IAccountCheckTransactionService;
import com.todaysoft.lims.order.service.IReconciliationService;
import com.todaysoft.lims.order.util.DateUtil;
import com.todaysoft.lims.order.util.IdGen;

@Service
public class ReconciliationServiceImpl implements IReconciliationService
{
    private static final Log LOG = LogFactory.getLog(ReconciliationServiceImpl.class);
    
    @Autowired
    private OrderAccountStateRecordMapper orderAccountStateRecordMapper;
    
    @Autowired
    private OrderPaymentConfirmMapper orderPaymentConfirmMapper;
    
    @Autowired
    private IAccountCheckMistakeScratchPoolService accountCheckMistakeScratchPoolService;
    
    @Autowired
    private IAccountCheckTransactionService accountCheckTransactionService;
    
    @Override
    public List<OrderAccountStateRecord> searchAccountStateRecord(OrderAccountStateRecordSearch searcher)
    {
        return orderAccountStateRecordMapper.search(searcher);
    }
    
    @Override
    public void reconciliation(List<OrderAccountStateRecord> bankList, int interfaceCode, OrderAccountCheckTask batch)
    {
        if (bankList == null)
        {
            bankList = new ArrayList<OrderAccountStateRecord>();
        }
        // 查询平台bill_date,interfaceCode成功的交易
        OrderPaymentConfirmExample example = new OrderPaymentConfirmExample();
        example.createCriteria().andPayTypeEqualTo(interfaceCode).andTranDateEqualTo(batch.getTradingDate());
        List<OrderPaymentConfirm> platSucessDateList = orderPaymentConfirmMapper.selectByExample(example);
        
        // 查询平台缓冲池中所有的数据
        List<AccountCheckMistakeScratchPool> platScreatchRecordList = accountCheckMistakeScratchPoolService.listScratchPoolRecord(null);
        
        // 差错list
        List<AccountCheckMistake> mistakeList = new ArrayList<AccountCheckMistake>();
        
        // 需要放入缓冲池中平台长款list
        List<AccountCheckMistakeScratchPool> insertScreatchRecordList = new ArrayList<AccountCheckMistakeScratchPool>();
        
        // 需要从缓冲池中移除的数据
        List<AccountCheckMistakeScratchPool> removeScreatchRecordList = new ArrayList<AccountCheckMistakeScratchPool>();
        
        List<OrderPaymentConfirm> updateSuccessRecordList = new ArrayList<OrderPaymentConfirm>();
        
        LOG.info("开始以平台的数据为准对账,平台长款记入缓冲池");
        baseOnPaltForm(platSucessDateList, bankList, mistakeList, insertScreatchRecordList, batch, updateSuccessRecordList);
        LOG.info("结束以平台的数据为准对账");
        
        LOG.info("开始以银行通道的数据为准对账");
        baseOnBank(platSucessDateList, bankList, platScreatchRecordList, mistakeList, batch, removeScreatchRecordList, updateSuccessRecordList);
        LOG.info("结束以银行通道的数据为准对账");
        
        // 保存数据
        accountCheckTransactionService.saveData(batch, mistakeList, insertScreatchRecordList, removeScreatchRecordList, updateSuccessRecordList);
    }
    
    /**
     * 以银行的数据为准对账
     * 
     * @param bankList
     *            银行对账单数据
     * 
     * @param misTakeList
     *            差错list
     * 
     * @param platScreatchRecordList
     *            平台缓冲池中的数据
     * 
     * @param batch
     *            对账批次
     * @param updateSuccessRecordList 
     */
    private void baseOnBank(List<OrderPaymentConfirm> platAllDateList, List<OrderAccountStateRecord> bankList, List<AccountCheckMistakeScratchPool> platScreatchRecordList, List<AccountCheckMistake> misTakeList, OrderAccountCheckTask batch, List<AccountCheckMistakeScratchPool> removeScreatchRecordList, List<OrderPaymentConfirm> updateSuccessRecordList)
    {
        BigDecimal platTradeAmount = BigDecimal.ZERO;// 平台交易总金额
        BigDecimal platFee = BigDecimal.ZERO;// 平台总手续费
        Integer tradeCount = 0;// 平台订单总数
        Integer mistakeCount = 0;
        // 拿银行数据去对账
        for (OrderAccountStateRecord bankRecord : bankList)
        {
            
            boolean flag = false;// 用于标记是否有匹配
            for (OrderPaymentConfirm record : platAllDateList)
            {
                /** !!! 修改 step1 根据 商户号+交易参考号+交易时间+交易类型   检查有匹配的数据 **/
                if (checkMatch(record, bankRecord))
                {
                    flag = true;
                }
                
            }
            
            /** step2： 如果没有匹配的数据，去缓冲池中查找对账，如果没有记录差错 **/
            if (!flag)
            {
                // 去缓冲池中查找对账(前提是缓冲池里面有数据)
                if (platScreatchRecordList != null)
                    for (AccountCheckMistakeScratchPool scratchRecord : platScreatchRecordList)
                    {
                        // 找到匹配的  *****
                        if (checkMatch(scratchRecord, bankRecord))
                        {
                            // 累计平台交易总金额和总手续费
                            platTradeAmount = platTradeAmount.add(scratchRecord.getCheckAmount());
                            tradeCount++;
                            flag = true;
                            
                            // 验证金额和手续费
                            /** step1:匹配订单金额 **/
                            // 平台金额多
                            if (scratchRecord.getCheckAmount().compareTo(bankRecord.getTradingAmount()) == 1)
                            {
                                // 金额不匹配，创建差错记录
                                AccountCheckMistake misktake =
                                    createMisktake(scratchRecord, null, bankRecord, ReconciliationMistakeTypeEnum.PLATFORM_OVER_CASH_MISMATCH, batch);
                                misTakeList.add(misktake);
                                mistakeCount++;
                                break;
                            }
                            // 平台金额少
                            else if (scratchRecord.getCheckAmount().compareTo(bankRecord.getTradingAmount()) == -1)
                            {
                                // 金额不匹配，创建差错记录
                                AccountCheckMistake misktake =
                                    createMisktake(scratchRecord, null, bankRecord, ReconciliationMistakeTypeEnum.PLATFORM_SHORT_CASH_MISMATCH, batch);
                                misTakeList.add(misktake);
                                mistakeCount++;
                                break;
                            }
                            
                            /** step3:把缓存池中匹配的记录删除掉 **/
                            removeScreatchRecordList.add(scratchRecord);
                            
                        }
                    }
            }
            
            // 缓冲池中还是没有这条记录,直接记录差错，差错类型为 PLATFORM_MISS("平台漏单")
            if (!flag)
            {
                AccountCheckMistake misktake = createMisktake(null, null, bankRecord, ReconciliationMistakeTypeEnum.PLATFORM_MISS, batch);
                misTakeList.add(misktake);
                mistakeCount++;
            }
        }
        
        // 统计数据保存
        // batch.setTradeAmount(batch.getTradeAmount().add(platTradeAmount));
        // batch.setTradeCount(batch.getTradeCount() + tradeCount);
        // batch.setMistakeCount(batch.getMistakeCount() + mistakeCount);
        if (!mistakeCount.equals(0)) //漏单的情况，忽略 错误数量为0 并且交易数0
        {
            batch.setReconciliationResult(0); //对账错误
        }
        
    }
    
    private boolean checkMatch(AccountCheckMistakeScratchPool scratchRecord, OrderAccountStateRecord bankRecord)
    {
        StringBuffer scratchInfo = new StringBuffer();
        StringBuffer bankInfo = new StringBuffer();
        scratchInfo.append(scratchRecord.getMerNum())
            .append(scratchRecord.getReferNo())
            .append(scratchRecord.getTranDate())
            .append(scratchRecord.getTranType());
        bankInfo.append(bankRecord.getMerNum())
            .append(bankRecord.getReferNo())
            .append(bankRecord.getTransactionDate())
            .append(TranTypeEnum.getHIntFromValue(bankRecord.getTradingType()));
        return scratchInfo.toString().equals(bankInfo.toString());
    }
    
    /**
     * 以平台的数据为准对账
     * 
     * @param platformDateList
     *            平台dilldate的成功数据
     * @param bankList
     *            银行成功对账单数据
     * 
     * @param misTakeList
     *            差错list
     * @param screatchRecordList
     *            需要放入缓冲池中平台长款list
     * 
     * @param batch
     *            对账批次
     * @param updateSuccessRecordList 
     */
    private void baseOnPaltForm(List<OrderPaymentConfirm> platformDateList, List<OrderAccountStateRecord> bankList, List<AccountCheckMistake> misTakeList, List<AccountCheckMistakeScratchPool> screatchRecordList, OrderAccountCheckTask batch, List<OrderPaymentConfirm> updateSuccessRecordList)
    {
        BigDecimal platTradeAmount = BigDecimal.ZERO;// 平台交易总金额
        BigDecimal platFee = BigDecimal.ZERO;// 平台总手续费
        Integer tradeCount = 0;// 平台订单总数
        Integer mistakeCount = 0;
        
        for (OrderPaymentConfirm record : platformDateList)
        {
            Boolean flag = false;// 用于标记是否有匹配
            // 累计平台交易总金额和总手续费
            platTradeAmount = platTradeAmount.add(record.getCheckAmount());
            tradeCount++;
            for (OrderAccountStateRecord bankRecord : bankList)
            {
                // 如果银行账单中有匹配数据：进行金额，手续费校验
                if (checkMatch(record, bankRecord))
                {
                    flag = true;// 标记已经找到匹配
                    
                    /** step1:匹配订单金额 **/
                    // 平台金额多
                    if (record.getCheckAmount().compareTo(bankRecord.getTradingAmount()) == 1)
                    {
                        // 金额不匹配，创建差错记录
                        AccountCheckMistake misktake =
                            createMisktake(null, record, bankRecord, ReconciliationMistakeTypeEnum.PLATFORM_OVER_CASH_MISMATCH, batch);
                        misTakeList.add(misktake);
                        mistakeCount++;
                        break;
                    }
                    // 平台金额少
                    else if (record.getCheckAmount().compareTo(bankRecord.getTradingAmount()) == -1)
                    {
                        // 金额不匹配，创建差错记录
                        AccountCheckMistake misktake =
                            createMisktake(null, record, bankRecord, ReconciliationMistakeTypeEnum.PLATFORM_SHORT_CASH_MISMATCH, batch);
                        misTakeList.add(misktake);
                        mistakeCount++;
                        break;
                    }
                    //对账正确的记录
                    updateSuccessRecordList.add(record);
                }
            }
            // 没有找到匹配的记录，把这个订单记录到缓冲池中 基本不会出现
            if (!flag)
            {
                AccountCheckMistakeScratchPool screatchRecord = getScratchRecord(record, batch);
                screatchRecordList.add(screatchRecord);
            }
        }
        
        // 统计数据保存
        // batch.setTradeAmount(platTradeAmount);
        // batch.setTradeCount(tradeCount);
        // batch.setMistakeCount(mistakeCount);
        if (mistakeCount.equals(0) && screatchRecordList.size() == 0) //错误数量为0 并且未留到缓冲池 为正确
        {
            batch.setReconciliationResult(1); //对账正确
            batch.setReconciliationStatus(1); //已对账
        }
    }
    
    private boolean checkMatch(OrderPaymentConfirm record, OrderAccountStateRecord bankRecord)
    {
        StringBuffer platInfo = new StringBuffer();
        StringBuffer bankInfo = new StringBuffer();
        platInfo.append(record.getMerNum()).append(record.getReferNo()).append(record.getTranDate()).append(record.getTranType());
        bankInfo.append(bankRecord.getMerNum())
            .append(bankRecord.getReferNo())
            .append(bankRecord.getTransactionDate())
            .append(TranTypeEnum.getHIntFromValue(bankRecord.getTradingType()));
        return platInfo.toString().equals(bankInfo.toString());
    }
    
    /**
     * 创建差错记录
     * 
     * @param scratchRecord
     *            平台缓冲池中的订单记录
     * @param record
     *            平台订单记录
     * @param bankRecord
     *            银行账单记录
     * @param mistakeType
     *            差错类型
     * @return 注意：scratchRecord和record 至少有一个为空
     */
    private AccountCheckMistake createMisktake(AccountCheckMistakeScratchPool scratchRecord, OrderPaymentConfirm record, OrderAccountStateRecord bankRecord, ReconciliationMistakeTypeEnum mistakeType, OrderAccountCheckTask batch)
    {
        
        AccountCheckMistake mistake = new AccountCheckMistake();
        mistake.setAccountCheckTaskId(batch.getId());
        mistake.setBillDate(batch.getReconciliationDate());
        mistake.setErrType(mistakeType.getDesc());
        mistake.setHandleStatus(MistakeHandleStatusEnum.NOHANDLE.name());
        mistake.setBankType(batch.getInterfaceCode().toString());
        mistake.setCreateTime(new Date());
        if (record != null)
        {
            mistake.setOrderNo(record.getOrderCode());
            mistake.setTradeTime(record.getTranDate());
            mistake.setTrxNo(record.getReferNo());
            mistake.setOrderAmount(record.getCheckAmount());
            mistake.setPaymentFullTime(record.getPaymentTime());
        }
        
        if (scratchRecord != null)
        {
            mistake.setOrderNo(scratchRecord.getOrderNo());
            mistake.setTradeTime(scratchRecord.getTranDate());
            mistake.setTrxNo(scratchRecord.getReferNo());
            mistake.setOrderAmount(scratchRecord.getCheckAmount());
            mistake.setPaymentFullTime(scratchRecord.getPaymentTime());
        }
        
        if (bankRecord != null)
        {
            mistake.setBankOrderNo(bankRecord.getOrderId()); //这里是订单编号
            mistake.setBankTradeTime(bankRecord.getTransactionDate());
            mistake.setBankTrxNo(bankRecord.getReferNo());
            mistake.setBankAmount(bankRecord.getTradingAmount());
            mistake.setBankPaymentFullTime(DateUtil.parseString2Date(bankRecord.getTransactionDate() + bankRecord.getTradingTime()));
        }
        return mistake;
        
    }
    
    /**
     * 得到缓存记录：用于放入缓冲池
     * 
     * @param record
     *           平台 支付记录
     * @param batch
     *            对账任务
     * @return
     */
    //确定一比交易最少元素 platInfo.append(record.getMerNum()).append(record.getReferNo()).append(record.getTranDate()).append(record.getTranType());
    private AccountCheckMistakeScratchPool getScratchRecord(OrderPaymentConfirm record, OrderAccountCheckTask batch)
    {
        AccountCheckMistakeScratchPool scratchRecord = new AccountCheckMistakeScratchPool();
        BeanUtils.copyProperties(record, scratchRecord);
        scratchRecord.setId(IdGen.uuid());
        scratchRecord.setCheckTaskId(batch.getId());
        scratchRecord.setCreateTime(new Date());
        scratchRecord.setOrderNo(record.getOrderCode());
        scratchRecord.setBillDate(batch.getReconciliationDate());
        return scratchRecord;
    }
    
}
