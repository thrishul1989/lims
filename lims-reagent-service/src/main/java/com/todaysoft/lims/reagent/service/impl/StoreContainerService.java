package com.todaysoft.lims.reagent.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.StoreContainerSearcher;
import com.todaysoft.lims.reagent.entity.Sample;
import com.todaysoft.lims.reagent.entity.StorageLocation;
import com.todaysoft.lims.reagent.entity.StoreContainer;
import com.todaysoft.lims.reagent.model.request.StoreContainerCreateRequest;
import com.todaysoft.lims.reagent.model.request.StoreContainerFormRequest;
import com.todaysoft.lims.reagent.model.request.StoreContainerModifyRequest;
import com.todaysoft.lims.reagent.model.request.StoreContainerPagingRequest;
import com.todaysoft.lims.reagent.service.IStoreContainerService;
import com.todaysoft.lims.reagent.util.Constant;
import com.todaysoft.lims.reagent.util.LpushRedisLocation;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class StoreContainerService implements IStoreContainerService
{
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    private Logger log = Logger.getLogger(StoreContainerService.class);
    
    @Override
    public Pagination<StoreContainer> paging(StoreContainerPagingRequest request)
    {
        StoreContainerSearcher searcher = new StoreContainerSearcher();
        BeanUtils.copyProperties(request, searcher);
        return baseDaoSupport.find(searcher.toQuery(), request.getPageNo(), request.getPageSize(), StoreContainer.class);
    }
    
    @Override
    public StoreContainer getStoreContainer(String id)
    {
        return baseDaoSupport.get(StoreContainer.class, id);
    }
    
    public StorageLocation getStoreContainerLocation(Integer id)
    {
        return baseDaoSupport.get(StorageLocation.class, id);
    }
    
    public Integer selectContainerSeqNextVal(String type)
    {
        String param = "containerSeq" + type;
        List<?> result = baseDaoSupport.findBySql("SELECT NEXTVAL('" + param + "')");
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
    public String create(StoreContainerCreateRequest request)
    {
        StoreContainer storeContainer = new StoreContainer();
        BeanUtils.copyProperties(request, storeContainer, "id");
        storeContainer.setCode(request.getDeviceType() + String.format("%02d", selectContainerSeqNextVal(request.getDeviceType()))); //设置流水号
        storeContainer.setDeleted(false);
        storeContainer.setCreateTime(new Date());
        baseDaoSupport.insert(storeContainer);
        
        //存储位置编号规则为：存储容器编号-架号盒号-位号
        //如编号为001的负80度冰箱的1号架2号盒14位的存储位置编号为：
        //BX80001-01-01-014
        createLocation(request, storeContainer);
        
        return storeContainer.getId();
    }
    
    @Autowired
    private LpushRedisLocation lpushRedisLocation;
    
    private void createLocation(StoreContainerFormRequest request, StoreContainer storeContainer)
    {
        StorageLocation storageLocation = null;
        //此处需要控制 规格的输入 为0或者空的情况
        Integer frame = request.getFrame();
        Integer box = request.getBox();
        Integer site = request.getSite();
        Integer cell = request.getCell();
        
        String[] widthCell = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "J", "K"};
        
        for (int i = 1; i <= frame; i++)
        {
            for (int j = 1; j <= box; j++)
            {
                for (int k = 1; k <= site; k++)
                {
                    for (int c = 1; c <= cell; c++)
                    {
                        StringBuffer bs = new StringBuffer();
                        bs = bs.append(storeContainer.getCode()).append("-");
                        bs = bs.append(String.format("%02d", i)).append("-");
                        bs = bs.append(String.format("%02d", j)).append("-");
                        
                        bs = bs.append(widthCell[k - 1]).append(String.format("%02d", c));
                        
                        storageLocation = new StorageLocation();
                        //storageLocation.setId(UUID.randomUUID().toString());
                        storageLocation.setDeviceId(storeContainer.getId());
                        storageLocation.setCode(bs.toString());// 位置编号
                        
                        lpushRedisLocation.addLocationRedis(storeContainer.getId(), request.getSampleType(), request.getStorageType(), bs.toString());
                        
                        storageLocation.setState(Constant.CONTAINER_LOCATION_DEFAULT_STATE);
                        baseDaoSupport.insert(storageLocation);
                    }
                }
            }
        }
        
    }
    
    @Override
    @Transactional
    public void modify(StoreContainerModifyRequest request)
    {
        StoreContainer entity = getStoreContainer(request.getId());
        Integer frame = request.getFrame();
        Integer box = request.getBox();
        Integer site = request.getSite();
        Integer cell = request.getCell();
        Integer oldframe = entity.getFrame();
        Integer oldbox = entity.getBox();
        Integer oldsite = entity.getSite();
        Integer oldCell = entity.getCell();
        Boolean result = false;
        if (request.getDeviceType().equals(entity.getDeviceType()))
        {
            if (frame == oldframe && box == oldbox && site == oldsite && cell == oldCell)
            {
                result = true;
            }
        }
        
        if (!result)
        {
            //先删除之前所有的容器位置
            //再生成新的位置
            baseDaoSupport.executeHql("delete StorageLocation s where s.deviceId = ?", new Object[] {entity.getId()});
            //清空缓存
            lpushRedisLocation.clearLocationRedis(entity.getId(), request.getSampleType(), request.getStorageType(), "");
            createLocation(request, entity);
        }
        
        BeanUtils.copyProperties(request, entity, new String[] {"code"});
        entity.setCreateTime(new Date());
        baseDaoSupport.update(entity);
        
    }
    
    @Override
    @Transactional
    public void delete(String id)
    {
        StoreContainer entity = getStoreContainer(id);
        
        if (StringUtils.isNotEmpty(entity))
        {
            baseDaoSupport.executeHql("delete StorageLocation s where s.deviceId = ?", new Object[] {entity.getId()}); //删除对应容器
            //同时清空缓存
            lpushRedisLocation.clearLocationRedis(entity.getId(), entity.getSampleType(), entity.getStorageType(), "");
            //baseDaoSupport.delete(entity);
            entity.setDeleted(true);
            entity.setDeleteTime(new Date());
            baseDaoSupport.update(entity);
        }
        
    }
    
    @Override
    public List<StorageLocation> searchFreeLocationByContainerType(String containerType)
    {
        NamedQueryer queryer = new NamedQueryer();
        StringBuffer hql = new StringBuffer();
        hql.append("SELECT lo FROM StorageLocation lo , StoreContainer sc WHERE 1=1");
        hql.append(" AND lo.deviceId = sc.id");
        hql.append(" AND sc.deviceType =:type ");
        queryer.setHql(hql.toString());
        queryer.setNames(Arrays.asList("type"));
        queryer.setValues(Arrays.asList((Object)containerType));
        List<StorageLocation> records = baseDaoSupport.find(queryer, StorageLocation.class);
        return records; //默认自增一个
        
    }
    
    /**
     * 根据条件查询 【容器id、位置code】位置列表
     */
    @Override
    public Pagination<StorageLocation> getStorageLocationById(StoreContainerPagingRequest request)
    {
        Pagination<StorageLocation> pagging = baseDaoSupport.find(getQuery(request), request.getPageNo(), request.getPageSize(), StorageLocation.class);
        
        return pagging;
    }
    
    /**
     * 根据条件查询 【容器id、位置code】位置列表---查询语句
     */
    public NamedQueryer getQuery(StoreContainerPagingRequest request)
    {
        NamedQueryer queryer = new NamedQueryer();
        StringBuffer hql = new StringBuffer();
        hql.append("FROM StorageLocation sc WHERE sc.deviceId =:id");
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        names.add("id");
        values.add(request.getId());
        if (null != request.getState())
        {
            hql.append(" AND sc.state =:state");
            names.add("state");
            values.add(request.getState());
        }
        
        if (StringUtils.isNoneBlank(request.getCode()))
        {
            hql.append(" AND sc.code Like:code");
            names.add("code");
            values.add("%" + request.getCode() + "%");
        }
        hql.append(" order by code asc ");
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return queryer;
    }
    
    /**
     * 根据容器类型查询样本----可能存在
     */
    @Override
    public Sample getSampleByContainerType(String containerType)
    {
        
        /* NamedQueryer queryer =NamedQueryer.builder().hql("FROM Sample s WHERE s.storeContainer = :storeContainer")
                                          .names(Lists.newArrayList("storeContainer"))
                                          .values(Lists.newArrayList((Object)containerType)).build();
        List<Sample> records = baseDaoSupport.find(queryer, Sample.class);
        
        return records.isEmpty() ? null : records.get(0);*/
        return null;
        
    }
    
    /**
     * 根据位置id，清空所有 
     */
    @Override
    @Transactional
    public int cleanContainerLocation(String ids)
    {
        StoreContainer s = baseDaoSupport.get(StoreContainer.class, ids);
        
        int c = 0;
        String hql = "update StorageLocation s set s.sampleId=? ," + " s.state = ? " + "where s.deviceId= ?";
        try
        {
            c = baseDaoSupport.executeHql(hql, new Object[] {null, Constant.CONTAINER_LOCATION_DEFAULT_STATE, ids}); //清除对应容器
            
            if (StringUtils.isNotEmpty(s))
            {
                lpushRedisLocation.clearLocationRedis(s.getId(), s.getSampleType(), s.getStorageType(), ""); //清空所有 
                
                Integer frame = s.getFrame();
                Integer box = s.getBox();
                Integer site = s.getSite();
                
                for (int i = 1; i <= frame; i++)
                {
                    for (int j = 1; j <= box; j++)
                    {
                        for (int k = 1; k <= site; k++)
                        {
                            
                            StringBuffer bs = new StringBuffer();
                            bs = bs.append(s.getCode()).append("-");
                            bs = bs.append(String.format("%02d", i)).append("-");
                            bs = bs.append(String.format("%02d", j)).append("-");
                            bs = bs.append(String.format("%03d", k));
                            
                            lpushRedisLocation.addLocationRedis(s.getId(), s.getSampleType(), s.getStorageType(), bs.toString());
                            
                        }
                    }
                }
                
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return c;
    }
    
    /**
     * 根据存储容器id获取  
     * 当前已被占用位置数据
     */
    @Override
    public Long countUserdLocation(String id)
    {
        StringBuffer hql = new StringBuffer();
        hql.append("select count(*) from StorageLocation s where s.deviceId=? and s.state=? ");
        Object[] values = {id, Constant.CONTAINER_LOCATION_USED};
        return baseDaoSupport.find(Number.class, hql.toString(), values).get(0).longValue();
    }
    
    @Override
    public boolean validate(StoreContainerPagingRequest request)
    {
        if (Collections3.isNotEmpty(baseDaoSupport.vaildateUniquen(StoreContainer.class, request, "name")))
        {
            return false;
        }
        return true;
    }
    
    @Override
    @Transactional
    public StorageLocation cleanOneContainerLocation(String id)
    {
        StorageLocation s = baseDaoSupport.get(StorageLocation.class, id);
        
        if (StringUtils.isNotEmpty(s))
        {
            s.setState(Constant.CONTAINER_LOCATION_DEFAULT_STATE);
            baseDaoSupport.update(s);
        }
        
        return s;
    }
    
    @Override
    @Transactional
    public Integer genLocation(StorageLocation s)
    {
        if (StringUtils.isNotEmpty(s.getDeviceId()))
        {
            StoreContainer device = baseDaoSupport.get(StoreContainer.class, s.getDeviceId());
            if (StringUtils.isNotEmpty(device))
            {
                lpushRedisLocation.clearLocationRedis(device.getId(), device.getSampleType(), device.getStorageType(), ""); //清空所有 
                
                //对已有的位置 排序查询---插入 redis 
                StringBuffer hql = new StringBuffer();
                hql.append("FROM StorageLocation o where  o.state =:state and o.deviceId =:deviceId  order by o.code asc "); //转存库里 并且是已转存过的 样本
                
                List<StorageLocation> result =
                    baseDaoSupport.findByNamedParam(StorageLocation.class,
                        hql.toString(),
                        new String[] {"state", "deviceId"},
                        new Object[] {1, s.getDeviceId()});
                
                if (Collections3.isNotEmpty(result))
                {
                    for (StorageLocation l : result)
                    {
                        lpushRedisLocation.addLocationRedis(device.getId(), device.getSampleType(), device.getStorageType(), l.getCode());
                    }
                }
            }
            
        }
        return 1;
    }
    
    @Override
    @Transactional
    public void synchronizeContainer(String id)
    {
        if (StringUtils.isNotEmpty(id))
        {
            StoreContainer device = baseDaoSupport.get(StoreContainer.class, id);
            if (StringUtils.isNotEmpty(device))
            {
                lpushRedisLocation.clearLocationRedis(device.getId(), device.getSampleType(), device.getStorageType(), ""); //清空所有 
                
                //对已有的位置 排序查询---插入 redis 
                StringBuffer hql = new StringBuffer();
                hql.append("FROM StorageLocation o where  o.state =:state and o.deviceId =:deviceId order by o.code asc "); //转存库里 并且是已转存过的 样本
                
                List<StorageLocation> result =
                    baseDaoSupport.findByNamedParam(StorageLocation.class, hql.toString(), new String[] {"state", "deviceId"}, new Object[] {1, id});
                
                if (Collections3.isNotEmpty(result))
                {
                    for (StorageLocation l : result)
                    {
                        lpushRedisLocation.addLocationRedis(device.getId(), device.getSampleType(), device.getStorageType(), l.getCode());
                    }
                }
            }
            
        }
        
    }
}
