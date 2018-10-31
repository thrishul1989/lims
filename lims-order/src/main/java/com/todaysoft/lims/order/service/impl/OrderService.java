package com.todaysoft.lims.order.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.todaysoft.lims.order.model.AccountBalanceModel;
import com.todaysoft.lims.order.model.BasicSampleModel;
import com.todaysoft.lims.order.model.BasicSampleRequest;
import com.todaysoft.lims.order.model.CallBackSampleModel;
import com.todaysoft.lims.order.model.reconciliation.AccountStatementRequest;
import com.todaysoft.lims.order.mybatis.mapper.OrderAccountStateRecordMapper;
import com.todaysoft.lims.order.mybatis.mapper.OrderMapper;
import com.todaysoft.lims.order.mybatis.mapper.OrderScheduleMapper;
import com.todaysoft.lims.order.mybatis.mapper.SampleMapper;
import com.todaysoft.lims.order.mybatis.mapper.TestingReportMapper;
import com.todaysoft.lims.order.mybatis.model.CommonOrderModel;
import com.todaysoft.lims.order.mybatis.model.OrderAccountStateRecord;
import com.todaysoft.lims.order.mybatis.model.OrderScheduleModel;
import com.todaysoft.lims.order.mybatis.model.PoolingSheetVariables;
import com.todaysoft.lims.order.mybatis.model.PrimarySample;
import com.todaysoft.lims.order.mybatis.model.ScheduleMonitorOrderModel;
import com.todaysoft.lims.order.mybatis.model.TestingMonitorOrderModel;
import com.todaysoft.lims.order.mybatis.model.TestingReport;
import com.todaysoft.lims.order.mybatis.parameter.OrderSearcher;
import com.todaysoft.lims.order.mybatis.parameter.ScheduleMonitorOrderSearcher;
import com.todaysoft.lims.order.mybatis.parameter.TestingMonitorOrderSearcher;
import com.todaysoft.lims.order.request.MaintainReportRequest;
import com.todaysoft.lims.order.request.OrderSearchCommonRequest;
import com.todaysoft.lims.order.request.OrderSearchRequest;
import com.todaysoft.lims.order.request.OrderStatusScheduleRequest;
import com.todaysoft.lims.order.request.SampleSynchronizeRequest;
import com.todaysoft.lims.order.request.TestingReportModel;
import com.todaysoft.lims.order.response.CommonOrderResponse;
import com.todaysoft.lims.order.response.GanalysisSampleInfo;
import com.todaysoft.lims.order.response.OrderSearchResponse;
import com.todaysoft.lims.order.response.ReceiveSampleOrderResponse;
import com.todaysoft.lims.order.response.SampleKeyword;
import com.todaysoft.lims.order.response.ScheduleMonitorOrder;
import com.todaysoft.lims.order.response.TestingMonitorOrder;
import com.todaysoft.lims.order.service.IOrderService;
import com.todaysoft.lims.order.util.DateUtil;
import com.todaysoft.lims.order.util.IdGen;
import com.todaysoft.lims.order.util.ThreeDes;
import com.todaysoft.lims.order.utils.FamilyRelationSex;
import com.todaysoft.lims.order.utils.JsonUtils;

@Service
public class OrderService implements IOrderService
{
    @Autowired
    private OrderMapper mapper;
    
    @Autowired
    private OrderScheduleMapper orderScheduleMapper;
    
    @Autowired
    private SampleMapper sampleMapper;
    
    @Autowired
    private TestingReportMapper testingReportMapper;
    
    private static final Log LOG = LogFactory.getLog(ReconciliationServiceImpl.class);
    
