package com.todaysoft.lims.sample.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.dao.searcher.SampleOrderSearcher;
import com.todaysoft.lims.sample.dao.searcher.SampleReceiveDetailSearcher;
import com.todaysoft.lims.sample.dao.searcher.SampleReceiveSearcher;
import com.todaysoft.lims.sample.entity.Product;
import com.todaysoft.lims.sample.entity.Project;
import com.todaysoft.lims.sample.entity.Sample;
import com.todaysoft.lims.sample.entity.SampleChangeRecord;
import com.todaysoft.lims.sample.entity.SampleOrder;
import com.todaysoft.lims.sample.entity.SampleReceive;
import com.todaysoft.lims.sample.entity.SampleReceiveDetail;
import com.todaysoft.lims.sample.entity.SampleTracing;
import com.todaysoft.lims.sample.entity.StorageLocation;
import com.todaysoft.lims.sample.entity.User;
import com.todaysoft.lims.sample.model.CommitChargeRequest;
import com.todaysoft.lims.sample.model.UserDetailsModel;
import com.todaysoft.lims.sample.model.VariableUtils;
import com.todaysoft.lims.sample.model.request.OrderListRequest;
import com.todaysoft.lims.sample.model.request.SampleFormRequest;
import com.todaysoft.lims.sample.model.request.SampleReceiveDetailPagingRequest;
import com.todaysoft.lims.sample.model.request.SampleReceiveDetailRequest;
import com.todaysoft.lims.sample.model.request.SampleReceiveOrder;
import com.todaysoft.lims.sample.model.request.SampleReceivePagingRequest;
import com.todaysoft.lims.sample.model.request.TestingScheduleStartRequest;
import com.todaysoft.lims.sample.service.ISampleReceiveService;
import com.todaysoft.lims.sample.service.ISampleTracingService;
import com.todaysoft.lims.sample.service.IStorageLocationService;
import com.todaysoft.lims.sample.service.ITestingScheduleService;
import com.todaysoft.lims.sample.service.ITestingTaskService;
import com.todaysoft.lims.sample.service.adapter.UserAdapter;
import com.todaysoft.lims.sample.util.CodeUtils;
import com.todaysoft.lims.sample.util.Constant;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.DateUtils;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class SampleReceiceService implements ISampleReceiveService
{
    
    private Logger log = Logger.getLogger(SampleReceiceService.class);
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private IStorageLocationService storageLocatinoService;
    
    @Autowired
    private ISampleTracingService sampleTracingService;
    
    @Autowired
    private ITestingTaskService testingtaskservice;
    
    @Autowired
    private ITestingScheduleService scheduleservice;
    
    @Autowired
    private UserAdapter userAdapter;
    
    @Override
    public Pagination<SampleReceive> paging(SampleReceivePagingRequest request)
    {
        SampleReceiveSearcher searcher = new SampleReceiveSearcher();
        BeanUtils.copyProperties(request, searcher);
        return baseDaoSupport.find(searcher.toQuery(), request.getPageNo(), request.getPageSize(), SampleReceive.class);
    }
    
    @Override
    public SampleReceive getSampleReceive(Integer id)
    {
        return baseDaoSupport.get(SampleReceive.class, id);
    }
    
    @Override
    @Transactional
    public void create(SampleFormRequest request)
    {
        if (StringUtils.isEmpty(request.getReceiveCode()))
        {
            throw new ServiceException("接收code不能为空");
        }
        SampleReceive entity = new SampleReceive();
        BeanUtils.copyProperties(request, entity);
        baseDaoSupport.insert(entity);
    }
    
    @Override
    @Transactional
    public void modify(SampleFormRequest request)
    {
        SampleReceive entity = getSampleReceive(request.getId());
        BeanUtils.copyProperties(request, entity);
        baseDaoSupport.update(entity);
    }
    
    @Override
    @Transactional
    public void delete(Integer id)
    {
        SampleReceive entity = getSampleReceive(id);
        if (entity == null)
        {
            throw new RuntimeException("删除操作出错，原因：对象找不到");
            
        }
        baseDaoSupport.delete(entity);
    }
    
    /**
     * 创建样本关联订单
     */
    @Override
    @Transactional
    public Integer createOrder(SampleReceiveOrder request)
    {
        if (request != null)
        {
            request.setOrderCode(CodeUtils.getSampleOrderCode());
        }
        SampleOrder entity = new SampleOrder();
        BeanUtils.copyProperties(request, entity);
        entity.setChargeState(Constant.ORDER_UNCHARGE);
        entity.setPayState(Constant.ORDER_UNPAY);
        baseDaoSupport.insert(entity);
        return entity.getId();
    }
    
    /**
     * 创建样本接收明细 ---更新添加
     */
    @Override
    @Transactional
    public void createReceiveDetalUpdate(SampleReceiveDetailRequest request)
    {
        SampleReceiveDetail entity = new SampleReceiveDetail();
        Sample sample;
        BeanUtils.copyProperties(request, entity, new String[] {"id"});
        
        sample = baseDaoSupport.get(Sample.class, Integer.parseInt(request.getCode())); //样本类型id
        String containerType = null;
        if (sample != null)
        {
            entity.setName(sample.getName()); //样本类型名称
            // containerType = sample.getStoreContainer();
        }
        
        // entity.setSampleType(String.valueOf(sample.getType()));  //样本类型 --原始 样本
        //规则：样本类型代码<1位>年月日<6位>检测分类<1位>流水号<3位>送样次数<2位>共13位
        //entity.setSampleInstanceCode(SerialNumber.newInstance(sample.getType().toString(), new Date(), selectSampleSeqNextVal(), 1).toString()); // 默认生成规则
        entity.setState(Constant.UNSTARTED); //默认是未启动
        entity.setCreateTime(new Date());
        entity.setReceiveId(request.getId()); //保存父表
        entity.setIsSave(Constant.SAMPLE_DETAIL_SAVED); //保存
        String temporaryStorageLocation = testingtaskservice.searchFreeLocationByContainerType(containerType, Constant.Temporary_Storage_Location, 0);
        String longtermStorageLocation = testingtaskservice.searchFreeLocationByContainerType(containerType, Constant.Longterm_Storage_Location, 0);
        entity.setTemporaryStorageLocation(temporaryStorageLocation);
        entity.setLongtermStorageLocation(longtermStorageLocation);
        
        baseDaoSupport.insert(entity);
        
        StorageLocation tempLocation = storageLocatinoService.getByLocation(temporaryStorageLocation);
        modifyStorageLocation(tempLocation, request, Constant.Temporary_Storage_Location);
        StorageLocation longLocation = storageLocatinoService.getByLocation(longtermStorageLocation);
        modifyStorageLocation(longLocation, request, Constant.Longterm_Storage_Location);
        
        //流程启动
        TestingScheduleStartRequest startRequest = new TestingScheduleStartRequest();
        startRequest.setStarter(request.getCreatorId());
        startRequest.setSampleInstanceId(entity.getId().toString());
        
        SampleReceive sampleReceive = baseDaoSupport.get(SampleReceive.class, request.getId());
        if (null != sampleReceive.getOrderId())
        {
            SampleOrder order = baseDaoSupport.get(SampleOrder.class, Integer.parseInt(sampleReceive.getOrderId()));
            if (Constant.ORDER_PAYED.equals(order.getPayState()))
            { //当订单已付费
                String productIds = order.getTestProduct();
                startByProduct(productIds, startRequest);
            }
            else
            {
                log.info("关联订单为：" + order.getOrderName() + "暂未付费，样本无法启动");
            }
            
        }
        else if (null != sampleReceive.getRelatedItems())
        {
            Project project = baseDaoSupport.get(Project.class, Integer.parseInt(sampleReceive.getRelatedItems()));
            String productIds = project.getProductArray();
            startByProduct(productIds, startRequest);
        }
        
    }
    
    /**
     * 创建样本接收明细
     */
    @Override
    @Transactional
    public void createReceiveDetal(SampleReceiveDetailRequest request)
    {
        SampleReceiveDetail entity = new SampleReceiveDetail();
        Sample sample;
        BeanUtils.copyProperties(request, entity);
        
        sample = baseDaoSupport.get(Sample.class, Integer.parseInt(request.getCode()));
        String containerType = null;
        if (sample != null)
        {
            entity.setName(sample.getName()); //样本类型名称
            // containerType = sample.getStoreContainer();
        }
        
        //entity.setSampleType(String.valueOf(sample.getType())); //样本类型
        
        //规则：样本类型代码<1位>年月日<6位>检测分类<1位>流水号<3位>送样次数<2位>共13位
        // entity.setSampleInstanceCode(SerialNumber.newInstance(sample.getType().toString(), new Date(), selectSampleSeqNextVal(), 1).toString()); // 默认生成规则
        entity.setState(Constant.UNSTARTED); //默认是未启动
        entity.setCreateTime(new Date());
        entity.setIsSave(Constant.SAMPLE_DETAIL_TEMPSAVE);
        String temporaryStorageLocation = testingtaskservice.searchFreeLocationByContainerType(containerType, Constant.Temporary_Storage_Location, 0);
        String longtermStorageLocation = testingtaskservice.searchFreeLocationByContainerType(containerType, Constant.Longterm_Storage_Location, 0);
        entity.setTemporaryStorageLocation(temporaryStorageLocation);
        entity.setLongtermStorageLocation(longtermStorageLocation);
        
        baseDaoSupport.insert(entity);
        
        UserDetailsModel user = userAdapter.getUser(entity.getCreatorId());
        
        //样本信息保存到样本跟踪表，有临存和长存两个状态，所以要存两条记录，分别进行跟踪
        //临存
        SampleTracing tempStore =
            SampleTracing.builder()
                .createTime(new Date())
                .sampleDetailId(entity.getId())
                .original(VariableUtils.ORIGINAL_Y)
                .category(VariableUtils.TYPE_SAMPLE)
                .typeId(Integer.parseInt(entity.getCode()))
                .typeName(entity.getName())
                .instCode(entity.getSampleInstanceCode())
                .storageType(VariableUtils.STORAGE_TEMP)
                .storageLocation(entity.getTemporaryStorageLocation())
                .remainAmount(Double.parseDouble(entity.getTemporaryStorageAmount()))
                .unit(entity.getCompany())
                .executerName(user.getArchive().getName())
                .build();
        sampleTracingService.create(tempStore);
        // 保存样本变更记录表 LIMS_SAMPLE_CHANGE_RECORD
        SampleChangeRecord changeRcordTemp =
            SampleChangeRecord.builder()
                .sampleTracingId(tempStore.getId())
                .changeType(VariableUtils.TYPE_IN)
                .changeEvent(VariableUtils.CHANGE_RECEIVE)
                .changeAmount(tempStore.getRemainAmount())
                .changeTime(new Date())
                .build();
        sampleTracingService.createSampleChange(changeRcordTemp);
        
        //长存
        SampleTracing longStore =
            SampleTracing.builder()
                .createTime(new Date())
                .sampleDetailId(entity.getId())
                .original(VariableUtils.ORIGINAL_Y)
                .category(VariableUtils.TYPE_SAMPLE)
                .typeId(Integer.parseInt(entity.getCode()))
                .typeName(entity.getName())
                .instCode(entity.getSampleInstanceCode())
                .storageType(VariableUtils.STORAGE_LONG)
                .storageLocation(entity.getLongtermStorageLocation())
                .remainAmount(Double.parseDouble(entity.getLongtermStorageAmount()))
                .unit(entity.getCompany())
                .executerName(user.getArchive().getName())
                .build();
        sampleTracingService.create(longStore);
        SampleChangeRecord changeRcordLong =
            SampleChangeRecord.builder()
                .sampleTracingId(longStore.getId())
                .changeType(VariableUtils.TYPE_IN)
                .changeEvent(VariableUtils.CHANGE_RECEIVE)
                .changeAmount(longStore.getRemainAmount())
                .changeTime(new Date())
                .build();
        sampleTracingService.createSampleChange(changeRcordLong);
        
        StorageLocation tempLocation = storageLocatinoService.getByLocation(temporaryStorageLocation);
        modifyStorageLocation(tempLocation, request, Constant.Temporary_Storage_Location);
        StorageLocation longLocation = storageLocatinoService.getByLocation(longtermStorageLocation);
        modifyStorageLocation(longLocation, request, Constant.Longterm_Storage_Location);
    }
    
    public void modifyStorageLocation(StorageLocation location, SampleReceiveDetailRequest request, String locationType)
    {
        /*location.setSampleType("1");
        location.setCompany(request.getCompany());
        if (request.getSampleCount() > 0 | request.getLongtermStorageAmount() != null)
        {
            if (Constant.Longterm_Storage_Location.equals(locationType))
            {
                location.setStorageCapacity(Double.valueOf(request.getLongtermStorageAmount()));
            }
            else
            {
                location.setStorageCapacity((double)request.getSampleCount());
            }
        }
        location.setSampleKey(Integer.parseInt(request.getCode()));
        location.setState(Constant.CONTAINERLOCATION_USED);*/
        storageLocatinoService.modify(location);
    }
    
    /**
     * 下拉框展现所有未关联样本接收订单
     *
     * @param request
     * @return
     */
    @Override
    public List<SampleOrder> list(OrderListRequest request)
    {
        SampleOrderSearcher searcher = new SampleOrderSearcher();
        BeanUtils.copyProperties(request, searcher);
        return baseDaoSupport.find(searcher.toQuery(), SampleOrder.class);
    }
    
    @Override
    public List<SampleReceiveDetail> detailList(SampleReceiveDetailRequest request)
    {
        SampleReceiveDetailSearcher searcher = new SampleReceiveDetailSearcher();
        BeanUtils.copyProperties(request, searcher);
        return baseDaoSupport.find(searcher);
    }
    
    /**
     * 删除明细 --- 关联
     *
     * @param id
     */
    
    @Override
    @Transactional
    public void deleteDetail(Integer id)
    {
        
        SampleReceiveDetail entity = getSampleReceiveDetail(String.valueOf(id));
        String location = entity.getTemporaryStorageLocation();
        baseDaoSupport.delete(entity);
        
        StorageLocation sl = storageLocatinoService.getByLocation(location);
        //sl.builder().state("1").sampleKey(null).sampleType(null).storageCapacity(null).locationCode(null).build();
        /*  sl.setState("1");
          sl.setSampleKey(null);
          sl.setSampleType(null);
          sl.setStorageCapacity(null);
          sl.setCompany(null);*/
        storageLocatinoService.modify(sl);
        
    }
    
    /**
     * 根据主键获取样本明细对象
     *
     * @param id
     * @return
     */
    @Override
    public SampleReceiveDetail getSampleReceiveDetail(String id)
    {
        return baseDaoSupport.get(SampleReceiveDetail.class, id);
    }
    
    /**
     * 创建样本接收记录
     */
    @Override
    @Transactional
    public void createReceiveRecord(SampleFormRequest request)
    {
        
        SampleReceive entity = new SampleReceive();
        BeanUtils.copyProperties(request, entity);
        entity.setReceiveCode(CodeUtils.getSampleReceiveCode());
        entity.setAcceptDate(new Date());
        baseDaoSupport.insert(entity);
        
        String ids = request.getDetailId();
        SampleReceiveDetail s;
        if (StringUtils.isNotEmpty(ids))
        {
            String[] id = ids.split(",");
            for (String i : id)
            {
                log.info("关联明细共" + id.length + "条，具体为：" + i);
                s = baseDaoSupport.get(SampleReceiveDetail.class, Integer.parseInt(i));
                if (s != null)
                {
                    s.setReceiveId(entity.getId());
                    s.setIsSave(Constant.SAMPLE_DETAIL_SAVED);
                    baseDaoSupport.saveOrUpdate(s);
                    
                    //流程启动
                    TestingScheduleStartRequest startRequest = new TestingScheduleStartRequest();
                    User user = userAdapter.get(request.getAcceptPerson());
                    if (user != null)
                    {
                        String userId = String.valueOf(user.getId());
                        startRequest.setStarter(userId);
                    }
                    else
                    {
                        startRequest.setStarter("1");
                    }
                    startRequest.setSampleInstanceId(i);
                    if (null != entity.getOrderId())
                    {
                        SampleOrder order = baseDaoSupport.get(SampleOrder.class, Integer.parseInt(entity.getOrderId()));
                        if (Constant.ORDER_PAYED.equals(order.getPayState()))
                        { //当订单已付费
                            String productIds = order.getTestProduct();
                            startByProduct(productIds, startRequest);
                        }
                        else
                        {
                            log.info("关联订单为：" + order.getOrderName() + "暂未付费，样本无法启动。");
                        }
                    }
                    else if (null != entity.getRelatedItems())
                    {
                        Project project = baseDaoSupport.get(Project.class, Integer.parseInt(entity.getRelatedItems()));
                        String productIds = project.getProductArray();
                        startByProduct(productIds, startRequest);
                    }
                    
                }
                else
                {
                    log.error("根据明细id:" + i + "获取不到明细对象，保存样本信息成功，更新关联明细失败");
                }
                
            }
        }
        else
        {
            log.info("样本接收无明细");
        }
        
    }
    
    /**
     * 分页查询订单列表
     */
    @Override
    public Pagination<SampleOrder> paging(OrderListRequest request)
    {
        SampleOrderSearcher searcher = new SampleOrderSearcher();
        BeanUtils.copyProperties(request, searcher);
        Pagination<SampleOrder> orders = baseDaoSupport.find(searcher.toListQuery(), request.getPageNo(), request.getPageSize(), SampleOrder.class);
        for (SampleOrder order : orders.getRecords())
        {
            Product product = baseDaoSupport.get(Product.class, Integer.parseInt(order.getTestProduct()));
            order.setProductName(product.getName());
        }
        return orders;
    }
    
    @Override
    public List<SampleOrder> getOrder(OrderListRequest request)
    {
        return null;
    }
    
    /**
     * 根据订单id查询订单
     */
    @Override
    public SampleOrder getOrderById(String id)
    {
        Integer orderId = null;
        if (id != null && !"".equals(id))
        {
            orderId = Integer.valueOf(id);
        }
        return baseDaoSupport.get(SampleOrder.class, orderId);
    }
    
    /**
     * 根据订单id查询样本明细
     * <p>
     * 去掉关系表设计 2016年8月5日14:21:23
     */
    @Override
    public List<SampleReceiveDetail> getDetailByOrderId(Integer orderId)
    {
        String hql = "select d from SampleReceive s ,  SampleReceiveDetail d where 1=1 ";
        List<Object> values = new ArrayList<Object>();
        hql = addWhere(orderId, hql, values);
        return (List<SampleReceiveDetail>)baseDaoSupport.find(hql, values);
        
    }
    
    private String addWhere(Integer orderId, String hql, List<Object> values)
    {
        if (orderId != null)
        {
            hql += " and s.orderId =? ";
            values.add(orderId.toString());
        }
        hql += " and s.id = d.receiveId ";
        
        return hql;
    }
    
    @Override
    public List<SampleReceiveDetail> getRelation(SampleFormRequest request)
    {
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql("FROM SampleReceiveDetail s WHERE s.receiveId = :receiveId");
        queryer.setNames(Arrays.asList("receiveId"));
        queryer.setValues(Arrays.asList((Object)request.getId()));
        return baseDaoSupport.find(queryer, SampleReceiveDetail.class);
        
    }
    
    @Override
    public Pagination<SampleReceiveDetail> sampleDetailPaging(SampleReceiveDetailPagingRequest request)
    {
        
        SampleReceiveDetailSearcher searcher = new SampleReceiveDetailSearcher();
        BeanUtils.copyProperties(request, searcher);
        
        Pagination<SampleReceiveDetail> result =
            baseDaoSupport.find(searcher.toQueryStartActiviti(), request.getPageNo(), request.getPageSize(), SampleReceiveDetail.class);
        
        List<SampleReceiveDetail> record = result.getRecords();
        if (record != null)
        {
            for (SampleReceiveDetail d : record)
            {
                Integer receiveId = d.getReceiveId();
                if (null != receiveId)
                {
                    SampleReceive receive = baseDaoSupport.get(SampleReceive.class, receiveId);
                    if (null != receive)
                    {
                        String orderId = receive.getOrderId(); //关联订单
                        String items = receive.getRelatedItems();
                        checkSampleReceive(orderId, items);
                        d.setAcceptDate(DateUtils.formatDateTime(receive.getAcceptDate()));
                        if (orderId != null && !"".equals(orderId))
                        {
                            SampleOrder order = baseDaoSupport.get(SampleOrder.class, Integer.parseInt(orderId));
                            if (null != order)
                            {
                                d.setProductId(order.getTestProduct());
                                List<String> l = new ArrayList<String>();
                                if (order.getTestProduct() != null && !"".equals(order.getTestProduct()))
                                {
                                    String[] ids = order.getTestProduct().split(",");
                                    for (int i = 0; i < ids.length; i++)
                                    {
                                        l.add(ids[i]);
                                    }
                                }
                                d.setProductList(l);
                                
                            }
                        }
                    }
                    
                }
                
                List<Project> proList = baseDaoSupport.find(searcher.toQueryProductByReceiveID(receiveId), Project.class);
                if (Collections3.isNotEmpty(proList))
                {
                    Project pro = proList.get(0);
                    String proStrings = pro.getProductArray();
                    d.setProductId(proStrings);
                    
                    List<String> l = new ArrayList<String>();
                    if (proStrings != null && !"".equals(proStrings))
                    {
                        String[] ids = proStrings.split(",");
                        for (int i = 0; i < ids.length; i++)
                        {
                            l.add(ids[i]);
                        }
                    }
                    d.setProductList(l);
                    
                }
                
            }
            
        }
        
        return result;
        
    }
    
    //根据样本接收id 查询产品id
    
    //判断样本接收是否已关联订单或者项目
    private boolean checkSampleReceive(String orderId, String items)
    {
        
        Boolean r1 = StringUtils.isEmpty(orderId);
        Boolean r2 = StringUtils.isEmpty(items);
        if (!r1.equals(r2))
        {
            return true;
        }
        else
        {
            throw new RuntimeException("样本接收数据有误，必须只能关联订单或者项目其中一个");
        }
        
    }
    
    @Override
    public Integer selectSampleSeqNextVal()
    {
        Object[] obj = {};
        List<?> result = baseDaoSupport.findBySql("SELECT NEXTVAL('SampleInstanceSeq')", obj);
        if (result != null)
        {
            return (Integer)result.get(0);
        }
        else
        {
            log.error("获取流水号有误！请检查数据库流水号表是否新建默认数据！");
            return 1;
        }
    }
    
    @Override
    @Transactional
    public Boolean redoSampleDetail(String data)
    {
        Integer i =
            baseDaoSupport.execute("delete SampleReceiveDetail i where i.isSave = ? and i.createUser = ? ",
                new Object[] {Constant.SAMPLE_DETAIL_TEMPSAVE, data});
        return true;
    }
    
    @Override
    @Transactional
    public Boolean checkedOderName(String orderName)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM SampleOrder s WHERE s.orderName = :orderName")
                .names(Lists.newArrayList("orderName"))
                .values(Lists.newArrayList(orderName))
                .build();
        List<SampleOrder> records = baseDaoSupport.find(queryer, SampleOrder.class);
        
        return Collections3.isEmpty(records);
    }
    
    public void startByProduct(String productIds, TestingScheduleStartRequest startRequest)
    {
        List<Integer> pIdList = Arrays.stream(productIds.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        pIdList.stream().forEach(pId -> {
            startRequest.setProductId(String.valueOf(pId));
            scheduleservice.start(startRequest);
        });
    }
    
    /*	@Override
    	public Pagination<SampleReceiveDetail> getSampleDetailByOrderId(Integer id) {
    		String hql = "select d from SampleReceive s ,  SampleReceiveDetail d where 1=1 ";
            List<Object> values = new ArrayList<Object>();
            hql = addWhere(id, hql, values);
             baseDaoSupport.find(hql, values);
    		return null;
    		
    	}*/
    
    @Override
    public SampleOrder searchOrderById(Integer id)
    {
        return baseDaoSupport.get(SampleOrder.class, id);
    }
    
    @Override
    public Pagination<SampleReceiveDetail> searchSampleDetailByOrderId(OrderListRequest request)
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("select d FROM SampleReceive s , SampleReceiveDetail d WHERE 1=1");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter(hql, request, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return baseDaoSupport.find(queryer, request.getPageNo(), request.getPageSize(), SampleReceiveDetail.class);
    }
    
    private void addFilter(StringBuffer hql, OrderListRequest request, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(request.getOrderId()))
        {
            hql.append(" AND s.orderId =:orderId");
            paramNames.add("orderId");
            parameters.add(request.getOrderId().toString());
        }
        hql.append(" and s.id = d.receiveId ");
        
    }
    
    @Override
    @Transactional
    public Integer commitCharge(CommitChargeRequest request)
    {
        SampleOrder entity = searchOrderById(request.getOrderId());
        BeanUtils.copyProperties(request, entity);
        entity.setChargeState(Constant.ORDER_CHARGED);
        entity.setPayState(Constant.ORDER_PAYED);
        baseDaoSupport.update(entity);
        
        //启动这个订单下所有样本的启动
        TestingScheduleStartRequest startRequest = new TestingScheduleStartRequest();
        startRequest.setOrderId(request.getOrderId().toString());
        startRequest.setStarter(request.getStarter());
        //      startRequest.setProductId(entity.getp);
        //      startRequest.setSampleInstanceId(sampleInstanceId);
        List<SampleReceiveDetail> detail = getDetailByOrderId(request.getOrderId());
        if (Collections3.isNotEmpty(detail))
        {
            //遍历样本明细id执行启动，此时就是关联订单，暂不用项目
            for (SampleReceiveDetail s : detail)
            {
                startRequest.setSampleInstanceId(s.getId().toString());
                if (null != request.getOrderId())
                {
                    SampleOrder order = baseDaoSupport.get(SampleOrder.class, request.getOrderId());
                    String productIds = order.getTestProduct();
                    startByProduct(productIds, startRequest);
                }
            }
        }
        else
        {
            log.info("关联订单为：" + request.getOrderId() + "无可启动样本");
        }
        
        return request.getOrderId();
    }
    
}
