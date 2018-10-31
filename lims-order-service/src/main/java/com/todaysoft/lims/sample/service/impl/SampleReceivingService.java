package com.todaysoft.lims.sample.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.dao.searcher.SampleReceivingSearcher;
import com.todaysoft.lims.sample.dao.searcher.SampleTransferringDetailSearcher;
import com.todaysoft.lims.sample.entity.Sample;
import com.todaysoft.lims.sample.entity.StorageLocation;
import com.todaysoft.lims.sample.entity.StoreContainer;
import com.todaysoft.lims.sample.entity.order.AppSampleTransport;
import com.todaysoft.lims.sample.entity.order.OrderPrimarySample;
import com.todaysoft.lims.sample.entity.order.OrderResearchSample;
import com.todaysoft.lims.sample.entity.order.OrderSampleView;
import com.todaysoft.lims.sample.entity.order.OrderSubsidiarySample;
import com.todaysoft.lims.sample.entity.samplereceiving.ReceivedSample;
import com.todaysoft.lims.sample.entity.samplereceiving.SampleReceiving;
import com.todaysoft.lims.sample.entity.samplereceiving.SampleReceivingDetail;
import com.todaysoft.lims.sample.entity.samplereceiving.SampleStoraging;
import com.todaysoft.lims.sample.entity.samplereceiving.SampleStoragingDetail;
import com.todaysoft.lims.sample.entity.samplereceiving.SampleTransferring;
import com.todaysoft.lims.sample.entity.samplereceiving.SampleTransferringDetail;
import com.todaysoft.lims.sample.entity.samplereceiving.TestingSample;
import com.todaysoft.lims.sample.entity.samplereceiving.TestingSampleStorage;
import com.todaysoft.lims.sample.model.SampleReceivingFormRequest;
import com.todaysoft.lims.sample.model.SampleStoragingFormRequest;
import com.todaysoft.lims.sample.model.SampleTransferFormRequest;
import com.todaysoft.lims.sample.model.order.OrderSearchRequest;
import com.todaysoft.lims.sample.model.request.SampleReceivePagingRequest;
import com.todaysoft.lims.sample.model.sampleReceiving.SampleStoragingSearcher;
import com.todaysoft.lims.sample.model.sampleReceiving.SampleTransferringDetailModel;
import com.todaysoft.lims.sample.service.IOrderService;
import com.todaysoft.lims.sample.service.ISampleReceivingService;
import com.todaysoft.lims.sample.service.adapter.TestingAdapter;
import com.todaysoft.lims.sample.util.CodeUtils;
import com.todaysoft.lims.sample.util.Constant;
import com.todaysoft.lims.sample.util.LpopRedisLocation;
import com.todaysoft.lims.sample.util.Response;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class SampleReceivingService implements ISampleReceivingService
{
    
    private Logger logger = Logger.getLogger(this.getClass());
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private TestingAdapter testingAdapter;
    
    @Autowired
    private IOrderService orderService;
    
    @Override
    public Pagination<SampleReceiving> sampleReceivingPaging(SampleReceivePagingRequest request)
    {
        SampleReceivingSearcher searcher = new SampleReceivingSearcher();
        BeanUtils.copyProperties(request, searcher);
        Pagination<SampleReceiving> paging = baseDaoSupport.find(searcher.toAuthQuery(), request.getPageNo(), request.getPageSize(), SampleReceiving.class);
        
        return paging;
    }
    
    @Override
    @Transactional
    public void create(SampleReceivingFormRequest request)
    {
        SampleReceiving entity = new SampleReceiving();
        BeanUtils.copyProperties(request, entity);
        entity.setCode(CodeUtils.getDefaultCode("收样"));
        entity.setReceiveTime(new Date());
        baseDaoSupport.insert(entity);
        createForeign(request, entity);
        
    }
    
    private void createForeign(SampleReceivingFormRequest request, SampleReceiving entity)
    {
        if (Collections3.isNotEmpty(request.getSampleReceivingDetail()))
        {
            for (SampleReceivingDetail source : request.getSampleReceivingDetail())
            {
                SampleReceivingDetail target = new SampleReceivingDetail();
                target.setSampleReceiving(entity);
                BeanUtils.copyProperties(source, target, new String[] {"sampleReceiving"});
                baseDaoSupport.insert(target);
                String familyRelation = "";
                int bussinessType = source.getBtype();
                switch (bussinessType)
                {
                    case 1:
                        OrderPrimarySample psample = baseDaoSupport.get(OrderPrimarySample.class, source.getId());
                        if (StringUtils.isNotEmpty(psample))
                        {
                            //质检状态 ,质检状态：0-不合格 1-合格[打包运输状态：0：待送样；1：送样中；2：已接收样本；3：异常样本；4：已返样样本；]
                            psample.setSamplePackageStatus(source.getQcStatus() == 1 ? 2 : 3);
                            psample.setErrorReason(source.getQcRemark());
                            psample.setSampleErrorStatus(0);
                            psample.setUpdateTime(new Date());
                            psample.setAcceptTime(new Date());
                            psample.setErrorType(source.getQcStatus() == 1 ? null : "0"); //这里表示收样异常
                            baseDaoSupport.update(psample);
                        }
                        
                        break;
                    case 2:
                        OrderSubsidiarySample subample = baseDaoSupport.get(OrderSubsidiarySample.class, source.getId());
                        if (StringUtils.isNotEmpty(subample))
                        {
                            //质检状态 ,质检状态：0-不合格 1-合格[打包运输状态：0：待送样；1：送样中；2：已接收样本；3：异常样本；4：已返样样本；]
                            familyRelation = subample.getFamilyRelation();
                            subample.setSamplePackageStatus(source.getQcStatus() == 1 ? 2 : 3);
                            subample.setErrorReason(source.getQcRemark());
                            subample.setSampleErrorStatus(0);
                            subample.setUpdateTime(new Date());
                            subample.setAcceptTime(new Date());
                            subample.setErrorType(source.getQcStatus() == 1 ? null : "0"); //这里表示收样异常
                            baseDaoSupport.update(subample);
                        }
                        break;
                    case 3:
                        OrderResearchSample resample = baseDaoSupport.get(OrderResearchSample.class, source.getId());
                        if (StringUtils.isNotEmpty(resample))
                        {
                            //质检状态 ,质检状态：0-不合格 1-合格[打包运输状态：0：待送样；1：送样中；2：已接收样本；3：异常样本；4：已返样样本；]
                            resample.setSamplePackageStatus(source.getQcStatus() == 1 ? 2 : 3);
                            resample.setErrorReason(source.getQcRemark());
                            resample.setSampleErrorStatus(0);
                            resample.setUpdateTime(new Date());
                            resample.setAcceptTime(new Date());
                            resample.setErrorType(source.getQcStatus() == 1 ? null : "0"); //这里表示收样异常
                            baseDaoSupport.update(resample);
                        }
                        break;
                    default:
                        break;
                }
                if (Constant.SAMPLE_RECEIVING_SUCCESS == source.getQcStatus())
                {
                    OrderSearchRequest or = new OrderSearchRequest();
                    or.setSampleCode(source.getSampleCode());
                    List<OrderSampleView> view = getSampleByView(or);
                    if (Collections3.isNotEmpty(view))
                    {
                        OrderSampleView result = view.get(0);
                        Sample sample = baseDaoSupport.get(Sample.class, result.getSampleType());
                        ReceivedSample receivedSample =
                            ReceivedSample.builder()
                                .orderId(entity.getOrderId())
                                .businessType(result.getSampleBtype())
                                .sampleCode(result.getSampleCode())
                                .samplingDate(result.getSamplingDate())
                                .sampleId(result.getSampleId())
                                .sampleName(result.getSampleName())
                                .typeId(result.getSampleType())
                                .typeName(sample != null ? sample.getName() : result.getSampleType() + "错误数据")
                                .lsStatus(Constant.SAMPLE_RECEIVE_LSTSOUT_STATUS)
                                .purpose(result.getPurpose())
                                .tsStatus(Constant.SAMPLE_RECEIVE_LSTSOUT_STATUS)
                                .processStatus(Constant.SAMPLE_RECEIVING)
                                .sampleSize(result.getSampleSize())
                                
                                .build();
                        baseDaoSupport.insert(receivedSample);
                        //保存testing_sample表
                        TestingSample testingSample = new TestingSample();
                        testingSample.setSampleCode(result.getSampleCode());
                        testingSample.setSampleTypeId(result.getSampleType());
                        testingSample.setSampleTypeName(sample != null ? sample.getName() : result.getSampleType() + "错误数据");
                        testingSample.setOriginalSampleId(result.getSampleId());
                        baseDaoSupport.insert(testingSample);
                    }
                }
                
            }
        }
        
    }
    
    @Override
    @Transactional
    public void update(SampleReceivingFormRequest request)
    {
        if (StringUtils.isNotEmpty(request.getId()))
        {
            SampleReceiving entity = getSampleReceivingById(request.getId());
            if (entity != null)
            {
                BeanUtils.copyProperties(request, entity);
                baseDaoSupport.update(entity);
                
                baseDaoSupport.executeHql("delete SampleReceivingDetail s where s.sampleReceiving.id = ?", new Object[] {entity.getId()}); //先删除原有
                createForeign(request, entity);
                
            }
            else
            {
                throw new RuntimeException("ERROR:根据" + request.getId() + "找不到样本对象！");
            }
            
        }
    }
    
    @Override
    public SampleReceiving getSampleReceivingById(String id)
    {
        return baseDaoSupport.get(SampleReceiving.class, id);
    }
    
    @Override
    public List<OrderSampleView> getSampleByView(OrderSearchRequest request)
    {
        NamedQueryer queryer = new NamedQueryer();
        StringBuffer hql = new StringBuffer();
        hql.append("FROM OrderSampleView o where  o.sampleCode =:code");
        queryer.setHql(hql.toString());
        queryer.setNames(Arrays.asList("code"));
        queryer.setValues(Arrays.asList((Object)request.getSampleCode()));
        List<OrderSampleView> view = baseDaoSupport.find(queryer, OrderSampleView.class);
        return view;
    }
    
    @Override
    @Transactional
    public void createTransfer(SampleTransferFormRequest request)
    {
        SampleTransferring entity = new SampleTransferring();
        BeanUtils.copyProperties(request, entity);
        entity.setCode(CodeUtils.getDefaultCode("转存"));
        entity.setOperateTime(new Date());
        baseDaoSupport.insert(entity);
        
        if (Collections3.isNotEmpty(request.getSampleTransferringDetail()))
        {
            
            for (SampleTransferringDetailModel source : request.getSampleTransferringDetail())
            {
                SampleTransferringDetail target = new SampleTransferringDetail();
                BeanUtils.copyProperties(source, target);
                target.setSampleTransferring(entity);
                baseDaoSupport.insert(target);
                
                //转存不分配位置 --根据sampleId 更新量
                if (StringUtils.isNotEmpty(source.getSampleId()))
                {
                    ReceivedSample receivedSample = baseDaoSupport.get(ReceivedSample.class, source.getSampleId());
                    if (StringUtils.isNotEmpty(receivedSample))
                    {
                        if (StringUtils.isNotEmpty(source.getLsSize()))
                        {
                            receivedSample.setLsSize(source.getLsSize());
                        }
                        receivedSample.setTsSize(source.getTsSize());
                        receivedSample.setProcessStatus(Constant.SAMPLE_RECEIVING_TRANS);
                        baseDaoSupport.update(receivedSample);
                    }
                    else
                    {
                        logger.error("根据样本id：" + source.getSampleId() + "找不到已收样样本");
                    }
                }
                else
                {
                    logger.error("根据样本id不能为空");
                }
                
            }
            
        }
        
    }
    
    @Transactional
    @Override
    public String updateLocationState(String code)
    {
        if (StringUtils.isNotEmpty(code))
        {
            
            StringBuffer hql = new StringBuffer();
            hql.append("SELECT s FROM StorageLocation s  WHERE 1=1 and s.code =:code ");
            
            List<StorageLocation> records = baseDaoSupport.findByNamedParam(StorageLocation.class, hql.toString(), new String[] {"code"}, new Object[] {code});
            if (Collections3.isNotEmpty(records))
            {
                StorageLocation locatioin = records.get(0);
                locatioin.setState(Constant.LOCATIONS_USED);
                baseDaoSupport.update(locatioin);
                return locatioin.getCode();
            }
            
        }
        return code;
        
    }
    
    @Override
    public StoreContainer getBestDevice(String id, String storageType)
    {
        StoreContainer locatioin = null;
        if (StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(storageType))
        {
            NamedQueryer queryer = new NamedQueryer();
            StringBuffer hql = new StringBuffer();
            hql.append("SELECT distinct sc FROM  StoreContainer sc,StorageLocation lo WHERE  lo.deviceId = sc.id");
            hql.append(" AND sc.deleted =false "); //非删除
            hql.append(" AND sc.sampleType =:id "); //2长期 1临存 
            hql.append(" AND sc.storageType =:storageType "); //2长期 1临存 
            hql.append(" AND lo.state = 1");
            hql.append(" order by sc.createTime asc ");
            queryer.setHql(hql.toString());
            queryer.setNames(Arrays.asList("id", "storageType"));
            queryer.setValues(Arrays.asList((Object)id, storageType));
            Pagination<StoreContainer> records = baseDaoSupport.find(queryer, 1, 10, StoreContainer.class);
            if (Collections3.isNotEmpty(records.getRecords()))
            {
                locatioin = records.getRecords().get(0);
            }
            
        }
        return locatioin;
        
    }
    
    //根据样本类型、存储类型---分配位置
    //storeType --1(临时存储) 2(长期存储)
    @Transactional
    @Override
    public String searchFreeLocation(String id, String storageType)
    {
        if (StringUtils.isNotEmpty(id))
        {
            NamedQueryer queryer = new NamedQueryer();
            StringBuffer hql = new StringBuffer();
            hql.append("SELECT lo FROM StorageLocation lo , StoreContainer sc, Sample ss WHERE 1=1");
            hql.append(" AND ss.id =:id ");
            hql.append(" AND sc.deleted =false "); //非删除
            hql.append(" AND sc.storageType =:storageType "); //2长期 1临存
            hql.append(" AND sc.sampleType =ss.id "); //根据样本类型
            hql.append(" AND lo.state = 1 "); //存储状态  1 未存储；2 已占用
            hql.append(" AND ss.storageType = sc.deviceType "); //容器类型 
            hql.append(" AND lo.deviceId = sc.id ");
            hql.append(" order by lo.code asc ");
            queryer.setHql(hql.toString());
            queryer.setNames(Arrays.asList("id", "storageType"));
            queryer.setValues(Arrays.asList((Object)id, storageType));
            Pagination<StorageLocation> records = baseDaoSupport.find(queryer, 1, 10, StorageLocation.class);
            if (Collections3.isNotEmpty(records.getRecords()))
            {
                StorageLocation locatioin = records.getRecords().get(0);
                locatioin.setState(Constant.LOCATIONS_USED);
                baseDaoSupport.update(locatioin);
                return locatioin.getCode();
            }
            else
            {
                logger.error("无可用存储位置，请增加类型为" + id + "的容器位置");
                return null;
            }
        }
        else
        {
            logger.error("容器类型 为：" + id + "样本没有没有配置存储容器，无法生成位置");
            return null;
        }
        
    }
    
    private List<ReceivedSample> checkStatus(OrderSearchRequest request, int status)
    {
        
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        StringBuffer hql = new StringBuffer();
        hql.append("FROM ReceivedSample o where  o.sampleCode =:code and o.processStatus =:status"); //转存库里 并且是已转存过的 样本
        addFilter(hql, paramNames, parameters, request, status);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        
        List<ReceivedSample> result = baseDaoSupport.find(queryer, ReceivedSample.class);
        return result;
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters, OrderSearchRequest request, int status)
    {
        paramNames.add("code");
        parameters.add(request.getSampleCode());
        paramNames.add("status");
        parameters.add(status);
        if (Constant.SAMPLE_RECEIVE_TEMPINSTRING.equals(request.getStoreType()))
        {
            hql.append(" AND o.tsLocation is not null");
        }
        if (Constant.SAMPLE_RECEIVE_LONGINSTRIGN.equals(request.getStoreType()))
        {
            hql.append(" AND o.lsLocation is not null");
        }
    }
    
    @Override
    public List<ReceivedSample> getHasInboundSample(OrderSearchRequest request)
    {
        return checkStatus(request, Constant.SAMPLE_RECEIVING_TRANS);
    }
    
    /* private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters, OrderSearchRequest request)
     {
         if (Constant.SAMPLE_RECEIVE_TEMPINSTRING.equals(request.getStoreType()))
         {
             hql.append(" AND o.tsSize > 0");
         }
         else
         {
             hql.append(" AND o.lsSize > 0");
         }
         
     }*/
    
    @Autowired
    private LpopRedisLocation lpopRedisLocation;
    
    @Override
    @Transactional
    public Response<ReceivedSample> getTransferSample(OrderSearchRequest request)
    {
        Response<ReceivedSample> response = new Response<ReceivedSample>();
        
        if (StringUtils.isNoneEmpty(request.getSampleCode()))
        {
            StringBuffer hql = new StringBuffer();
            // hql.append("FROM ReceivedSample o where  o.sampleCode =:code and o.processStatus =:status "); //转存库里 并且是已转存过的 样本
            hql.append("FROM ReceivedSample o where  o.sampleCode =:code  "); //转存库里 并且是已转存过的 样本
            
            List<ReceivedSample> result =
                baseDaoSupport.findByNamedParam(ReceivedSample.class, hql.toString(), new String[] {"code"}, new Object[] {request.getSampleCode()});
            
            String storagingId = "";
            SampleStoraging entity = new SampleStoraging();
            if (StringUtils.isEmpty(request.getStoragingId()))
            {
                BeanUtils.copyProperties(request, entity);
                entity.setCode(CodeUtils.getSampleReceivingCode());
                entity.setOperateTime(new Date());
                entity.setType(Integer.parseInt(request.getStoreType()));
                entity.setStatus(false); //false---可以继续入库
                baseDaoSupport.insert(entity);
                storagingId = entity.getId();
            }
            else
            {
                entity = baseDaoSupport.get(SampleStoraging.class, request.getStoragingId());
            }
            
            if (Collections3.isNotEmpty(result))
            {
                
                for (ReceivedSample source : result)
                {
                    if (source.getProcessStatus().equals(Constant.SAMPLE_REDO_STATUS))
                    {
                        response.setSuccess(false);
                        response.setMessage("原因：" + request.getSampleCode() + "订单已取消，无需入库!");
                        return response;
                    }
                    
                    SampleStoragingDetail target = new SampleStoragingDetail();
                    target.setSampleCode(source.getSampleCode());
                    if (Constant.SAMPLE_RECEIVE_LONGINSTRIGN.equals(request.getStoreType()))
                    {
                        
                        String location = "";
                        if (StringUtils.isNotEmpty(source.getLsSize()) && source.getLsSize().compareTo(BigDecimal.ZERO) == 1)
                        {
                            StoreContainer device = getBestDevice(source.getTypeId(), Constant.SAMPLE_RECEIVE_LONGINSTRIGN);
                            location =
                                lpopRedisLocation.lpopLocationRedis(device != null ? device.getId() : "",
                                    source.getTypeId(),
                                    Constant.SAMPLE_RECEIVE_LONGINSTRIGN);
                            updateLocationState(location);
                        }
                        else
                        {
                            response.setSuccess(false);
                            response.setMessage("原因：" + request.getSampleCode() + "长期存储量为0,无需入库");
                            return response;
                        }
                        if (StringUtils.isNotEmpty(location))
                        {
                            source.setParentId(storagingId);//返回到前台页面显示
                            source.setLsLocation(location); //返回到前台页面显示
                            target.setLocation(location);
                            target.setSampleStoraging(entity);
                            baseDaoSupport.insert(target);
                            entity.setOperateTime(new Date());
                            baseDaoSupport.update(entity);
                            
                        }
                        else
                        {
                            response.setSuccess(false);
                            response.setMessage("原因：无可用的长期存储位置，请添加【" + source.getTypeName() + "】对应的存储容器");
                            return response;
                        }
                        
                    }
                    if (Constant.SAMPLE_RECEIVE_TEMPINSTRING.equals(request.getStoreType()))
                    {
                        String location = "";
                        if (StringUtils.isNotEmpty(source.getTsSize()) && source.getTsSize().compareTo(BigDecimal.ZERO) == 1)
                        {
                            StoreContainer device = getBestDevice(source.getTypeId(), Constant.SAMPLE_RECEIVE_TEMPINSTRING);
                            location =
                                lpopRedisLocation.lpopLocationRedis(device != null ? device.getId() : "",
                                    source.getTypeId(),
                                    Constant.SAMPLE_RECEIVE_TEMPINSTRING);
                            updateLocationState(location);
                        }
                        else
                        {
                            response.setSuccess(false);
                            response.setMessage("原因：" + request.getSampleCode() + "临时存储量为0,无需入库");
                            return response;
                        }
                        
                        if (StringUtils.isNotEmpty(location))
                        {
                            source.setTsLocation(location); //返回到前台页面显示
                            target.setLocation(location);
                            target.setSampleStoraging(entity);
                            baseDaoSupport.insert(target);
                            source.setParentId(storagingId);//返回到前台页面显示
                            entity.setOperateTime(new Date());
                            baseDaoSupport.update(entity);
                        }
                        else
                        {
                            response.setSuccess(false);
                            response.setMessage("原因：无可用 的临时存储位置，请添加【" + source.getTypeName() + "】对应的存储容器");
                            return response;
                        }
                    }
                    
                    //存LIMS_TESTING_SAMPLE_STORAGE by dhr
                    
                    String localtionStorage = "";
                    BigDecimal sampleSize = null;
                    if (Constant.SAMPLE_RECEIVE_LONGINSTRIGN.equals(request.getStoreType()))
                    {
                        localtionStorage = source.getLsLocation();
                        sampleSize = source.getLsSize();
                    }
                    
                    if (Constant.SAMPLE_RECEIVE_TEMPINSTRING.equals(request.getStoreType()))
                    {
                        localtionStorage = source.getTsLocation();
                        sampleSize = source.getTsSize();
                    }
                    NamedQueryer querySampleStorage = new NamedQueryer();
                    StringBuffer hqlSampleStorage = new StringBuffer();
                    hqlSampleStorage.append("FROM TestingSampleStorage o where  o.sampleCode =:sampleCode and o.locationCode =:locationCode"); //转存库里 并且是已转存过的 样本
                    querySampleStorage.setHql(hqlSampleStorage.toString());
                    querySampleStorage.setNames(Arrays.asList("sampleCode", "locationCode"));
                    querySampleStorage.setValues(Arrays.asList((Object)request.getSampleCode(), localtionStorage));
                    List<TestingSampleStorage> testingSampleStorageList = baseDaoSupport.find(querySampleStorage, TestingSampleStorage.class);
                    if (Collections3.isEmpty(testingSampleStorageList) && Constant.SAMPLE_RECEIVE_LONGINSTRIGN.equals(request.getStoreType()))
                    {
                        TestingSampleStorage testingSampleStorage = new TestingSampleStorage();
                        testingSampleStorage.setSampleCode(source.getSampleCode());
                        testingSampleStorage.setLocationCode(localtionStorage);
                        testingSampleStorage.setSampleSize(sampleSize);
                        testingSampleStorage.setStatus(1);
                        baseDaoSupport.insert(testingSampleStorage);
                    }
                }
                
                response.setObj(result);
                response.setSuccess(true);
                return response;
                
            }
            else
            {
                List<OrderSampleView> orderSampleView = getSampleByView(request);
                if (Collections3.isEmpty(orderSampleView))
                {
                    response.setSuccess(false);
                    response.setMessage("原因：【" + request.getSampleCode() + "】样本不存在");
                    return response;
                }
                response.setSuccess(false);
                response.setMessage("原因：【" + request.getSampleCode() + "】样本未经过转存");
                return response;
            }
        }
        else
        {
            throw new RuntimeException("入库过程中，无查询样本code");
        }
        
    }
    
    @Override
    public void autoStartProcess(String id)
    {
        testingAdapter.autoStartOrderTesting(id);
    }
    
    @Override
    @Transactional
    public void createStoraging(SampleStoragingFormRequest request)
    {
        
        if (StringUtils.isNotEmpty(request.getStoragingId())) //找到操作父节点
        {
            SampleStoraging pp = baseDaoSupport.get(SampleStoraging.class, request.getStoragingId());
            if (StringUtils.isNotEmpty(pp))
            {
                pp.setRemark(request.getRemark());
                pp.setStatus(true);//结束
                pp.setOperateTime(new Date());
                baseDaoSupport.update(pp);
            }
        }
        
    }
    
    @Override
    public Pagination<SampleTransferring> sampleTransferringPaging(SampleTransferringDetailSearcher request)
    {
        
        return baseDaoSupport.find(request.toQuery(), request.getPageNo(), request.getPageSize(), SampleTransferring.class);
    }
    
    @Override
    public Pagination<SampleStoraging> sampleStoragePaging(SampleStoragingSearcher request)
    {
        return baseDaoSupport.find(request.toAuthQuery(), request.getPageNo(), request.getPageSize(), SampleStoraging.class);
    }
    
    @Override
    public Pagination<SampleReceivingDetail> sampleDetail(SampleReceivePagingRequest request)
    {
        SampleReceivingSearcher searcher = new SampleReceivingSearcher();
        BeanUtils.copyProperties(request, searcher);
        Pagination<SampleReceivingDetail> result =
            baseDaoSupport.find(searcher.toDetailQuery(), request.getPageNo(), request.getPageSize(), SampleReceivingDetail.class);
        for (SampleReceivingDetail s : result.getRecords())
        {
            
            String hql = "from OrderSampleView  o where o.sampleCode =:sampleCode ";
            List<OrderSampleView> subSample =
                baseDaoSupport.findByNamedParam(OrderSampleView.class, hql, new String[] {"sampleCode"}, new Object[] {s.getSampleCode()});
            
            OrderSampleView os = Collections3.getFirst(subSample);
            if (StringUtils.isNotEmpty(os))
            {
                s.setSampleName(os.getSampleName());
                s.setFamilyRelation(os.getFamilyRelation());
            }
        }
        
        return result;
    }
    
    @Override
    public Pagination<SampleStoragingDetail> storageDetail(SampleStoragingSearcher request)
    {
        return baseDaoSupport.find(request.toDetailQuery(), request.getPageNo(), request.getPageSize(), SampleStoragingDetail.class);
    }
    
    @Override
    public List<ReceivedSample> getReceivedSampleByCode(OrderSearchRequest request)
    {
        
        NamedQueryer queryer = new NamedQueryer();
        StringBuffer hql = new StringBuffer();
        hql.append("select s FROM ReceivedSample s ,Order o where o.code =:code and s.orderId = o.id ");
        queryer.setHql(hql.toString());
        queryer.setNames(Arrays.asList("code"));
        queryer.setValues(Arrays.asList((Object)request.getCode()));
        return baseDaoSupport.find(queryer, ReceivedSample.class);
    }
    
    @Override
    public Response<ReceivedSample> getHasReceivedSample(OrderSearchRequest request)
    {
        Response<ReceivedSample> response = new Response<ReceivedSample>();
        
        if (StringUtils.isNotEmpty(request.getSampleCode()))
        {
            
            SampleReceivingSearcher searcher = new SampleReceivingSearcher();
            searcher.setSampleCode(request.getSampleCode());
            searcher.setQcStatus(Constant.QC_STATUS_FAIL); //质检不合格
            List<ReceivedSample> validateStatusSample = baseDaoSupport.find(searcher.toDetailQuery(), ReceivedSample.class);
            if (Collections3.isNotEmpty(validateStatusSample))
            {
                response.setSuccess(false);
                response.setMessage("原因：【" + request.getSampleCode() + "】样本质检不合格");
                return response;
            }
            
            List<OrderSampleView> orderSampleView = getSampleByView(request);
            if (Collections3.isEmpty(orderSampleView))
            {
                response.setSuccess(false);
                response.setMessage("原因：【" + request.getSampleCode() + "】样本不存在");
                return response;
            }
            
            NamedQueryer queryer = new NamedQueryer();
            StringBuffer hql = new StringBuffer();
            hql.append(" FROM ReceivedSample s where s.sampleCode =:code");
            queryer.setHql(hql.toString());
            queryer.setNames(Arrays.asList("code"));
            queryer.setValues(Arrays.asList((Object)request.getSampleCode()));
            List<ReceivedSample> r = baseDaoSupport.find(queryer, ReceivedSample.class);
            if (Collections3.isNotEmpty(r))
            {
                ReceivedSample receivedSample = Collections3.getFirst(r);
                if (receivedSample.getProcessStatus().equals(Constant.SAMPLE_RECEIVING_CANCEL))
                {
                    response.setSuccess(false);
                    response.setMessage("原因：【" + request.getSampleCode() + "】样本关联订单已取消");
                }
                else if (receivedSample.getProcessStatus().equals(Constant.SAMPLE_RECEIVING))
                {
                    response.setSuccess(true);
                    response.setObj(r);
                }
                else if (receivedSample.getProcessStatus().equals(Constant.SAMPLE_RECEIVING_TRANS))
                {
                    response.setSuccess(false);
                    response.setMessage("原因：【" + request.getSampleCode() + "】样本已转存");
                }
                
            }
            else
            {
                response.setSuccess(false);
                response.setMessage("原因：【" + request.getSampleCode() + "】样本未接收");
            }
            
        }
        return response;
    }
    
    @Override
    public List<SampleReceivingDetail> getSampleDetailByCode(SampleReceivePagingRequest request)
    {
        NamedQueryer queryer = new NamedQueryer();
        StringBuffer hql = new StringBuffer();
        hql.append("select s FROM SampleReceivingDetail s where s.sampleCode = :sampleCode ");
        queryer.setHql(hql.toString());
        queryer.setNames(Arrays.asList("sampleCode"));
        queryer.setValues(Arrays.asList((Object)request.getSampleCode()));
        return baseDaoSupport.find(queryer, SampleReceivingDetail.class);
    }
    
    @Override
    public SampleTransferring getSampleTransferringById(String id)
    {
        return baseDaoSupport.get(SampleTransferring.class, id);
    }
    
    @Override
    public Map<String, Object> getReceivedSampleNameByCode(OrderSearchRequest request)
    {
        
        Map<String, Object> result = new HashMap<String, Object>();
        StringBuffer hql = new StringBuffer();
        hql.append(" select s.sampleName FROM ReceivedSample s where s.sampleCode =:code ");
        List<String> name = baseDaoSupport.findByNamedParam(String.class, hql.toString(), new String[] {"code"}, new Object[] {request.getSampleCode()});
        if (Collections3.isNotEmpty(name))
        {
            result.put("name", name.get(0));
        }
        return result;
    }
    
    @Override
    public Pagination<AppSampleTransport> packageSampleList(SampleReceivePagingRequest request)
    {
        SampleReceivingSearcher searcher = new SampleReceivingSearcher();
        BeanUtils.copyProperties(request, searcher);
        Pagination<AppSampleTransport> paging =
            baseDaoSupport.find(searcher.toPackageQuery(), request.getPageNo(), request.getPageSize(), AppSampleTransport.class);
        return paging;
    }
    
    @Override
    public List<SampleStoragingDetail> getSampleStoragingDetailBySampleCode(String sampleCode)
    {
        NamedQueryer queryer = new NamedQueryer();
        StringBuffer hql = new StringBuffer();
        hql.append("select s FROM SampleStoragingDetail s where s.sampleCode = :sampleCode ");
        queryer.setHql(hql.toString());
        queryer.setNames(Arrays.asList("sampleCode"));
        queryer.setValues(Arrays.asList((Object)sampleCode));
        return baseDaoSupport.find(queryer, SampleStoragingDetail.class);
    }
    
}