    @Override
    public OrderSearchResponse<ScheduleMonitorOrder> searchScheduleMonitorOrders(OrderSearchRequest request)
    {
        OrderSearchRequestAdapter adapter = new OrderSearchRequestAdapter(request);
        ScheduleMonitorOrderSearcher searcher = adapter.adapt(ScheduleMonitorOrderSearcher.class);
        
        if (searcher.isFilterAsEmptyResultSet())
        {
            return OrderSearchResponse.empty(request.getPageNo(), request.getPageSize());
        }
        
        int count = mapper.countForScheduleMonitorOrderSearcher(searcher);
        
        List<ScheduleMonitorOrder> orders;
        
        if (count > 0)
        {
            Integer pageNo = request.getPageNo();
            Integer pageSize = request.getPageSize();
            
            if (null != pageNo && null != pageSize)
            {
                
                int minPageNo = 1;
                int maxPageNo = count / pageSize;
                
                if (maxPageNo == 0 || count % pageSize != 0)
                {
                    maxPageNo++;
                }
                
                pageNo = pageNo < minPageNo ? minPageNo : pageNo;
                pageNo = pageNo > maxPageNo ? maxPageNo : pageNo;
                
                int offset = (pageNo - 1) * pageSize;
                searcher.setOffset(offset);
                searcher.setLimit(request.getPageSize());
            }
            
            List<ScheduleMonitorOrderModel> records = mapper.searchScheduleMonitorOrders(searcher);
            
            ScheduleMonitorOrder order;
            orders = new ArrayList<ScheduleMonitorOrder>();
            
            for (ScheduleMonitorOrderModel record : records)
            {
                order = new ScheduleMonitorOrder();
                BeanUtils.copyProperties(record, order);
                orders.add(order);
            }
        }
        else
        {
            orders = Collections.emptyList();
        }
        
        return OrderSearchResponse.generate(request.getPageNo(), request.getPageSize(), count, orders);
    }
    
    @Override
    public OrderSearchResponse<TestingMonitorOrder> searchTestingMonitorOrders(OrderSearchRequest request)
    {
        OrderSearchRequestAdapter adapter = new OrderSearchRequestAdapter(request);
        TestingMonitorOrderSearcher searcher = adapter.adapt(TestingMonitorOrderSearcher.class);
        
        if (searcher.isFilterAsEmptyResultSet())
        {
            return OrderSearchResponse.empty(request.getPageNo(), request.getPageSize());
        }
        
        int count = mapper.countForTestingMonitorOrderSearcher(searcher);
        
        List<TestingMonitorOrder> orders;
        
        if (count > 0)
        {
            Integer pageNo = request.getPageNo();
            Integer pageSize = request.getPageSize();
            
            if (null != pageNo && null != pageSize)
            {
                
                int minPageNo = 1;
                int maxPageNo = count / pageSize;
                
                if (maxPageNo == 0 || count % pageSize != 0)
                {
                    maxPageNo++;
                }
                
                pageNo = pageNo < minPageNo ? minPageNo : pageNo;
                pageNo = pageNo > maxPageNo ? maxPageNo : pageNo;
                
                int offset = (pageNo - 1) * pageSize;
                searcher.setOffset(offset);
                searcher.setLimit(request.getPageSize());
            }
            
            List<TestingMonitorOrderModel> records = mapper.searchTestingMonitorOrders(searcher);
            
            TestingMonitorOrder order;
            orders = new ArrayList<TestingMonitorOrder>();
            
            for (TestingMonitorOrderModel record : records)
            {
                order = new TestingMonitorOrder();
                BeanUtils.copyProperties(record, order);
                orders.add(order);
            }
        }
        else
        {
            orders = Collections.emptyList();
        }
        
        return OrderSearchResponse.generate(request.getPageNo(), request.getPageSize(), count, orders);
    }
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    @Override
    public OrderSearchResponse<CommonOrderResponse> searchOrders(OrderSearchCommonRequest request)
    {
        OrderSearcher searcher = adapt(request);
        
        int count = mapper.countForOrderSearcher(searcher);
        
        List<CommonOrderResponse> orders;
        
        if (count > 0)
        {
            Integer pageNo = request.getPageNo();
            Integer pageSize = request.getPageSize();
            
            if (null != pageNo && null != pageSize)
            {
                
                int minPageNo = 1;
                int maxPageNo = count / pageSize;
                
                if (maxPageNo == 0 || count % pageSize != 0)
                {
                    maxPageNo++;
                }
                
                pageNo = pageNo < minPageNo ? minPageNo : pageNo;
                pageNo = pageNo > maxPageNo ? maxPageNo : pageNo;
                
                int offset = (pageNo - 1) * pageSize;
                searcher.setOffset(offset);
                searcher.setLimit(request.getPageSize());
            }
            
            CommonOrderResponse order;
            orders = new ArrayList<CommonOrderResponse>();
            
            List<CommonOrderModel> records = mapper.searchOrderList(searcher);
            
            for (CommonOrderModel record : records)
            {
                order = new CommonOrderResponse();
                BeanUtils.copyProperties(record, order);
                order.setCreatorName(record.getSalesmanName());
                order.setOrderType(record.getOrderMarketingCenterId());
                order.setTestingStatus(record.getOrderStatus());
                orders.add(order);
            }
        }
        else
        {
            orders = Collections.emptyList();
        }
        
        return OrderSearchResponse.generate(request.getPageNo(), request.getPageSize(), count, orders);
    }
    
