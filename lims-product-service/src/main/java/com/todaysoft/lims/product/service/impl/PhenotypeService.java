package com.todaysoft.lims.product.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.google.gson.Gson;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.IEntityWrapper;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.product.config.Configs;
import com.todaysoft.lims.product.dao.searcher.PhenotypeSearcher;
import com.todaysoft.lims.product.entity.Phenotype;
import com.todaysoft.lims.product.model.request.PhenotypeRequest;
import com.todaysoft.lims.product.model.response.PhenotypePageModel;
import com.todaysoft.lims.product.service.IPhenotypeService;
import com.todaysoft.lims.product.service.adapter.PhenotypeCosumerEvent;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class PhenotypeService implements IPhenotypeService, IEntityWrapper<Phenotype, PhenotypePageModel>
{
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private Configs configs;
    
    @Resource(name = "producer")
    private Producer producer;
    
    @Override
    @Transactional
    public void create(Phenotype request)
    {
        request.setCreateTime(new Date());
        request.setDeleted(false);
        baseDaoSupport.insert(request);
        
    }
    
    @Override
    public void sendPhenotypeProduce(String id, String actionType)
    {
        PhenotypeCosumerEvent event = new PhenotypeCosumerEvent();
        event.setId(id);
        event.setActionType(actionType);
        Message msg = new Message(configs.getAliyunONSTopic(), "phenotypeTag", new Gson().toJson(event).getBytes());
        SendResult sendResult = producer.send(msg);
        System.out.println(sendResult.getMessageId());
        
    }
    
    @Override
    @Transactional
    public void modify(Phenotype request)
    {
        
        request.setCreateTime(new Date());
        baseDaoSupport.update(request);
        
    }
    
    @Override
    @Transactional
    public void delete(PhenotypeRequest searcher)
    {
        if (StringUtils.isNotEmpty(searcher.getId()))
        {
            
            Phenotype p = baseDaoSupport.get(Phenotype.class, searcher.getId());
            if (StringUtils.isNotEmpty(p))
            {
                p.setDeleted(true);
                p.setDeleteTime(new Date());
                baseDaoSupport.update(p);
            }
            
        }
        else if (StringUtils.isNotEmpty(searcher.getCode()))
        {
            baseDaoSupport.executeHql("delete Phenotype p where p.code = ?", new Object[] {searcher.getCode()});
        }
        
    }
    
    @Override
    public Pagination<PhenotypePageModel> paging(PhenotypeRequest request)
    {
        PhenotypeSearcher searcher = new PhenotypeSearcher();
        BeanUtils.copyProperties(request, searcher);
        Pagination<PhenotypePageModel> p = baseDaoSupport.find(searcher.toQuery(), request.getPageNo(), request.getPageSize(), this);
        for (PhenotypePageModel m : p.getRecords())
        {
            m.setList(null);
        }
        return p;
    }
    
    @Override
    public PhenotypePageModel getPhenotypeById(String id)
    {
        
        Phenotype p = baseDaoSupport.get(Phenotype.class, id);
        
        return new PhenotypePageModel(p);
    }
    
    @Override
    public boolean checkedName(PhenotypeRequest request)
    {
        String flag = null;
        if (StringUtils.isAnyNotEmpty(request.getCode()))
        {
            flag = "code";
        }
        else
        {
            flag = "name";
        }
        if (Collections3.isNotEmpty(baseDaoSupport.vaildateUniquen(Phenotype.class, request, flag)))
        {
            return false;
        }
        return true;
    }
    
    @Override
    public List<PhenotypePageModel> getPhenotypeList(PhenotypeRequest request)
    {
        
        PhenotypeSearcher searcher = new PhenotypeSearcher();
        BeanUtils.copyProperties(request, searcher);
        return baseDaoSupport.find(searcher, this);
    }
    
    @Override
    public PhenotypePageModel wrap(Phenotype entity)
    {
        return new PhenotypePageModel(entity);
    }
    
    @Override
    public PhenotypePageModel getPhenotypeByCode(String code)
    {
        List<Phenotype> list = baseDaoSupport.find(Phenotype.class, "from Phenotype p where p.code='" + code + "' and p.deleted = false");
        
        if (StringUtils.isNotEmpty(list))
        {
            return new PhenotypePageModel(list.get(0));
            
        }
        return null;
    }
    
}