    private OrderSearcher adapt(OrderSearchCommonRequest request)
    {
        OrderSearcher searcher = new OrderSearcher();
        BeanUtils.copyProperties(request, searcher);
        searcher.setOrderCode(request.getCode());
        searcher.setMarketingCenterId(request.getOrderType());
        searcher.setSalesmanName(request.getCreatorName());
        if (searcher.getOrderByClause() == null || "".equals(searcher.getOrderByClause()))
        {
            searcher.setOrderByClause("o.SUBMIT_TIME DESC ");
        }
        try
        {
            if (StringUtils.isNotBlank(request.getStart()))
            {
                searcher.setSubmitStartDate(DateUtil.toStartDate(sdf.parse(request.getStart())));
            }
            if (StringUtils.isNotBlank(request.getEnd()))
            {
                searcher.setSubmitEndDate(DateUtil.toEndDate(sdf.parse(request.getEnd())));
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return searcher;
    }
    
    @Override
    public Map<String, List<OrderScheduleModel>> searchTestingOrderStatusByOrderId(OrderStatusScheduleRequest request)
    {
        List<OrderScheduleModel> list;
        if ("4".equals(request.getOrderType()))
        {
            list = orderScheduleMapper.searchTestingOrderStatusByTecOrderId(request.getOrderId(), request.getProductId());
        }
        else
        {
            list = orderScheduleMapper.searchTestingOrderStatusByOrderId(request.getOrderId(), request.getProductId());
        }
        Map<String, List<OrderScheduleModel>> map = new HashMap<String, List<OrderScheduleModel>>();
        Set<String> productNameSet = new HashSet<String>();
        for (OrderScheduleModel record : list)
        {
            if (null != record.getPoolingJsons() && "" != record.getPoolingJsons())
            {
                String lanes = "";
                List<String> sequenceCodes = Lists.newArrayList();
                String[] jsonlans = record.getPoolingJsons().split("/");
                if (jsonlans.length > 0)
                {
                    for (String lane : jsonlans)
                    {
                        PoolingSheetVariables variables = JsonUtils.asObject(lane, PoolingSheetVariables.class);
                        if (null != variables)
                        {
                            if (null != variables.getPoolingCode() && "" != variables.getPoolingCode())
                            {
                                if (!sequenceCodes.contains(variables.getPoolingCode()))
                                {
                                    sequenceCodes.add(variables.getPoolingCode());
                                }
                            }
                        }
                    }
                }
                for (String s : sequenceCodes)
                {
                    if (null == lanes || "" == lanes)
                    {
                        lanes = s;
                    }
                    else
                    {
                        if (null != s && "" != s)
                        {
                            lanes += "," + s;
                        }
                    }
                }
                record.setPoolingJsons(lanes);
            }
            //样本编号为空 2017.12.27
            if (null == record.getSampleCode() || "" == record.getSampleCode())
            {
                String code = orderScheduleMapper.searchCodeByScheduleId(record.getScheduleId());
                record.setSampleCode(code);
            }
            productNameSet.add(record.getProductName());
        }
        
        for (String s : productNameSet)
        {
            List<OrderScheduleModel> testinglist = Lists.newArrayList();
            List<OrderScheduleModel> verifylist = Lists.newArrayList();
            List<OrderScheduleModel> verifyChrNulllist = Lists.newArrayList();
            for (OrderScheduleModel record : list)
            {
                if (null != record.getScheduleId() && "" != record.getScheduleId())
                {
                    if (s.equals(record.getProductName()))
                    {
                        if (StringUtils.isNotEmpty(record.getVerifyKey()) || "1".equals(record.getSamplePurpose()))//后者是验证类型的样本，有可能先启动
                        {
                            if (null != record.getChromosomeLocation() && "" != record.getChromosomeLocation())
                            {
                                verifylist.add(record);
                            }
                            else
                            {
                                verifyChrNulllist.add(record);
                            }
                        }
                        else
                        {
                            testinglist.add(record);
                        }
                    }
                }
            }
            verifylist.sort(Comparator.comparing(OrderScheduleModel::getMethodName)
                .thenComparing(OrderScheduleModel::getChromosomeLocation)
                .thenComparing(OrderScheduleModel::getSampleName));
            verifylist.addAll(verifyChrNulllist);
            map.put(s + "_testing", testinglist);
            map.put(s + "_verify", verifylist);
        }
        return map;
    }
    
    @Override
    public OrderSearchResponse<ReceiveSampleOrderResponse> searchReceiveSample(OrderSearchCommonRequest request)
    {
        
        OrderSearcher searcher = new OrderSearcher();
        BeanUtils.copyProperties(request, searcher);
        searcher.setMarketingCenterId(request.getOrderType());
        searcher.setSalesmanName(request.getCreatorName());
        try
        {
            if (StringUtils.isNotBlank(request.getStart()))
            {
                searcher.setSubmitStartDate(DateUtil.toStartDate(sdf.parse(request.getStart())));
            }
            if (StringUtils.isNotBlank(request.getEnd()))
            {
                searcher.setSubmitEndDate(DateUtil.toEndDate(sdf.parse(request.getEnd())));
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        
        int count = mapper.countForReceiveSample(searcher);
        
        List<ReceiveSampleOrderResponse> orders;
        
        if (count > 0)
        {
            Integer pageNo = request.getPageNo();
            Integer pageSize = request.getPageSize();
            
            if (null != pageNo && null != pageSize)
            {
                
                int minPageNo = 1;
                int maxPageNo = count / pageSize;
                
                if (maxPageNo == 0 || count % pageSize != 0)
                {
                    maxPageNo++;
                }
                
                pageNo = pageNo < minPageNo ? minPageNo : pageNo;
                pageNo = pageNo > maxPageNo ? maxPageNo : pageNo;
                
                int offset = (pageNo - 1) * pageSize;
                searcher.setOffset(offset);
                searcher.setLimit(request.getPageSize());
            }
            
            orders = mapper.searchReceiveSampleList(searcher);
            
        }
        else
        {
            orders = Collections.emptyList();
        }
        
        return OrderSearchResponse.generate(request.getPageNo(), request.getPageSize(), count, orders);
    }
    
    @Override
    public OrderSearchResponse<GanalysisSampleInfo> searchSamplesByCustomerId(SampleSynchronizeRequest request)
    {
        
        int count = sampleMapper.countForSample(request);
        List<GanalysisSampleInfo> orders;
        
        if (count > 0)
        {
            Integer pageNo = request.getPageNo();
            Integer pageSize = request.getPageSize();
            
            if (null != pageNo && null != pageSize)
            {
                int minPageNo = 1;
                int maxPageNo = count / pageSize;
                
                if (maxPageNo == 0 || count % pageSize != 0)
                {
                    maxPageNo++;
                }
                pageNo = pageNo < minPageNo ? minPageNo : pageNo;
                pageNo = pageNo > maxPageNo ? maxPageNo : pageNo;
                int offset = (pageNo - 1) * pageSize;
                request.setOffset(offset);
                request.setLimit(request.getPageSize());
            }
            orders = sampleMapper.searchSampleList(request);
            SampleSynchronizeRequest searcher = new SampleSynchronizeRequest();
            
            for (GanalysisSampleInfo o : orders)
            {
                o.setCreateSource(1); //lims同步
                searcher.setOrderId(o.getOrderId());
                List<SampleKeyword> d = sampleMapper.searchDisease(searcher);
                
                o.setSampleDisease(d);
                
                List<SampleKeyword> g = sampleMapper.searchGene(searcher);
                
                o.setSampleGene(g);
                
                List<SampleKeyword> p = sampleMapper.searchPhenotype(searcher);
                
                o.setSamplePhenotype(p);
                
                if (o.getBusinessType().equals(2))
                {
                    List<PrimarySample> primarySample = sampleMapper.searchPrimarySample(searcher);
                    if (primarySample != null && primarySample.size() > 0)
                    {
                        o.setPrimarySampleCode(primarySample.get(0).getCode());
                        o.setPrimarySampleId(primarySample.get(0).getId());
                    }
                    if (!o.getFamilyRelation().equals("1")) //非本人，本人则是取受检人性别
                    {
                        o.setExamineeSex(FamilyRelationSex.map.get(o.getFamilyRelation()));
                    }
                    
                }
                
            }
        }
        else
        {
            orders = Collections.emptyList();
        }
        
        return OrderSearchResponse.generate(request.getPageNo(), request.getPageSize(), count, orders);
    }
    
    @Override
    @Transactional
    public void updateLimsSampleStatus(CallBackSampleModel request)
    {
        sampleMapper.updateLimsSampleStatus1(request);
        sampleMapper.updateLimsSampleStatus2(request);
        sampleMapper.updateLimsSampleStatus3(request);
    }
    
    @Override
    public List<TestingReportModel> synchronizeReport(MaintainReportRequest request)
    {
        return warp(testingReportMapper.search(request));
    }
    
    private List<TestingReportModel> warp(List<TestingReport> record)
    {
        List<TestingReportModel> model = new ArrayList<TestingReportModel>();
        if (record != null && record.size() > 0)
        {
            TestingReportModel m = null;
            for (TestingReport t : record)
            {
                m = new TestingReportModel();
                BeanUtils.copyProperties(t, m);
                model.add(m);
            }
        }
        return model;
    }
    
    @Override
    public void updateLimsReportStatus(CallBackSampleModel request)
    {
        testingReportMapper.updateLimsReportStatus(request);
    }
    
    @Override
    public List<BasicSampleModel> seacherBasicSample(BasicSampleRequest request)
    {
        return sampleMapper.seacherBasicSample(request);
    }
    
    private static final String key = "S5U2N5I6T7SC#L*7$0M9NGWT";
    
    @Autowired
    private OrderAccountStateRecordMapper orderAccountStateRecordMapper;
    
    @Override
    @Transactional
    public Map<String, Object> resolveAccountStatement(AccountStatementRequest request)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String data = request.getData();
        OrderAccountStateRecord record = null;
        try
        {
            System.out.println("密文为：" + data);
            ThreeDes des = new ThreeDes(); // 实例化一个对像
            des.getKey();
            String decryptData = des.getDesString(data);
            if (StringUtils.isEmpty(decryptData))
            {
                result.put("code", "01");
                result.put("message", "参数为空");
                return result;
            }
            AccountBalanceModel model = JsonUtils.asObject(decryptData, AccountBalanceModel.class);
            if (null == model)
            {
                result.put("code", "01");
                result.put("message", "缺少参数,转化对象失败");
                return result;
            }
            for (String s : model.getData())
            {
                String[] source = s.split("\\|", 20);
                record = new OrderAccountStateRecord();
                if (copyData(record, source))
                {
                    //判断是否重复 保存数据库
                    int count =
                        orderAccountStateRecordMapper.countByCondition(record.getMerNum(), record.getTermId(), record.getReferNo(), record.getTradingType());
                    if (count < 1)
                    {
                        orderAccountStateRecordMapper.insertSelective(record);
                        LOG.info("解析成功,入库" + record);
                    }
                }
                else
                {
                    result.put("code", "01");
                    result.put("message", "data数据长度有误，不能转化为数组");
                    return result;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result.put("code", "01");
            result.put("message", "解析异常");
            return result;
        }
        result.put("code", "00");
        result.put("message", "成功");
        LOG.info("解析成功,SUCCESS OVER");
        return result;
    }
    
    SimpleDateFormat sdformate = new SimpleDateFormat("yyyyMMdd");
    
    private boolean copyData(OrderAccountStateRecord record, String[] source)
    {
        if (source != null && source.length == 20)
        {
            record.setId(IdGen.uuid());
            record.setSeqNo(source[0]);
            record.setMerNum(source[1]);
            record.setMerName(source[2]);
            record.setTermId(source[3]);
            record.setSettlementDate(source[4].replace("-", ""));
            record.setTransactionDate(source[5].replace("-", ""));
            record.setTradingTime(source[6]);
            record.setOrderId(source[7]);
            record.setTradingType(source[8]);
            record.setTradingAmount(multiply(new BigDecimal(source[9])));
            record.setServiceCharge(multiply(new BigDecimal(source[10])));
            record.setEnterAmount(multiply(new BigDecimal(source[11])));
            record.setCardNumber(source[12]);
            record.setCardDomain(source[13]);
            record.setCardSpecies(source[14]);
            record.setReferNo(source[15]);
            record.setoReferNo(source[16]);
            record.setoTradingTime(source[17]);
            record.setPayType(source[18]);
            record.setBankCode(source[19]);
            return true;
        }
        else
        {
            return false;
        }
        
    }
    
    private BigDecimal multiply(BigDecimal amount)
    {
        return amount.multiply(new BigDecimal(100));
    }
}
